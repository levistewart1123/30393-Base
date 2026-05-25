package org.firstinspires.ftc.teamcode.tele;

import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.robot.Robot;

/**
 *
 */
public class CommandTeleOp extends OpMode {
    protected Robot robot = new Robot();
    protected boolean isRed;

    public CommandTeleOp(boolean isRed) {
        this.isRed = isRed;
    }

    @Override
    public void init() {
        robot.init(isRed, hardwareMap);
    }

    @Override
    public void start() {
        robot.periodic(gamepad1);

    }

    @Override
    public void loop() {
        robot.periodic(gamepad1);

        if (gamepad1.bWasPressed()){
            robot.toggleAiming();
        }
        if (gamepad1.xWasPressed()){
            robot.shoot.schedule();
        }
        if (gamepad1.aWasPressed()){
            robot.slowDrive = !robot.slowDrive;
        }
        //lab todo add automated drive controls from old code

    }
}
