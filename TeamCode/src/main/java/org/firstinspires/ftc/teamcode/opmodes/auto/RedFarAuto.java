package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red Far", group = "far", preselectTeleOp = "Red TeleOp")
public class RedFarAuto extends BaseFarAuto {
    public RedFarAuto() {
        super(true);
    }
}