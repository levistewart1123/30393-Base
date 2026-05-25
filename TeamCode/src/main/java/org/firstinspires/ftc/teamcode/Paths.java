package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class Paths {


    public final PathChain startToShoot;
    //etc

    public Paths(Follower follower, boolean isRed) {
        Pose start = new Pose(134, 139);
        Pose shoot = new Pose(134, 139);
        if (isRed){
            start.mirror();
            shoot.mirror();
        }
        startToShoot = follower.pathBuilder()
                .addPath(new BezierLine(
                        start,
                        shoot
                ))
                .setLinearHeadingInterpolation(Math.toRadians(180 - 216), Math.toRadians(180 - 300))
                .build();
        //etc
    }
}





