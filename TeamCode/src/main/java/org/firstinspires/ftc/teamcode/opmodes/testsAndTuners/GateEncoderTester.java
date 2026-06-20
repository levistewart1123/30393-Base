package org.firstinspires.ftc.teamcode.opmodes.testsAndTuners;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.subsystems.Shooter;
@TeleOp
@Deprecated
public class GateEncoderTester extends CommandOpMode {
    private final Shooter shooter = new Shooter();
    @Override
    public void init() {
        super.init();
        shooter.initialize(hardwareMap);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
//        if (super.loops < 1000) {
//            shooter.closeGate();
//        } else {
//            shooter.openGate();
//        }
//        telemetry.addData("encoder pos (deg): ", shooter.getGateEncoderPosDeg());
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
