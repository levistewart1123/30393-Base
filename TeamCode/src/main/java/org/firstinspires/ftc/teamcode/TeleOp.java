package org.firstinspires.ftc.teamcode;

import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 *
 */
public class TeleOp extends OpMode {
    protected Robot robot = new Robot();
    protected boolean red;

    public TeleOp(boolean red) {
        this.red = red;
    }

    @Override
    public void init() {
        robot.init(red);
        Scheduler.reset();
    }

    @Override
    public void start() {
        robot.f.startTeleOpDrive();
    }

    @Override
    public void loop() {
        if (gamepad1.aWasPressed()){
            robot.shoot.schedule();
        }

        robot.periodic();
        Scheduler.execute();
    }
}
