package org.firstinspires.ftc.teamcode.opmodes.testsAndTuners;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.subsystems.Intake;

@Configurable
@Deprecated
@TeleOp(name = "Intake Servo Tuner (needs public intake servos)", group = "1: tuners")
public class IntakeServoTuner extends CommandOpMode {
    private final Intake intake = new Intake();
    public static double lPos, rPos = 0;
    @Override
    public void init() {
        super.init();
        intake.initialize(hardwareMap);
    }

    @Override
    public void start() {
//        intake.left.set(lPos);
//        intake.right.set(rPos);
        super.start();
    }

    @Override
    public void loop() {
//        intake.left.set(lPos);
//        intake.right.set(rPos);
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
