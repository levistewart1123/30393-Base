package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.pedropathing.ftc.InvertedFTCCoordinates;
import com.pedropathing.ftc.PoseConverter;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class Limelight {
    public Limelight3A limelight3A;
    private LLResult result;

    public void initialize(HardwareMap hwMap) {
        limelight3A = hwMap.get(Limelight3A.class, "limelight");
        limelight3A.setPollRateHz(100);
        limelight3A.pipelineSwitch(0);
        limelight3A.start();
    }

    public void setPipeline(int pipeline) {
        limelight3A.pipelineSwitch(pipeline);
    }

    public void update() {
        result = limelight3A.getLatestResult();
    }

    public Pose getMT1PosePedro() {
        update();
        if (result != null && result.isValid()) {
            Pose3D mt1Pose = result.getBotpose();
            Pose2D limelightPose2D = new Pose2D(
                    mt1Pose.getPosition().unit,
                    mt1Pose.getPosition().x,
                    mt1Pose.getPosition().y,
                    AngleUnit.DEGREES,
                    mt1Pose.getOrientation().getYaw()
            );
            return PoseConverter.pose2DToPose(limelightPose2D, InvertedFTCCoordinates.INSTANCE);
        } else {
            return null;
        }
    }
}