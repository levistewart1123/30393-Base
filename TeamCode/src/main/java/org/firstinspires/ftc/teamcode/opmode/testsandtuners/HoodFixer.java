package org.firstinspires.ftc.teamcode.opmode.testsandtuners;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.teamcode.opmode.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.subsystems.Shooter;


@TeleOp
public class HoodFixer extends CommandOpMode {
    private Shooter shooter = new Shooter();
    @Override
    public void init() {
        super.init();
        shooter.init(hardwareMap);
    }

    @Override
    public void start() {
        super.start();
        shooter.setHood(1);
    }

    @Override
    public void loop() {
        super.loop();
        if (super.loops > 2000) {
            shooter.setHood(0.04);
        } else {
            shooter.setHood(1);
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
