package org.firstinspires.ftc.teamcode.robot.subsystems;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

public class Intake {
    private MotorEx intake;
    public ServoEx left, right; //!Public
    private final double lUpPos = 0;
    private final double rUpPos = 0;
    private final double lDownPos = 0;
    private final double rDownPos = 0;

    public void initialize(HardwareMap hwMap) {
        intake = new MotorEx(hwMap, "Intake", Motor.GoBILDA.RPM_1150);
        left = new ServoEx(hwMap, "IntakeServoL");
        right = new ServoEx(hwMap, "IntakeServoR");
        intake.setCachingTolerance(0.02);
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

    public Command lift = instant(() -> {
        left.set(lUpPos);
        right.set(rUpPos);
    }
    );
    public Command lower = instant(() -> {
                left.set(lDownPos);
                right.set(rDownPos);
            }
    );

}
