package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.commands.Commands.conditional;
import static com.pedropathing.ivy.commands.Commands.infinite;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.match;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.util.Timing;

import org.firstinspires.ftc.teamcode.subsystems.BeamBreaks;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;


import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

public class Robot {

    public enum DriveState{
        NORMAL,
        AIMING,
        AUTOMATED,
        OFF
    }

    DriveState driveState = DriveState.NORMAL;

    public Intake intake;
    public Shooter shooter;
    public Follower f;
    public BeamBreaks beamBreaks;
    Timing.Timer shootTimer = new Timing.Timer(700, TimeUnit.MILLISECONDS);
    boolean waitForOpen;
    public boolean shooting = false;
    public boolean autoAiming = false;
    boolean usingAutoGate = true;
    public Pose goalPose;
    double forwardInput, rightInput, rotateInput = 0;
    public boolean isShooting = false;
    public boolean automatedDrive = false;

    Command shoot = Command.build() //might want to make this 2 separate commands if I see any issues
            .setStart(() -> {
                isShooting = true;
                intake.stop();
                waitForOpen = shooter.gateIsClosed();
                if (waitForOpen){
                    shooter.openGate();
                }
                shootTimer.start();
            })
            .setExecute(() -> {
                if (waitForOpen) {
                    if (shootTimer.elapsedTime() > 300){
                        waitForOpen = false;
                        shootTimer.start();
                    }
                } else {
                    intake.spinIn();
                }

            })
            .setDone(() -> shootTimer.done())
            .setEnd(endCondition -> {
                intake.stop();
                shooter.closeGate();
                isShooting = false;
            })
            .requiring(intake, shooter)
            //todo add conflicting behaviors here and priority
            ;

    public Command handleNormalDrive(){
        return infinite(()-> f.setTeleOpDrive(forwardInput, rightInput, rotateInput));
    }
    public Command handleAimingDrive(){
        return infinite(()-> f.setTeleOpDrive(forwardInput, rightInput, getAimingPIDFOutput()));
    }
    public Command driveOff = instant(() -> f.setTeleOpDrive(0, 0, 0));
    public Command followPath(PathChain pathChain){
        return (driveOff).then(follow(f, pathChain));
    }

    public Command handleManualDrive = conditional(
            () -> autoAiming,
            handleNormalDrive(),
            handleAimingDrive()
    );

    public void init(boolean red){
        intake = new Intake();
        shooter = new Shooter();
        beamBreaks = new BeamBreaks();
        if (red){
            goalPose = Paths.redGoal;
        } else {
            goalPose = Paths.blueGoal;
        }
        f.setStartingPose(PoseSaver.endPose);
    }

    public double getDistToGoal(){
        double xDiff = f.getPose().getX() - goalPose.getX();
        double yDiff = f.getPose().getY() - goalPose.getY();
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)); //lab todo check this
    }

    public void periodic(Gamepad gamepad){
        f.update();

        forwardInput = gamepad.left_stick_y;
        rightInput = gamepad.left_stick_x;
        rotateInput = gamepad.right_stick_x;//lab todo check directions with old code

        shooter.periodic(getDistToGoal());
        beamBreaks.periodic(shooting, autoAiming);
        if (usingAutoGate){
            autoGate();
        }
    }

    public void autoGate(){
        if (beamBreaks.getBallCount() < 3 && !shooting){
            shooter.closeGate();
        } else {
            shooter.openGate();
        }
    }

    public void drive(double forward, double right, double rotate){
        f.setTeleOpDrive(forward, right, rotate);
    }

    public double getAimingPIDFOutput(){
        double xDiff = f.getPose().getX() - goalPose.getX();
        double yDiff = f.getPose().getY() - goalPose.getY();
        double targetAngle = Math.atan2(xDiff, yDiff);
        double error = f.getHeading() - targetAngle; //lab todo check this

        PIDFController headingPIDF = new PIDFController(0, 0, 0, 0); //robot todo tune this or base it off of pedro
        return headingPIDF.calculate(error); //robot todo ensure radians and degrees don't mix, this whole thing really needs testing
    }


}
