package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;

import java.util.stream.Stream;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drivetrain {

    private CANTalon frontLeft, frontRight, rearLeft, rearRight;

    private RobotDrive robotDrive;
    private DoubleSolenoid leftShifter, rightShifter;

    private DoubleSolenoid.Value shifterState;

    private boolean flipped;

    public Drivetrain() {
        frontLeft  = new CANTalon(FRONT_LEFT_ADDR);
        frontRight = new CANTalon(FRONT_RIGHT_ADDR);
        rearLeft   = new CANTalon(REAR_LEFT_ADDR);
        rearRight  = new CANTalon(REAR_RIGHT_ADDR);

        robotDrive = new RobotDrive(frontLeft,  rearLeft,
                                    frontRight, rearRight);

        leftShifter  = new DoubleSolenoid(PCM_B, 3, 6);
        rightShifter = new DoubleSolenoid(PCM_B, 2, 7);

        shifterState = LOW_GEAR;
        setShifters(shifterState);
        coast();
        flipped = false;
    }

    public void drive(double left, double right) {
        if (Math.abs(left) < MIN_DRIVE_JOYSTICK_INPUT)
            left = 0;
        if (Math.abs(right) < MIN_DRIVE_JOYSTICK_INPUT)
            right = 0;

        double powerCoeff = (shifterState == LOW_GEAR)
                                ? LOW_GEAR_POWER_COEFF
                                : HIGH_GEAR_POWER_COEFF;

        if (flipped) { // swap left and right, and reverse inputs
            double temp = left;
            left  = -right;
            right = -temp;
        }

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
    }

    public void brake() {
        allTalons().forEach(t -> t.enableBrakeMode(true));
    }

    public void coast() {
        allTalons().forEach(t -> t.enableBrakeMode(false));
    }

    public void flip() {
        flipped = !flipped;
    }

    private Stream<CANTalon> allTalons() {
        return Stream.of(frontLeft, frontRight, rearLeft, rearRight);
    }

    private void setShifters(DoubleSolenoid.Value value) {
        leftShifter.set(value);
        rightShifter.set(value);
    }
}
