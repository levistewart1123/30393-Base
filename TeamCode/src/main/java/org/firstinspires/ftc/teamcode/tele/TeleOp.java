package org.firstinspires.ftc.teamcode.tele;

import static com.pedropathing.ivy.Scheduler.schedule;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;

/**
 *
 */
public class TeleOp extends OpMode {
    protected Robot robot = new Robot();
    protected boolean isRed;

    public TeleOp(boolean isRed) {
        this.isRed = isRed;
    }

    @Override
    public void init() {
        robot.init(isRed, hardwareMap);
    }

    @Override
    public void start() {
        robot.follower.startTeleOpDrive();
    }

    @Override
    public void loop() {
        robot.periodic(gamepad1);
        if (gamepad1.bWasPressed()){
            robot.toggleAiming();
        }
        if (gamepad1.xWasPressed()){
            robot.startShoot();
        }
        if (gamepad1.aWasPressed()){
            robot.slowDrive = !robot.slowDrive;
        }
        //lab todo add automated drive controls from old code

    }
}
