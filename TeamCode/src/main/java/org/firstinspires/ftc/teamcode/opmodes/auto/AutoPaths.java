package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class AutoPaths {
    public Pose start, shoot, farLowHPCollect, farHighHPCollect, gateCollect, spikeMarkTop, spikeMarkMiddle, spikeMarkBottom;
    public PathChain startToShoot, shootToFarLowHPCollect, shootToFarHighHPCollect, farHighHPCollectToShoot, farLowHPCollectToShoot, shootToSpikeMarkTop,shootToSpikeMarkMiddle, shootToSpikeMarkBottom, shootToGateCollect, spikeMarkTopToShoot, spikeMarkMiddleToShoot,spikeMarkBottomToShoot, gateCollectToShoot;

    public AutoPaths(Follower follower, boolean isRed, boolean far) {
        farLowHPCollect = new Pose(10, 4, Math.toRadians(180));
        farHighHPCollect = new Pose(10,25.000, Math.toRadians(180));
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
                farLowHPCollect.mirror();
                farHighHPCollect.mirror();
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
            shootToFarLowHPCollect = follower.pathBuilder()
                    .addPath(new BezierLine(
                            shoot,
                            farLowHPCollect
                    ))
                    .setLinearHeadingInterpolation(shoot.getHeading(), farLowHPCollect.getHeading())
                    .build();
            shootToFarHighHPCollect = follower.pathBuilder()
                    .addPath(new BezierLine(
                            shoot,
                            farHighHPCollect
                    ))
                    .setLinearHeadingInterpolation(shoot.getHeading(), farHighHPCollect.getHeading())
                    .build();
            farHighHPCollectToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            farHighHPCollect,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(farHighHPCollect.getHeading(), shoot.getHeading())
                    .build();
            farLowHPCollectToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            farLowHPCollect,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(farLowHPCollect.getHeading(), shoot.getHeading())
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
            shootToSpikeMarkBottom = follower.pathBuilder()
                    .addPath(new BezierLine(
                            shoot,
                            spikeMarkBottom
                    ))
                    .setLinearHeadingInterpolation(shoot.getHeading(), spikeMarkBottom.getHeading())
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
            spikeMarkTopToShoot = follower.pathBuilder()
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
            spikeMarkBottomToShoot = follower.pathBuilder()
                    .addPath(new BezierLine(
                            spikeMarkBottom,
                            shoot
                    ))
                    .setLinearHeadingInterpolation(spikeMarkBottom.getHeading(), shoot.getHeading())
                    .build();
        }
    }
}





