package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Intake {

    private DoubleSolenoid left, right;
    private CANTalon roller;

    public static final DoubleSolenoid.Value
                         EXTEND  = DoubleSolenoid.Value.kForward,
                         RETRACT = DoubleSolenoid.Value.kReverse;
    private DoubleSolenoid.Value STATE = RETRACT;

    public Intake() {
        left  = new DoubleSolenoid(PCM_A, 0, 1);
        right = new DoubleSolenoid(PCM_A, 2, 3);

        roller = new CANTalon(INTAKE_ROLLER_ADDR);
    }

    public void update() {
        actuate(STATE);
    }

    public void extend() {
        STATE = EXTEND;
    }

    public void retract() {
        STATE = RETRACT;
    }

    public void roll(double speed) {
        roller.set(speed);
    }

    public void stop() {
        roll(0);
    }

    public DoubleSolenoid.Value getState() {
        return STATE;
    }

    private void actuate(DoubleSolenoid.Value value) {
        left.set(value);
        right.set(value);
    }
}
