package org.firstinspires.ftc.teamcode.robot.subsystems;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

public class Intake {
    private MotorEx intake;
    private ServoEx left, right;

    public void initialize(HardwareMap hwMap) {
        intake = new MotorEx(hwMap, "Intake", Motor.GoBILDA.RPM_1150);
        left = new ServoEx(hwMap, "IntakeServoL");
        right = new ServoEx(hwMap, "IntakeServoR");
        intake.setCachingTolerance(0.02);
        left.setCachingTolerance(0.02);
        right.setCachingTolerance(0.02);
    }


    public void spinIn() {
        intake.set(1);
    }

    public void spinOut() {
        intake.set(-1);
    }

    public void stop() {
        intake.set(0);
    }

    public double getSpeed() {
        return intake.get();
    }

    public Command setIn = instant(() -> intake.set(1));
    public Command setOut = instant(() -> intake.set(-1));
    public Command turnOff = instant(() -> intake.set(0));

    public Command run(double power) {
        return instant(() -> intake.set(power));
    }

    public void lift(){
        double l_UP = 0.95;
        left.set(l_UP);
        double r_UP = 0.3;
        right.set(r_UP);
    }

    public void lower(){
        double l_DOWN = 0.5;
        left.set(l_DOWN);
        double r_DOWN = 0;
        right.set(r_DOWN);
    }

    public Command lift = instant(this::lift)
            .requiring(this);

    public Command lower = instant(this::lower)
            .requiring(this);

}
