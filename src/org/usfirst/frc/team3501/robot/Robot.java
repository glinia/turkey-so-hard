package org.usfirst.frc.team3501.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;

import org.usfirst.frc.team3501.robot.Joystick;

import static org.usfirst.frc.team3501.robot.Consts.*;

public class Robot extends IterativeRobot {

    private Joystick leftStick  = new Joystick(LEFT_JOYSTICK_PORT),
                     rightStick = new Joystick(RIGHT_JOYSTICK_PORT);

    private Drivetrain drivetrain = new Drivetrain();
    private Intake intake         = new Intake();

    private Compressor compressor = new Compressor(PCM_A);

    public void robotInit() {
        compressor.start();

        CameraServer.getInstance().startAutomaticCapture("cam1");
    }

    public void teleopInit() {
        intake.retract();
    }

    public void teleopPeriodic() {
        buttonsPressed();

        intake.update();
        drive();
    }

    private void drive() {
        if (rightStick.get(2)) {
            double turn = rightStick.getX();

            drivetrain.driveRaw(turn, -turn);
        } else {
            double forward = leftStick.getY();
            double turn    = -rightStick.getX();

            drivetrain.drive(forward, turn);
        }
    }

    private void buttonsPressed() {
        // drivetrain
        if (leftStick.get(1)) {
            drivetrain.shiftHigh();
        } else {
            drivetrain.shiftLow();
        }

        if (leftStick.getToggleButton(7)) {
            drivetrain.flip();
        }

        // intake
        if (rightStick.get(1)) {
            intake.roll(0.7);
        } else if (rightStick.getOne(3, 4)) {
            intake.roll(-0.7);
        } else {
            intake.stop();
        }

        if (leftStick.getOne(3, 5)) {
            intake.extend();
        } else if (leftStick.getOne(4, 6)) {
            intake.retract();
        }
    }
}
