package org.firstinspires.ftc.teamcode.opmodes.auto;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.commands.Commands.waitUntil;
import static com.pedropathing.ivy.groups.Groups.deadline;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.seattlesolvers.solverslib.util.MathUtils.normalizeAngle;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.PoseSaver;
import org.firstinspires.ftc.teamcode.opmodes.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class BaseCloseAuto extends CommandOpMode {
    protected Robot robot = new Robot();
    protected boolean isRed;
    public BaseCloseAuto(boolean isRed){
        this.isRed = isRed;
    }

    public int middleSpikeMarkStartHeading;

    public Pose start, shoot, leave, gateCollect, spikeMarkTop, spikeMarkMiddle, spikeMarkBottom, midSpikeControl, midShootControl, topSpikeControl, gateControl;
    public PathChain shootToLeave, startToShoot, shootToSpikeMarkTop, shootToSpikeMarkMiddle, shootToSpikeMarkBottom, shootToGateCollect, spikeMarkTopToShoot, spikeMarkMiddleToShoot, spikeMarkBottomToShoot, gateCollectToShoot;

    protected Command startIntaking = parallel(
            robot.shooter.close,
            robot.intake.setIn
    );
    protected Command prepareShoot = sequential(
            robot.intake.turnOff,
            waitMs(500),
            robot.shooter.open
    );
    protected Command gateIntake, gateShoot, gateCycle;

    public double getHeadingToPointsRad(Pose start, Pose end){
        double xDiff = end.getX() - start.getX();
        double yDiff = end.getY() - start.getY();
        double angleFromCoords = Math.atan2(yDiff, xDiff);
        return normalizeAngle(angleFromCoords, false, AngleUnit.RADIANS);
    }

    public void buildPaths(boolean isRed) {

        // blue points
        gateCollect = new Pose(14.4, 60.5, Math.toRadians(150));
        spikeMarkTop = new Pose(21.5, 83.7, Math.toRadians(180));
        spikeMarkMiddle = new Pose(14.0, 55.5, Math.toRadians(180));
        spikeMarkBottom = new Pose(11.6, 40, Math.toRadians(180));
        start = new Pose(22.3, 120.2, Math.toRadians(139.4));
        shoot = new Pose(55, 76, Math.toRadians(130));
        leave = new Pose(55, 69, Math.toRadians(130));

        midSpikeControl = new Pose(35.2, 60);
        midShootControl = new Pose(35.2, 60);
        topSpikeControl = new Pose(50,83.7);
        gateControl = new Pose(40,60.5);
        middleSpikeMarkStartHeading = 170;


        //Pose FinalShoot = new Pose(47.4,115.0, Math.toRadians(150.4));
        if (isRed) {
            start = start.mirror();
            shoot = shoot.mirror();
            gateCollect = new Pose(129.6, 59.2, Math.toRadians(34));//TODO Change This may need to change on game field at event.
            spikeMarkTop = spikeMarkTop.mirror();
            spikeMarkMiddle = spikeMarkMiddle.mirror();
            spikeMarkBottom = spikeMarkBottom.mirror();
            leave = leave.mirror();

            midSpikeControl = midSpikeControl.mirror();
            midShootControl = midShootControl.mirror();
            topSpikeControl = topSpikeControl.mirror();
            gateControl = gateControl.mirror();
            middleSpikeMarkStartHeading = 10;
        }

        robot.follower.setPose(start);

        shootToLeave = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        shoot,
                        leave
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), leave.getHeading())
                .build();
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
                .setLinearHeadingInterpolation(Math.toRadians(middleSpikeMarkStartHeading)/*!not shoot's heading*/, spikeMarkMiddle.getHeading())
                .build();
        shootToSpikeMarkBottom = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        shoot,
                        spikeMarkBottom
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), spikeMarkBottom.getHeading())
                .build();
        shootToGateCollect = robot.follower.pathBuilder()
                .addPath(new BezierCurve(
                        shoot,
                        gateControl,
                        gateCollect
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), gateCollect.getHeading())
//                .setHeadingInterpolation(HeadingInterpolator.piecewise(
//                                new HeadingInterpolator.PiecewiseNode(
//                                        0,
//                                        0.1,
//                                        HeadingInterpolator.linear(shoot.getHeading(), getHeadingToPointsRad(shoot, gateCollect))
//                                ),
//                                new HeadingInterpolator.PiecewiseNode(
//                                        0.1,
//                                        0.6,
//                                        HeadingInterpolator.facingPoint(gateCollect)
//                                ),
//                                new HeadingInterpolator.PiecewiseNode(
//                                        0.6,
//                                        0.75,
//                                        HeadingInterpolator.linear(getHeadingToPointsRad(shoot, gateCollect), gateCollect.getHeading())
//                                ),
//                                new HeadingInterpolator.PiecewiseNode(
//                                        0.75,
//                                        1,
//                                        HeadingInterpolator.constant(gateCollect.getHeading())
//                                )
//                        )
//                )
                .build();
        gateCollectToShoot = robot.follower.pathBuilder()
                .addPath(new BezierCurve(
                        gateCollect,
                        gateControl,
                        shoot
                ))
                .setLinearHeadingInterpolation(gateCollect.getHeading(), shoot.getHeading())
//                .setHeadingInterpolation(HeadingInterpolator.piecewise(
//                                new HeadingInterpolator.PiecewiseNode(
//                                        0,
//                                        0.25,
//                                        HeadingInterpolator.constant(gateCollect.getHeading())
//                                ),
//                                new HeadingInterpolator.PiecewiseNode(
//                                        0.25,
//                                        0.4,
//                                        HeadingInterpolator.linear(gateCollect.getHeading(), getHeadingToPointsRad(shoot, gateCollect))
//                                ),
//                                new HeadingInterpolator.PiecewiseNode(
//                                        0.1,
//                                        0.6,
//                                        HeadingInterpolator.facingPoint(gateCollect)
//                                ),
//                                new HeadingInterpolator.PiecewiseNode(
//                                        0.6,
//                                        1,
//                                        HeadingInterpolator.linear(getHeadingToPointsRad(shoot, gateCollect), shoot.getHeading())
//                                )
//                        )
//                )
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

        gateIntake = sequential(
                startIntaking,
                deadline(
                        race(waitMs(5000), waitUntil(() -> robot.beamBreaks.getBallCount() == 3)), //todo change to waitUntil with beam breaks
                        follow(robot.follower, shootToGateCollect, 0.85)
                )
        );
        gateShoot = sequential(
                parallel(follow(robot.follower, gateCollectToShoot), prepareShoot),
                robot.fastShoot
        );

        gateCycle = sequential(
                gateIntake,
                gateShoot
        );
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
        schedule(
                sequential(
                        race(
                                sequential(
                                        robot.shooter.open,
                                        parallel(waitMs(3000), follow(robot.follower, startToShoot, true)),
                                        robot.fastShoot,
                                        waitMs(100),
                                        startIntaking,
                                        follow(robot.follower, shootToSpikeMarkMiddle),
                                        parallel(follow(robot.follower, spikeMarkMiddleToShoot), prepareShoot),
                                        robot.fastShoot, //8.87
                                        race(
                                                waitMs(13000),
                                                sequential(
                                                        gateIntake,
                                                        gateShoot,
                                                        gateIntake,
                                                        gateShoot,
                                                        gateIntake
                                                )
                                        ),
                                        gateShoot,
                                        startIntaking, //6.62
                                        follow(robot.follower, shootToSpikeMarkTop),
                                        parallel(follow(robot.follower, spikeMarkTopToShoot), prepareShoot),
                                        robot.fastShoot
                                ),
                                waitMs(29500)
                        ),
                        follow(robot.follower, shootToLeave)

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
