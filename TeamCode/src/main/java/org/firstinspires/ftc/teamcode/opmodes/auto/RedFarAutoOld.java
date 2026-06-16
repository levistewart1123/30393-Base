package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Far", group = "far", preselectTeleOp = "Red TeleOp")
public class RedFarAutoOld extends NewBaseFarAuto {
    public RedFarAutoOld() {
        super(true);
    }
}