package org.firstinspires.ftc.teamcode.tele;

import static com.pedropathing.ivy.Scheduler.execute;
import static com.pedropathing.ivy.Scheduler.isRunning;
import static com.pedropathing.ivy.Scheduler.isScheduled;
import static com.pedropathing.ivy.Scheduler.reset;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Paths;
import org.firstinspires.ftc.teamcode.robot.Robot;

import java.util.function.Supplier;

/**
 *
 */
public class CommandTeleOp extends OpMode {
    protected Robot robot = new Robot();
    protected boolean isRed;
    protected Paths paths;
    protected Supplier<PathChain> center;
    public CommandTeleOp(boolean isRed) {
        this.isRed = isRed;
    }

    @Override
    public void init() {
        robot.init(isRed, hardwareMap);
        paths = new Paths(robot.follower, isRed);
        center = () -> robot.follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(robot.follower::getPose, new Pose(72, 72))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(robot.follower::getHeading, Math.toRadians(0), 0.8))
                .build();

        reset();
    }


    @Override
    public void start() {
        robot.commandPeriodic(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        schedule(robot.startManualDrive);
        //schedule(robot.handleGate);
        schedule(robot.handleIntake);
        execute();
    }

    @Override
    public void loop() {
        execute();
        robot.commandPeriodic(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        //*autoaim
        if (gamepad1.bWasPressed()){
            robot.autoAiming = !robot.autoAiming;
        }
        //*shooting
        if (gamepad1.xWasPressed()){
            schedule(robot.shoot);
        }
        //*slow mode
        if (gamepad1.aWasPressed()){
            robot.slowDrive = !robot.slowDrive;
        }
        //todo path following, make individual commands in Robot class to set priority, etc
        if (gamepad1.yWasPressed()){

        }
        //*intake
        if (gamepad1.right_trigger > 0.1){
            robot.intakeState = Robot.IntakeState.IN;
        } else if (gamepad1.left_trigger > 0.1) {
            robot.intakeState = Robot.IntakeState.OUT;
        } else {
            robot.intakeState = Robot.IntakeState.OFF;
        }
        telemetry.addLine(Scheduler.isRunning(robot.shoot) ? "shooting" : "not shooting");
        telemetry.addLine(Scheduler.isRunning(robot.handleIntake) ? "manual intake" : "not manual intake");
        telemetry.addLine(Scheduler.isRunning(robot.handleGate) ? "auto gate" : "not auto gate");
        telemetry.addLine(Scheduler.isRunning(robot.startManualDrive) ? "manual drive" : "not manual drive");
        telemetry.update();
        //lab todo add automated drive controls from old code

    }
}
