package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AutoPaths;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.robot.subsystems.HuskyLens;

public class FarBlue extends OpMode {

    Robot robot = new Robot();
    int step = 1;
    protected boolean isRed;
    AutoPaths autoPaths;
    boolean rightSide;

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
                robot.follower.followPath(autoPaths.shootToSpikeMarkBottom);
                break;
            case 3:
                robot.follower.followPath(autoPaths.spikeMarkBottomToShoot);
                break;
            case 4:
                rightSide = HuskyLens.determineSide();
                if (rightSide){
                    robot.follower.followPath(autoPaths.shootToFarRightCollect);
                    break;
                } else {
                    robot.follower.followPath(autoPaths.shootToFarLeftCollect);
                    break;
                }
            case 5:
                if (rightSide){
                    robot.follower.followPath(autoPaths.farHumanPlayerCollectToShoot);
                } else {
                    robot.follower.followPath(autoPaths.farLeftCollectToShoot);
                }
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
