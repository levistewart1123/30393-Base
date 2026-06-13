package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red close", group = "close", preselectTeleOp = "Red TeleOp")
public class RedCloseAuto extends BaseCloseAuto {
    public RedCloseAuto() {
        super(true);
    }
}
