package org.usfirst.frc.team3501.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.usfirst.frc.team3501.robot.Joystick;

import static org.usfirst.frc.team3501.robot.Consts.*;

public class Robot extends IterativeRobot {

    private Joystick leftStick, rightStick;

    private Drivetrain drivetrain;
    private Intake intake;
    private Shooter shooter;
    private Compressor compressor;

    private FireSolenoid test;

    public void robotInit() {
        leftStick  = new Joystick(LEFT_JOYSTICK_PORT);
        rightStick = new Joystick(RIGHT_JOYSTICK_PORT);

        drivetrain = new Drivetrain();
        intake     = new Intake();
        shooter    = new Shooter();

        compressor = new Compressor(PCM_A);
        compressor.start();

        test = new FireSolenoid(PCM_A, -1, -1, 0.5);
    }

    public void teleopPeriodic() {
        buttonsPressed();

        if (rightStick.getTimedAction(3, 1.0)) {
            test.set(Value.kForward);
        } else if (rightStick.getTimedAction(4, 1.0)) {
            test.set(Value.kReverse);
        }

        drive();
    }

    private void drive() {
        double left  = leftStick.getY();
        double right = rightStick.getY();

        drivetrain.drive(left, right);
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
            intake.roll(1);
        } else {
            intake.stop();
        }

        // shooter
        if (rightStick.get(2)) {
            shooter.shoot();
        } else {
            shooter.load();
        }
    }

    @SuppressWarnings("unused")
    private void print(String message) {
        DriverStation.reportWarning(message, false);
    }
}
