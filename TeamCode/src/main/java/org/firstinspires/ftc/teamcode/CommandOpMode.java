package org.firstinspires.ftc.teamcode;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.util.Timing;

import org.firstinspires.ftc.teamcode.robot.Robot;

import java.util.concurrent.TimeUnit;

public class CommandOpMode extends OpMode {
    protected int loops = 0;
    protected int secondLoops = 0;
    protected int storedLoops = 0;
    protected Timing.Stopwatch loopTimer = new Timing.Stopwatch(TimeUnit.MILLISECONDS);
    protected Timing.Timer secTimer = new Timing.Timer(1000, TimeUnit.MILLISECONDS);
    public void reset() {
        Scheduler.reset();
    }

    /**
     * Schedules objects to the scheduler
     */
    public void schedule(Command... commands) {
        Scheduler.schedule(commands);
    }

    @Override
    public void init() {
    }

    @Override
    public void start() {
        loopTimer.start();
    }

    @Override
    public void loop() {
        secondLoops++;
        loops++;
        if (secTimer.done()){
            storedLoops = secondLoops;
            secondLoops = 0;
            secTimer.start();
        }
        telemetry.addData("this loop time: ", loopTimer.deltaTime());
        telemetry.addData("average loop time: ", (loopTimer.elapsedTime()/loops));
        telemetry.addData("loops per second (approximate)", secondLoops);
        Scheduler.execute();
    }

    public void stop() {
        reset();
    }
}
