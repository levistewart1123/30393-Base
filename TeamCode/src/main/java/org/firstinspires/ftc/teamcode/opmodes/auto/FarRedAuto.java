package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;

@Autonomous
public class FarRedAuto extends OpMode {

    Robot robot = new Robot();
    Auto auto = new Auto(true);


    @Override
    public void init() {
        robot.initialize(true, hardwareMap);
        Scheduler.reset();
        Scheduler.schedule(auto.farAuto());
    }

    @Override
    public void loop() {
        robot.follower.update();
        Scheduler.execute();
        // Feedback to Driver Hub for debugging
        telemetry.addData("x", robot.follower.getPose().getX());
        telemetry.addData("y", robot.follower.getPose().getY());
        telemetry.addData("heading", robot.follower.getPose().getHeading());
        telemetry.update();
    }
}
