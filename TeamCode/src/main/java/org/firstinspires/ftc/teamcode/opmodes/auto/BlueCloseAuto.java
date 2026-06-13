package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue close", group = "close", preselectTeleOp = "Blue TeleOp")
public class BlueCloseAuto extends BaseCloseAuto {
    public BlueCloseAuto() {
        super(false);
    }
}
