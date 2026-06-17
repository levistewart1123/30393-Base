package org.firstinspires.ftc.teamcode.opmodes.testsAndTuners;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.opmodes.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.subsystems.BeamBreaks;
import org.firstinspires.ftc.teamcode.robot.subsystems.Prism.GoBildaPrismDriver;

@TeleOp(name = "Artboard Checker", group = "2: tests")
public class PrismArtboardFixer extends CommandOpMode {
    private BeamBreaks beamBreaks = new BeamBreaks();
    GoBildaPrismDriver.Artboard currentArtboard = GoBildaPrismDriver.Artboard.ARTBOARD_0;
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
        if (gamepad1.aWasPressed()){
            currentArtboard = GoBildaPrismDriver.Artboard.ARTBOARD_0;
        }
        if (gamepad1.bWasPressed()){
            currentArtboard = GoBildaPrismDriver.Artboard.ARTBOARD_1;
        }
        if (gamepad1.yWasPressed()){
            currentArtboard = GoBildaPrismDriver.Artboard.ARTBOARD_2;
        }
        if (gamepad1.xWasPressed()){
            currentArtboard = GoBildaPrismDriver.Artboard.ARTBOARD_3;
        }
        if (gamepad1.leftBumperWasPressed()){
            currentArtboard = GoBildaPrismDriver.Artboard.ARTBOARD_4;
        }
        if (gamepad1.rightBumperWasPressed()){
            currentArtboard = GoBildaPrismDriver.Artboard.ARTBOARD_5;
        }
        if (gamepad1.leftStickButtonWasPressed()){
            currentArtboard = GoBildaPrismDriver.Artboard.ARTBOARD_6;
        }
        if (gamepad1.rightStickButtonWasPressed()){
            currentArtboard = GoBildaPrismDriver.Artboard.ARTBOARD_7;
        }
        beamBreaks.setArtboard(currentArtboard);
        telemetry.addLine("ascending order from 0-7: a, b, y, x, l, r, left stick, right stick");
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
