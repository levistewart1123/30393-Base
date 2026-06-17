package org.firstinspires.ftc.teamcode.opmodes.auto;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.commands.Commands.waitUntil;
import static com.pedropathing.ivy.groups.Groups.deadline;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.groups.Groups.repeat;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.PoseSaver;
import org.firstinspires.ftc.teamcode.opmodes.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class BaseCloseAuto extends CommandOpMode {
    protected Robot robot = new Robot();
    protected boolean isRed;
    public BaseCloseAuto(boolean isRed){
        this.isRed = isRed;
    }
    public Pose start, shoot, gateCollect, spikeMarkTop, spikeMarkMiddle, spikeMarkBottom, midSpikeControl, midShootControl, topSpikeControl;
    public PathChain startToShoot, shootToSpikeMarkTop, shootToSpikeMarkMiddle, shootToSpikeMarkBottom, shootToGateCollect, spikeMarkTopToShoot, spikeMarkMiddleToShoot, spikeMarkBottomToShoot, gateCollectToShoot;

    protected Command startIntaking = parallel(
            robot.shooter.close,
            robot.intake.setIn
    );
    protected Command prepareShoot = sequential(
            robot.intake.turnOff,
            waitMs(500),
            robot.shooter.open
    );

    public void buildPaths(boolean isRed) {

        gateCollect = new Pose(14.4, 58.2, Math.toRadians(144.9));
        spikeMarkTop = new Pose(22.1, 82.7, Math.toRadians(180));
        spikeMarkMiddle = new Pose(15.0, 55.5, Math.toRadians(180));
        spikeMarkBottom = new Pose(11.6, 40, Math.toRadians(180));

        start = new Pose(22.3, 120.2, Math.toRadians(139.4));
        shoot = new Pose(58, 72.9,Math.toRadians(130));
        midSpikeControl = new Pose(50, 58).mirror();
        midShootControl = new Pose(50, 58).mirror();
        topSpikeControl = new Pose(56,85).mirror();



        //Pose FinalShoot = new Pose(47.4,115.0, Math.toRadians(150.4));
        if (!isRed) {
            start.mirror();
            shoot.mirror();
            gateCollect.mirror();
            spikeMarkTop.mirror();
            spikeMarkMiddle.mirror();
            spikeMarkBottom.mirror();

            midSpikeControl.mirror();
            midShootControl.mirror();
            topSpikeControl.mirror();
        }

        robot.follower.setPose(start);

        startToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        start,
                        shoot
                ))
                .setLinearHeadingInterpolation(start.getHeading(), shoot.getHeading())
                .build();
        shootToSpikeMarkTop = robot.follower.pathBuilder()
                .addPath(new BezierCurve(
                        shoot,
                        topSpikeControl,
                        spikeMarkTop
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), spikeMarkTop.getHeading())
                .build();
        shootToSpikeMarkMiddle = robot.follower.pathBuilder()
                .addPath(new BezierCurve(
                        shoot,
                        midSpikeControl,
                        spikeMarkMiddle
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), spikeMarkMiddle.getHeading())
                .build();
        shootToSpikeMarkBottom = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        shoot,
                        spikeMarkBottom
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), spikeMarkBottom.getHeading())
                .build();
        shootToGateCollect = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        shoot,
                        gateCollect
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), gateCollect.getHeading())
                .build();
        gateCollectToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        gateCollect,
                        shoot
                ))
                .setLinearHeadingInterpolation(gateCollect.getHeading(), shoot.getHeading())
                .build();
        spikeMarkTopToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        spikeMarkTop,
                        shoot
                ))
                .setLinearHeadingInterpolation(spikeMarkTop.getHeading(), shoot.getHeading())
                .build();
        spikeMarkMiddleToShoot = robot.follower.pathBuilder()
                .addPath(new BezierCurve(
                        spikeMarkMiddle,
                        midShootControl,
                        shoot
                ))
                .setLinearHeadingInterpolation(spikeMarkMiddle.getHeading(), shoot.getHeading())
                .build();
        spikeMarkBottomToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        spikeMarkBottom,
                        shoot
                ))
                .setLinearHeadingInterpolation(spikeMarkBottom.getHeading(), shoot.getHeading())
                .build();
    }
    @Override
    public void init() {
        super.init();
        robot.initialize(isRed, hardwareMap);
        buildPaths(isRed);
        robot.follower.setPose(start);
    }

    @Override
    public void start() {
        schedule(sequential(
                robot.shooter.open,
                parallel(waitMs(3000), follow(robot.follower, startToShoot)),
                robot.fastShoot,
                startIntaking,
                follow(robot.follower, shootToSpikeMarkMiddle),
                parallel(follow(robot.follower, spikeMarkMiddleToShoot), prepareShoot),
                robot.fastShoot,
                repeat(sequential(
                        startIntaking,
                        deadline(
                            race(waitMs(5000), waitUntil(() -> robot.beamBreaks.getBallCount() == 3)), //todo change to waitUntil with beam breaks
                            follow(robot.follower, shootToGateCollect)
                        ),
                        parallel(follow(robot.follower, gateCollectToShoot), prepareShoot),
                        robot.fastShoot
                        ),
                        3),
                startIntaking,
                follow(robot.follower, shootToSpikeMarkTop),
                parallel(follow(robot.follower, spikeMarkTopToShoot), prepareShoot),
                robot.fastShoot
                )
        );
        super.start();
    }

    @Override
    public void loop() {
        robot.update(0,0,0);
        super.loop();
    }

    @Override
    public void stop() {
        PoseSaver.autoWasRun = true;
        PoseSaver.endPose = robot.follower.getPose();
        super.stop();
    }
}
