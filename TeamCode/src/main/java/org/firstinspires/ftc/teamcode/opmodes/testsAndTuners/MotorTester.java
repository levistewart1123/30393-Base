package org.firstinspires.ftc.teamcode.opmodes.testsAndTuners;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "Motor Tester (Old)", group = "2: tests")
public class MotorTester extends LinearOpMode {
    Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.initialize(true, hardwareMap);
        waitForStart();
        robot.follower.startTeleOpDrive();
        robot.follower.setTeleOpDrive(1, 0, 0);
        telemetry.addLine("driving");
        telemetry.update();
        sleep(1000);
        robot.follower.setTeleOpDrive(0,0,0);
        sleep(1000);
        robot.intake.spinIn();
        telemetry.addLine("intaking");
        telemetry.update();
        sleep(1000);
        robot.intake.stop();
        sleep(1000);
//        robot.shooter.openGate();
        //sleep(1000);
//        robot.shooter.closeGate();
        //sleep(1000);
        robot.shooter.setHood(0);
        telemetry.addLine("hood");
        telemetry.update();
        sleep(1000);
        robot.shooter.setHood(1);
        sleep(1000);
        robot.shooter.runNoPIDF(0.1);
        telemetry.addLine("flywheels");
        telemetry.update();
        sleep(1000);
        robot.shooter.runNoPIDF(0);
        sleep(1000);
        robot.kickstand.lift();
        telemetry.addLine("kickstand");
        telemetry.update();


    }
}
