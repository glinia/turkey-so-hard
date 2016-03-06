package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shooter {

    private DoubleSolenoid left, right;

    private final DoubleSolenoid.Value UP    = DoubleSolenoid.Value.kReverse,
                                       DOWN  = DoubleSolenoid.Value.kForward;

    public Shooter() {
        left  = new DoubleSolenoid(PCM_A, 4, 2);
        right = new DoubleSolenoid(PCM_B, 2, 4);
    }

    public void shoot() {
        setCata(UP);
    }

    public void load() {
        setCata(DOWN);
    }

    private void setCata(DoubleSolenoid.Value value) {
        left.set(value);
        right.set(value);
    }
}
