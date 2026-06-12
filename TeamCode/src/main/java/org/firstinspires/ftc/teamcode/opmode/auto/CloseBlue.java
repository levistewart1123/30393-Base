package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AutoPaths;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class CloseBlue extends OpMode {

    Robot robot = new Robot();
    int step = 1;
    protected boolean isRed;
    AutoPaths autoPaths;

    @Override
    public void init() {
        Timer pathTimer = new Timer();
        robot.initialize(isRed, hardwareMap);
        autoPaths = new AutoPaths(robot.follower, isRed,false);
    }

    @Override
    public void loop() {
        updatePathState();
        robot.follower.update();
    }

    public void updatePathState(){
        switch (step){
            case 1:
                robot.follower.followPath(autoPaths.startToShoot, 0.5, true);
                break;
            case 2:
                robot.follower.followPath(autoPaths.shootToSpikeMarkMiddle);
                break;
            case 3:
                robot.follower.followPath(autoPaths.spikeMarkMiddleToShoot);
                break;
            case 4:
                robot.follower.followPath(autoPaths.shootToGateCollect);
                break;
            case 5:
                robot.follower.followPath(autoPaths.gateCollectToShoot);
                break;
            case 6:
                robot.follower.followPath(autoPaths.shootToGateCollect);
                break;
            case 7:
                 //TODO add end pose to AutoPaths.
        }
    }

    public void setStep(int Step){
        step = Step;
    }
}
