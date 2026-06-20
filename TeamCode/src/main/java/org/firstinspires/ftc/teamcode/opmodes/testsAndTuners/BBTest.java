package org.firstinspires.ftc.teamcode.opmodes.testsAndTuners;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.subsystems.BeamBreaks;

@Configurable
@TeleOp(name = "Beam Break Test", group = "2: tests")
public class BBTest extends CommandOpMode {
    private final BeamBreaks beamBreaks = new BeamBreaks();
    @Override
    public void init() {
        super.init();
        beamBreaks.initialize(hardwareMap);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        beamBreaks.updatePrism(false, false);
        telemetry.addData("balls: ", beamBreaks.getBallCount());
        telemetry.addData("bottom: ", beamBreaks.bottom.isPressed());
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
