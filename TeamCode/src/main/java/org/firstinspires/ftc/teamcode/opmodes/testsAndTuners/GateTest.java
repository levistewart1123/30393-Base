package org.firstinspires.ftc.teamcode.opmodes.testsAndTuners;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.subsystems.Shooter;

@Configurable
@TeleOp(name = "Gate Test", group = "2: tests")
public class GateTest extends CommandOpMode {
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
        if (super.loops < 3000) {
            shooter.closeGate();
        } else {
            shooter.openGate();
        }
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
