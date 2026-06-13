package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.util.Timing;

import org.firstinspires.ftc.teamcode.robot.subsystems.Prism.GoBildaPrismDriver;

import java.util.concurrent.TimeUnit;


public class BeamBreaks {
    public TouchSensor top;
    public TouchSensor middle;
    public TouchSensor bottom;
    GoBildaPrismDriver prism;
    boolean timerRun = false;

    private final Timing.Timer timer = new Timing.Timer(300, TimeUnit.MILLISECONDS);
    boolean topWasPressed, middleWasPressed, bottomWasPressed = false;


    public void initialize(HardwareMap hwMap){
        prism = hwMap.get(GoBildaPrismDriver.class, "prism");
        top = hwMap.get(TouchSensor.class, "Beam Break Top");//robot todo wiring?
        middle = hwMap.get(TouchSensor.class, "Beam Break Middle");
        bottom = hwMap.get(TouchSensor.class, "Beam Break Bottom");
    }

    /**
     * sets SECTR lights (don't use with periodic)
     */
    public void auraFarm(){
        prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_7);
    }

    public int getBallCount(){
        int ballAmount = 0;
        if (bottom.isPressed() && topWasPressed && middleWasPressed){
            if (!timerRun && timer.done()){
                timer.start();
                timerRun = true;
            }
            if (timer.done()) {
                bottomWasPressed = true;
            }
        }
        if (middle.isPressed() && topWasPressed){
            middleWasPressed = true;
        }
        if (top.isPressed()){
            topWasPressed = true;
        }
        if (topWasPressed){
            ballAmount++;
        }
        if (middleWasPressed){
            ballAmount++;
        }
        if (bottomWasPressed){
            ballAmount++;
        }
        return ballAmount;
    }

    public void reset(){
        topWasPressed = false;
        middleWasPressed = false;
        bottomWasPressed = false;
        timerRun = false;
    }
    public void clearPrism(){
        prism.clearAllAnimations();
    }

    public void updatePrism(boolean shooting, boolean autoAim){
        int balls = getBallCount();
        if (shooting){
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_6); //lab todo change artboards
        } else if (autoAim) {
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_5);
        } else if (balls == 3){
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_4);
        } else if (balls == 2) {
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_3);
        } else if (balls == 1) {
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_2);
        } else if (balls == 0) {
            prism.loadAnimationsFromArtboard(GoBildaPrismDriver.Artboard.ARTBOARD_0);
        }
    }
}
