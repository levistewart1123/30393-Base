package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;
import com.seattlesolvers.solverslib.util.InterpLUT;

import org.firstinspires.ftc.teamcode.Constants;

import java.util.Objects;

public class Shooter {

    public enum states{
        AUTOMATIC,
        FIXED,
        OFF
    }

    states state = states.AUTOMATIC;

    private MotorGroup flywheels;
    private ServoEx gate;
    private ServoEx hood;
    final double FIXED_SPEED = 0.5;

    public double targetRPM = 0;

    InterpLUT velocities = new InterpLUT();
    InterpLUT angles = new InterpLUT();

    boolean launching = false;

    public void init(HardwareMap hwMap){
        flywheels = new MotorGroup(
                new MotorEx(hwMap, "FlywheelLeft", Motor.GoBILDA.NONE.getCPR(), 4825).setInverted(true),
                new MotorEx(hwMap, "FlywheelRight", Motor.GoBILDA.NONE.getCPR(), 4825)
        );
        flywheels.setRunMode(Motor.RunMode.VelocityControl);

        flywheels.setVeloCoefficients(0.0015, 0, 0);
        flywheels.setFeedforwardCoefficients(0, 1.45); //!use recalc

        gate = hwMap.get(ServoEx.class, "Gate");
        gate.setInverted(true);

        hood = hwMap.get(ServoEx.class, "Hood");
        hood.setInverted(true);

        velocities.add(1, 1); //TODO: replace this
        velocities.createLUT();
        angles.add(1, 1); //TODO: replace this
        angles.createLUT();

        state = states.AUTOMATIC;
    }

    public void openGate(){
        gate.set(Constants.GATE_OPEN_POS);
    }

    public void closeGate(){
        gate.set(Constants.GATE_CLOSED_POS);
    }

    public boolean gateIsOpen(){
        return gate.get() == Constants.GATE_OPEN_POS; //FIXME this doesn't use the encoder I think
    }
    public boolean gateIsClosed(){
        return gate.get() == Constants.GATE_CLOSED_POS; //FIXME this doesn't use the encoder I think
    }

    //testing

//?uncertain if this will be used, commenting it out now

//    public void periodic(double distance){
//        if (state == states.AUTOMATIC) {
//            targetRPM = velocities.get(distance);
//            flywheels.set(targetRPM / flywheels.getMaxRPM());
//            hood.set(angles.get(distance));
//        }
//    }
//    public void changeState(states state){
//        this.state = state;
//        switch(state){
//            case OFF:
//                flywheels.set(0);
//                break;
//            case FIXED:
//                flywheels.set(FIXED_SPEED);
//                hood.set(0);
//                break;
//        }
//
//    }


}
