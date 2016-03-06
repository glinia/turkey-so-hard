package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Intake {

    private DoubleSolenoid left, right;
    private CANTalon roller;

    private final DoubleSolenoid.Value EXTEND  = DoubleSolenoid.Value.kForward,
                                       RETRACT = DoubleSolenoid.Value.kReverse;

    public Intake() {
        left  = new DoubleSolenoid(PCM_A, 0, 0);
        right = new DoubleSolenoid(PCM_A, 0, 0);

        roller = new CANTalon(INTAKE_ROLLER_ADDR);
    }

    public void extend() {
        actuate(EXTEND);
    }

    public void retract() {
        actuate(RETRACT);
    }

    public void roll(double speed) {
        roller.set(speed);
    }

    public void stop() {
        roll(0);
    }

    private void actuate(DoubleSolenoid.Value value) {
        left.set(value);
        right.set(value);
    }
}
