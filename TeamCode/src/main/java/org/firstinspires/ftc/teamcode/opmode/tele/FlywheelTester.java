package org.firstinspires.ftc.teamcode.opmode.tele;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp
public class FlywheelTester extends OpMode {
    Robot robot = new Robot();
    @Override
    public void init() {
        robot.init(true, hardwareMap);
    }

    @Override
    public void start() {
        robot.follower.startTeleOpDrive();
        robot.follower.update();
    }

    @Override
    public void loop() {
        robot.follower.update();
        robot.follower.setTeleOpDrive(0.5, 0, 0);
    }
}
