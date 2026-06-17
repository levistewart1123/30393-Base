package org.firstinspires.ftc.teamcode.opmodes.colors;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.auto.BaseCloseAuto;

@Autonomous(name = "Red close", group = "close", preselectTeleOp = "Red TeleOp")
public class RedCloseAuto extends BaseCloseAuto {
    public RedCloseAuto() {
        super(true);
    }
}
