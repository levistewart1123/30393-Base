package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.match;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.util.Timing;

import org.firstinspires.ftc.teamcode.subsystems.BeamBreaks;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;


import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

public class Robot {

    enum DriveState{
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

    Command shoot = Command.build() //might want to make this 2 separate commands if I see any issues
            .setStart(() -> {
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
            })
            .requiring(intake, shooter)
            //todo add conflicting behaviors here and priority
            ;

    public Command handleNormalDrive(){
        return instant(()-> f.setTeleOpDrive(forwardInput, rightInput, rotateInput));
    }
    public Command handleAimingDrive(){
        return instant(()-> f.setTeleOpDrive(forwardInput, rightInput, rotateInput));
    }






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

        EnumMap<DriveState, Command> cases = new EnumMap<>(DriveState.class);
        cases.put(DriveState.NORMAL, handleNormalDrive());
//        cases.put(DriveState.AIMING, );
//        cases.put(DriveState.AUTOMATED, );
//        cases.put(DriveState.OFF, );
        Command handleDrive = match(() -> driveState, cases);
    }

    public double getDistToGoal(){
        double xDiff = f.getPose().getX() - goalPose.getX();
        double yDiff = f.getPose().getY() - goalPose.getY();
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)); //robot todo check this
    }

    public void periodic(){
        f.update();
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

    public void updateMoveInput(Gamepad gamepad){ //lab todo check directions with old code
        forwardInput = gamepad.left_stick_y;
        rightInput = gamepad.left_stick_x;
        rotateInput = gamepad.right_stick_x;
    }



}
