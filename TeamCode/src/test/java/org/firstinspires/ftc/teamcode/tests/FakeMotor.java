package org.firstinspires.ftc.teamcode.tests;

import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.wpilibcontroller.SimpleMotorFeedforward;

public class FakeMotor {
    double kP, kI, kD, kS, kV, kA = 0;
    PIDController PID;
    SimpleMotorFeedforward FF;
    double currentPower = 0;
    public void set(double speed) {
        currentPower = PID.calculate(getVelocity(), speed) + FF.calculate(speed);
    }

    private double getVelocity() {
        return currentPower * 9/10;
    }

    public void setPIDCoeffs(double kP, double kI, double kD, double kS, double kV, double kA){
        PID = new PIDController(kP, kI, kD);
        FF = new SimpleMotorFeedforward(kS, kV, kA);
    }
    public double get() {
        return currentPower;
    }
}
