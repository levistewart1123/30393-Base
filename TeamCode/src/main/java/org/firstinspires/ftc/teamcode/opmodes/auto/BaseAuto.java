package org.firstinspires.ftc.teamcode.opmodes.auto;

import org.firstinspires.ftc.teamcode.opmodes.CommandOpMode;
import org.firstinspires.ftc.teamcode.robot.Robot;

public class BaseAuto extends CommandOpMode {
    protected Robot robot = new Robot();
    protected boolean isRed;
    public BaseAuto(boolean isRed){
        this.isRed = isRed;
    }

    public void buildPaths(){

    }

    @Override
    public void init() {
        super.init();
        buildPaths();
        robot.initialize(isRed, hardwareMap);
    }

    @Override
    public void start() {
        robot.update();
        super.start();
    }

    @Override
    public void loop() {
        robot.update();
        super.loop();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
