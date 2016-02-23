package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Shooter {

    private CANTalon flywheel;
    private DoubleSolenoid hoodLeft, hoodRight, step;

    private final DoubleSolenoid.Value OPEN  = DoubleSolenoid.Value.kForward,
                                       CLOSE = DoubleSolenoid.Value.kReverse,
                                       UP    = DoubleSolenoid.Value.kReverse,
                                       DOWN  = DoubleSolenoid.Value.kForward;

    public Shooter() {
        flywheel = new CANTalon(FLYWHEEL_ADDR);

        hoodLeft  = new DoubleSolenoid(PCM_A, 2, 4);
        hoodRight = new DoubleSolenoid(PCM_B, 4, 2);
        step      = new DoubleSolenoid(PCM_A, 1, 5);

        setHood(CLOSE);
        disengage();
        step.set(DOWN);
    }

    public void shoot() {
        step.set(UP);
    }

    public void load() {
        step.set(DOWN);
    }

    public void set(double speed) {
        flywheel.set(speed);
    }

    public void engage() {
        set(FLYWHEEL_SPEED);
    }

    public void disengage() {
        set(0);
    }

    public void retractHood() {
        setHood(CLOSE);
    }

    public void openHood() {
        setHood(OPEN);
    }

    private void setHood(DoubleSolenoid.Value value) {
        hoodLeft.set(value);
        hoodRight.set(value);
    }
}
