package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drivetrain {

    private RobotDrive robotDrive;
    private DoubleSolenoid leftShifter, rightShifter;

    private DoubleSolenoid.Value shifterState;

    public Drivetrain() {
        CANTalon frontLeft  = new CANTalon(FRONT_LEFT_ADDR);
        CANTalon frontRight = new CANTalon(FRONT_RIGHT_ADDR);
        CANTalon rearLeft   = new CANTalon(REAR_LEFT_ADDR);
        CANTalon rearRight  = new CANTalon(REAR_RIGHT_ADDR);

        robotDrive = new RobotDrive(frontLeft,  rearLeft,
                                    frontRight, rearRight);

        leftShifter  = new DoubleSolenoid(PCM_B, 1, 5);
        rightShifter = new DoubleSolenoid(PCM_B, 0, 6);

        shifterState = LOW_GEAR;
        setShifters(shifterState);
    }

    public void drive(double left, double right) {
        if (Math.abs(left) < MIN_DRIVE_JOYSTICK_INPUT)
            left = 0;
        if (Math.abs(right) < MIN_DRIVE_JOYSTICK_INPUT)
            right = 0;

        double powerCoeff = (shifterState == LOW_GEAR)
                                ? LOW_GEAR_POWER_COEFF
                                : HIGH_GEAR_POWER_COEFF;

        driveRaw(powerCoeff * left, powerCoeff * right);
    }

    public void driveRaw(double left, double right) {
        robotDrive.tankDrive(left, right, false);
    }

    public void stop() {
        driveRaw(0.0, 0.0);
    }

    public void shiftHigh() {
        setShifters(HIGH_GEAR);
    }

    public void shiftLow() {
        setShifters(LOW_GEAR);
    }

    public void shift() {
        if (shifterState == HIGH_GEAR) {
            shiftLow();
            return;
        } else if (shifterState == LOW_GEAR) {
            shiftHigh();
            return;
        }

        System.err.println("Drivetrain::shift is being weird.");
    }

    private void setShifters(DoubleSolenoid.Value value) {
        leftShifter.set(value);
        rightShifter.set(value);
    }
}
