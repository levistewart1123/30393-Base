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
    private final double L_UP = 0.95;
    private final double R_UP = 0.3;
    private final double L_DOWN = 0.5;
    private final double R_DOWN = 0;

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
        left.set(L_UP);
        right.set(R_UP);
    }

    public void lower(){
        left.set(L_DOWN);
        right.set(R_DOWN);
    }

    public Command lift = instant(this::lift)
            .requiring(this);

    public Command lower = instant(this::lower)
            .requiring(this);

}
