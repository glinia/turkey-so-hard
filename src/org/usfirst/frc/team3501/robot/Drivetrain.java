package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;

import java.util.HashMap;
import java.util.stream.Stream;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Drivetrain {

    private CANTalon frontLeft, frontRight, rearLeft, rearRight;
    private DoubleSolenoid leftShifter, rightShifter;

    private DoubleSolenoid.Value shifterState;
    private boolean isFlipped;

    public Drivetrain() {
        frontLeft  = new CANTalon(FRONT_LEFT_ADDR);
        frontRight = new CANTalon(FRONT_RIGHT_ADDR);
        rearLeft   = new CANTalon(REAR_LEFT_ADDR);
        rearRight  = new CANTalon(REAR_RIGHT_ADDR);

        leftShifter  = new DoubleSolenoid(PCM_A, 4, 5);
        rightShifter = new DoubleSolenoid(PCM_A, 6, 7);

        shifterState = LOW_GEAR;
        setShifters(shifterState);
        brake();
        isFlipped = false;
    }

    public void drive(double forward, double turn) {
        HashMap<String, Double> result =
            DrivetrainAlgorithm.drive(forward, turn, shifterState, isFlipped);

        driveRaw(result.get("leftPwm"), result.get("rightPwm"));
    }

    public void driveRaw(double left, double right) {
        frontLeft.set(left);
        rearLeft.set(left);
        frontRight.set(-right);
        rearRight.set(-right);
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
        } else if (shifterState == LOW_GEAR) {
            shiftHigh();
        }
    }

    public void brake() {
        allTalons().forEach(t -> t.enableBrakeMode(true));
    }

    public void coast() {
        allTalons().forEach(t -> t.enableBrakeMode(false));
    }

    public void flip() {
        isFlipped = !isFlipped;
    }

    private Stream<CANTalon> allTalons() {
        return Stream.of(frontLeft, frontRight, rearLeft, rearRight);
    }

    private void setShifters(DoubleSolenoid.Value value) {
        leftShifter.set(value);
        rightShifter.set(value);
    }
}
