package org.firstinspires.ftc.teamcode.robot;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.PoseSaver;
import org.firstinspires.ftc.teamcode.robot.pedroPathing.Constants;


import java.util.List;

/**
 * This holds all of our subsystem classes and puts together Commands using them.
 */
@Configurable
public class Robot {
    public boolean slowDrive = false;
    public Follower follower;
    public boolean isRed;


    public void initialize(boolean isRed, HardwareMap hwMap) {
        List<LynxModule> allHubs = hwMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO); //we can try setting this to manual and see how much loop times improve
        }

        follower = Constants.createFollower(hwMap);

        this.isRed = isRed;
        if (PoseSaver.autoWasRun) {
            follower.setStartingPose(PoseSaver.endPose);
        } else {
            follower.setStartingPose(new Pose(10, 10, 0));
        }
        PoseSaver.autoWasRun = false;
        follower.update();
    }

    public void update() {
        follower.update();
    }


}
