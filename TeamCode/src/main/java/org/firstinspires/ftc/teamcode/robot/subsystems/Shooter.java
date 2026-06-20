package org.firstinspires.ftc.teamcode.robot.subsystems;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;
import com.seattlesolvers.solverslib.util.InterpLUT;

public class Shooter {
    private MotorGroup flywheels;
    private ServoEx gate;
    private ServoEx hood;
    /**
     * if the robot should operate as a close or far shooter. Changes which InterpLUTs are used for hood pos and flywheel RPM.
     */
    public boolean closeMode = true;

    // Example

    /**
     * takes in distance and returns flywheel power
     */
    InterpLUT closeVelocities = new InterpLUT();
    /**
     * takes in distance and returns hood angle
     */
    InterpLUT closeAngles = new InterpLUT();
    /**
     * takes in distance and returns flywheel power
     */
    InterpLUT farVelocities = new InterpLUT();
    /**
     * takes in distance and returns hood angle
     */
    InterpLUT farAngles = new InterpLUT();
    /**
     * takes in distance and returns how long it
     * will take an artifact to reach the goal in seconds.
     */
    public InterpLUT secShotTakes = new InterpLUT();


    public void initialize(HardwareMap hwMap){
        flywheels = new MotorGroup(
                new MotorEx(hwMap, "FlywheelLeft", 28, 4825).setInverted(true),
                new MotorEx(hwMap, "FlywheelRight", 28, 4825)
        );
        flywheels.setRunMode(Motor.RunMode.VelocityControl);

        flywheels.setVeloCoefficients(5, 0, 0);//maybe retune kP
        flywheels.setFeedforwardCoefficients(350, 1.26); //robot todo use recalc

        gate = new ServoEx(hwMap, "Gate");
        gate.setInverted(true);

        hood = new ServoEx(hwMap, "Hood");
        hood.setInverted(true);
        closeVelocities.add(0, 0.52);
        closeVelocities.add(48.5, 0.52);
        closeVelocities.add(60, 0.55);
        closeVelocities.add(73.75, 0.58);
        closeVelocities.add(82.8, 0.6);
        closeVelocities.add(91.7, 0.62); //hood 0.5
        closeVelocities.add(100.6, 0.64); //hood 0.7
        closeVelocities.createLUT();
        closeAngles.add(0, 0.25);
        closeAngles.add(82.8, 0.25);
        closeAngles.add(91.7, 0.5);
        closeAngles.add(100.6, 0.7);
        closeAngles.createLUT();
        farVelocities.add(0, 0.68);
        farVelocities.add(128.2, 0.68);
        farVelocities.add(135, 0.68);
        farVelocities.add(155.08, 0.73);
        farVelocities.createLUT();
        farAngles.add(0, 0.65);
        farAngles.add(129, 0.65);
        farAngles.add(136.83, 0.65);
        farAngles.add(155, 0.65);
        farAngles.createLUT();
        secShotTakes.add(95.3,0.4);
        secShotTakes.add(75,0.4);
        secShotTakes.add(56,0.4);
        secShotTakes.add(113.25,0.4);
        secShotTakes.add(227,0.4);
        secShotTakes.add(200,0.4);
        //secShotTakes.createLUT();
    }

    public double getFlywheelVelocity(){
        return flywheels.getVelocity();
    }


    /**
     * sets if the robot should operate as a close shooter or a far shooter.
     * this changes which InterpLUTs we look at for hood angle and flywheel velocity
     * @param close if it should be close
     * @return a command setting shooter's closeMode
     */
    public Command setClose(boolean close) {
        return instant(() -> closeMode = close)
                .requiring(this)
                .setPriority(0)
                .setBlockedBehavior(BlockedBehavior.QUEUE)
                ;
    }
    public void update(double distance){
        flywheels.set(closeMode ? closeVelocities.get(distance) : farVelocities.get(distance));
        hood.set(closeMode ? closeAngles.get(distance) : farAngles.get(distance));
    }

    public void openGate(){
        gate.set(0);
    }
    public void closeGate(){
        gate.set(1);
    }

    public Command open = instant(() -> gate.set(0));
    public Command close = instant(() -> gate.set(1));

    public void setHood(double position){
        hood.set(position);
    }
    public void runWithPIDF(double power){ //-1 to 1
        flywheels.set(power);
    }
    public boolean isOpen(){
        return gate.get() == 0;
    }
    public void setFlywheelCoeffs(double kP, double kI, double kD, double kS, double kV, double kA){ //0.55, 55.90
        flywheels.setVeloCoefficients(kP, kI, kD);
        flywheels.setFeedforwardCoefficients(kS, kV, kA);
    }

}
