package org.firstinspires.ftc.teamcode.opmodes.auto;

import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.opmodes.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.robot.subsystems.HuskyLens;

@Deprecated

public class OldBaseFarAuto extends CommandOpMode {
    public OldBaseFarAuto(boolean isRed) {
        this.isRed = isRed;
    }
    Robot robot = new Robot();

    protected boolean isRed;

    public Pose start, shoot, farLowHPCollect, farHighHPCollect, gateCollect, spikeMarkTop, spikeMarkMiddle, spikeMarkBottom, sideDetermine;
    public PathChain startToShoot, sideDetermineToFarLowHPCollect, sideDetermineToFarHighHPCollect, farHighHPCollectToShoot, farLowHPCollectToShoot, shootToSpikeMarkTop,shootToSpikeMarkMiddle, shootToSpikeMarkBottom, shootToGateCollect, spikeMarkTopToShoot, spikeMarkMiddleToShoot,spikeMarkBottomToShoot, gateCollectToShoot, shootToSideDetermine;

    Command humanPlayerZoneTo;
    Command humanPlayerZoneBack;
    public void buildPaths(boolean isRed) {
        farLowHPCollect = new Pose(10, 4, Math.toRadians(180));
        farHighHPCollect = new Pose(10,25.000, Math.toRadians(180));
        gateCollect = new Pose(14.4, 58.2, Math.toRadians(144.9));
        spikeMarkTop = new Pose(22.1, 82.7, Math.toRadians(180));
        spikeMarkMiddle = new Pose(15.0, 56.5, Math.toRadians(180));
        spikeMarkBottom = new Pose(11.6, 40, Math.toRadians(180));

        start = new Pose(55.6, 7.2, Math.toRadians(90));
        shoot = new Pose(56.9, 20.6, Math.toRadians(118.5));
        sideDetermine = new Pose(56.9, 20.6, Math.toRadians(180));

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

        robot.follower.setPose(start);

        startToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        start,
                        shoot
                ))
                .setLinearHeadingInterpolation(start.getHeading(), shoot.getHeading())
                .build();
        sideDetermineToFarLowHPCollect = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        shoot,
                        farLowHPCollect
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), farLowHPCollect.getHeading())
                .build();
        sideDetermineToFarHighHPCollect = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        shoot,
                        farHighHPCollect
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), farHighHPCollect.getHeading())
                .build();
        farHighHPCollectToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        farHighHPCollect,
                        shoot
                ))
                .setLinearHeadingInterpolation(farHighHPCollect.getHeading(), shoot.getHeading())
                .build();
        farLowHPCollectToShoot = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        farLowHPCollect,
                        shoot
                ))
                .setLinearHeadingInterpolation(farLowHPCollect.getHeading(), shoot.getHeading())
                .build();
        shootToSideDetermine = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        shoot,
                        sideDetermine
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), sideDetermine.getHeading())
                .build();

        shootToSpikeMarkTop = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        shoot,
                        spikeMarkTop
                ))
                .setLinearHeadingInterpolation(shoot.getHeading(), spikeMarkTop.getHeading())
                .build();
        shootToSpikeMarkMiddle = robot.follower.pathBuilder()
                .addPath(new BezierLine(
                        shoot,
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
                .addPath(new BezierLine(
                        spikeMarkMiddle,
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

    public Command determineSide = instant(() -> {
                double sNumber = HuskyLens.sideNumber();
                if (sNumber == -1){// high
                    humanPlayerZoneTo = follow(robot.follower, sideDetermineToFarHighHPCollect);
                    humanPlayerZoneBack = follow(robot.follower, farHighHPCollectToShoot, true);
                } else { // low
                    humanPlayerZoneTo = follow(robot.follower, sideDetermineToFarLowHPCollect);
                    humanPlayerZoneBack = follow(robot.follower, farLowHPCollectToShoot, true);
                }
            }
            );

    protected Command run(){
        return sequential(
                //! add Flywheel spinup
                follow(robot.follower, startToShoot, true),
                //waitUntil(robot.shooter.getFlywheelVelocity()> robot.shooter.targetRPM), //! Flywheel to be at speed
                robot.shoot, //* may need to be slow Shoot
                robot.intake.setIn,
                follow(robot.follower, shootToSpikeMarkBottom),
                robot.intake.run(0.5),
                follow(robot.follower, spikeMarkBottomToShoot),
                robot.shoot, //* may need to be slow Shoot
                //waitUntil(robot.isShooting == false), //! robot.isShooting is not BooleanSupplier
                robot.intake.setIn,
                follow(robot.follower, sideDetermineToFarLowHPCollect),
                robot.intake.run(0.5),
                follow(robot.follower, farLowHPCollectToShoot),
                robot.shoot, //* may need to be slow Shoot
                follow(robot.follower, shootToSideDetermine),
                determineSide,//TODO make rotate to 90 to get reading
                robot.intake.setIn,
                humanPlayerZoneTo,
                robot.intake.run(0.5),
                humanPlayerZoneBack,
                robot.shoot, //* may need to be slow Shoot
                follow(robot.follower, shootToSideDetermine),
                determineSide,//TODO make rotate to 90 to get reading
                robot.intake.setIn,
                humanPlayerZoneTo,
                robot.intake.run(0.5),
                humanPlayerZoneBack,
                robot.shoot, //* may need to be slow Shoot
                follow(robot.follower, shootToSideDetermine),
                determineSide,//TODO make rotate to 90 to get reading
                robot.intake.setIn,
                humanPlayerZoneTo,
                robot.intake.run(0.5),
                humanPlayerZoneBack,
                robot.shoot
        );//* may need to be slow Shoot
    }

    @Override
    public void init() {
        super.init();
        robot.initialize(true, hardwareMap);
        buildPaths(isRed);
        humanPlayerZoneTo = follow(robot.follower, sideDetermineToFarHighHPCollect);
        humanPlayerZoneBack = follow(robot.follower, farHighHPCollectToShoot, true);
        determineSide = instant(() -> {
                    double sNumber = HuskyLens.sideNumber();
                    if (sNumber == -1){// high
                        humanPlayerZoneTo = follow(robot.follower, sideDetermineToFarHighHPCollect);
                        humanPlayerZoneBack = follow(robot.follower, farHighHPCollectToShoot, true);
                    } else { // low
                        humanPlayerZoneTo = follow(robot.follower, sideDetermineToFarLowHPCollect);
                        humanPlayerZoneBack = follow(robot.follower, farLowHPCollectToShoot, true);
                    }
                }
        );
    }

    @Override
    public void start() {
        schedule(run());
        super.start();
    }

    @Override
    public void loop() {
        robot.update(0,0,0);
        telemetry.addData("x", robot.follower.getPose().getX());
        telemetry.addData("y", robot.follower.getPose().getY());
        telemetry.addData("heading", robot.follower.getPose().getHeading());
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }
}