package org.firstinspires.ftc.teamcode.opmodes.auto;

import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.robot.subsystems.HuskyLens;


public class Auto{

    Command humanPlayerZoneTo, humanPlayerZoneBack;
    Robot robot = new Robot();
    protected boolean isRed;
    AutoPaths autoPaths;

    public Auto(boolean isRed) {
        this.isRed = isRed;
    }
    
    public Command RedFar() {
        return sequential(
                follow(robot.follower, autoPaths.startToShoot, true),
                robot.fastShoot,//! may need to be slowShoot
                waitMs(500),
                follow(robot.follower, autoPaths.shootToSpikeMarkBottom),
                follow(robot.follower, autoPaths.spikeMarkBottomToShoot),
                robot.fastShoot,//! may need to be slowShoot
                waitMs(2000),
                determineSide(),//TODO make rotate to 90 to get reading
                humanPlayerZoneTo,
                humanPlayerZoneBack,
                robot.fastShoot,//! may need to be slowShoot
                determineSide(),//TODO make rotate to 90 to get reading
                humanPlayerZoneTo,
                humanPlayerZoneBack,
                robot.fastShoot,//! may need to be slowShoot
                determineSide(),//TODO make rotate to 90 to get reading
                humanPlayerZoneTo,
                humanPlayerZoneBack,
                robot.fastShoot,//! may need to be slowShoot
                );
    }

    public Command determineSide() {
        double sNumber = HuskyLens.sideNumber();
        if (sNumber == -1){// high
            humanPlayerZoneTo = follow(robot.follower, autoPaths.shootToFarHighHPCollect);
            humanPlayerZoneBack = follow(robot.follower, autoPaths.farHighHPCollectToShoot, true);
        } else { // low
            humanPlayerZoneTo = follow(robot.follower, autoPaths.shootToFarLowHPCollect);
            humanPlayerZoneBack = follow(robot.follower, autoPaths.farLowHPCollectToShoot, true);
        }
        return null;
    }
}