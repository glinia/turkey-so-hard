package org.usfirst.frc.team3501.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

import org.usfirst.frc.team3501.robot.Joystick;

import static org.usfirst.frc.team3501.robot.Consts.*;

public class Robot extends IterativeRobot {

    private Joystick leftStick, rightStick;

    private Drivetrain drivetrain;
    private Shooter shooter;
    private Compressor compressor;

    public void robotInit() {
        leftStick  = new Joystick(LEFT_JOYSTICK_PORT);
        rightStick = new Joystick(RIGHT_JOYSTICK_PORT);

        drivetrain = new Drivetrain();

        compressor = new Compressor(PCM_A);
        compressor.start();
    }

    public void teleopPeriodic() {
        buttonsPressed();

        drive();
    }

    private void drive() {
        double left  = leftStick.getY();
        double right = rightStick.getY();

        drivetrain.drive(left, right);
    }

    private void buttonsPressed() {
        if (rightStick.getToggleButton(1)) {
            drivetrain.shift();
            print("Shifting at " + System.currentTimeMillis());
        }

        if (leftStick.get(1)) {
            shooter.engage();
        } else {
            shooter.disengage();
        }
    }

    private void print(String message) {
        DriverStation.reportWarning(message, false);
    }
}
