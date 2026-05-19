package org.firstinspires.ftc.teamcode;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.util.Timing;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

import java.util.concurrent.TimeUnit;

public class Robot {
    public Intake in;
    public Shooter fly;
    Timing.Timer shootTimer = new Timing.Timer(700, TimeUnit.MILLISECONDS);
    boolean waitForOpen;


    Command shoot = Command.build()
            .setStart(() -> {
                in.stop();
                waitForOpen = fly.gateIsClosed();
                if (waitForOpen){
                    fly.openGate();
                }
            })
            .setExecute(() -> {
                if (waitForOpen) {
                    if (shootTimer.elapsedTime() > 300){
                        waitForOpen = false;
                        shootTimer.start();
                    }
                } else {
                    in.spinIn();
                }

            })
            .setDone(() -> shootTimer.done())
            .setEnd(endCondition -> {
                in.stop();
                fly.closeGate();
            })
            .requiring(in, fly)
            //Add conflicting behaviors here and priority
            ;

    public void init(){
        in = new Intake();
        fly = new Shooter();
    }

}
