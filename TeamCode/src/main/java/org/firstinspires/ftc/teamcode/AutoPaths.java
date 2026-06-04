package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class AutoPaths {
    public Pose start, shoot, farHumanPlayerCollect, farHighHumanPlayerCollect, gateCollect, spikeMarkTop, spikeMarkMiddle, spikeMarkBottom;
    public PathChain startToShoot, shootToFarRightCollect, shootToFarLeftCollect, farLeftCollectToShoot, farHumanPlayerCollectToShoot, shootToSpikeMarkTop,shootToSpikeMarkMiddle, shootToGateCollect, spikeMarkOneToShoot, spikeMarkMiddleToShoot, gateCollectToShoot;

    public AutoPaths(Follower follower, boolean isRed, boolean far) {
        farHumanPlayerCollect = new Pose(10, 4, Math.toRadians(180));
        farHighHumanPlayerCollect = new Pose(10,25.000, Math.toRadians(180));
        gateCollect = new Pose(14.4, 58.2, Math.toRadians(144.9));
        spikeMarkTop = new Pose(22.1, 82.7, Math.toRadians(180));
        spikeMarkMiddle = new Pose(15.0, 56.5, Math.toRadians(180));
        spikeMarkBottom = new Pose(11.6, 40, Math.toRadians(180));

        if (far) {// When far
            start = new Pose(55.6, 7.2, Math.toRadians(90));
            shoot = new Pose(56.9, 20.6, Math.toRadians(118.5));

            if (isRed) {
                start.mirror();
                shoot.mirror();
                farHumanPlayerCollect.mirror();
                farHighHumanPlayerCollect.mirror();
                gateCollect.mirror();
                spikeMarkTop.mirror();
                spikeMarkMiddle.mirror();
                spikeMarkBottom.mirror();
            }
            startToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            start,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(start.getHeading(), shoot.getHeading())
                    .build();
            shootToFarRightCollect = follower.pathBuilder()
                    .addPath(new BezierLine(
                            shoot,
                            farHumanPlayerCollect
                    ))
                    .setLinearHeadingInterpolation(shoot.getHeading(), farHumanPlayerCollect.getHeading())
                    .build();
            shootToFarLeftCollect = follower.pathBuilder()
                    .addPath(new BezierLine(
                            shoot,
                            farHighHumanPlayerCollect
                    ))
                    .setLinearHeadingInterpolation(shoot.getHeading(), farHighHumanPlayerCollect.getHeading())
                    .build();
            farLeftCollectToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            farHighHumanPlayerCollect,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(farHighHumanPlayerCollect.getHeading(), shoot.getHeading())
                    .build();
            farHumanPlayerCollectToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            farHumanPlayerCollect,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(farHumanPlayerCollect.getHeading(), shoot.getHeading())
                    .build();
        } else if (!far) {// When close
            start = new Pose(22.3, 120.2, Math.toRadians(139.4));
            shoot = new Pose(59.6, 72.9,Math.toRadians(130));

            if (isRed){
                start.mirror();
                shoot.mirror();
                gateCollect.mirror();
                spikeMarkTop.mirror();
                spikeMarkMiddle.mirror();
            }
            startToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            start,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(start.getHeading(), shoot.getHeading())
                    .build();
            shootToSpikeMarkTop = follower.pathBuilder()
                    .addPath(new BezierLine(
                            shoot,
                            spikeMarkTop
                    ))
                    .setLinearHeadingInterpolation(shoot.getHeading(), spikeMarkTop.getHeading())
                    .build();
            shootToSpikeMarkMiddle = follower.pathBuilder()
                    .addPath(new BezierLine(
                            shoot,
                            spikeMarkMiddle
                    ))
                    .setLinearHeadingInterpolation(shoot.getHeading(), spikeMarkMiddle.getHeading())
                    .build();
            shootToGateCollect = follower.pathBuilder()
                    .addPath(new BezierLine(
                            shoot,
                            gateCollect
                    ))
                    .setLinearHeadingInterpolation(shoot.getHeading(), gateCollect.getHeading())
                    .build();
            gateCollectToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            gateCollect,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(gateCollect.getHeading(), shoot.getHeading())
                    .build();
            spikeMarkOneToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            spikeMarkTop,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(spikeMarkTop.getHeading(), shoot.getHeading())
                    .build();
            spikeMarkMiddleToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            spikeMarkMiddle,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(spikeMarkMiddle.getHeading(), shoot.getHeading())
                    .build();
        }
    }
}





