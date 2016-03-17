package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

public class Shooter {

    private DoubleSolenoid left, right;

    private Intake intake;

    private final DoubleSolenoid.Value UP    = DoubleSolenoid.Value.kForward,
                                       DOWN  = DoubleSolenoid.Value.kReverse;

    // intake_catapult (state is what it's trying to do)
    private enum STATE {IN_IN, IN_OUT, INTAKING, OUT_OUT, PASSIVE};

    private Timer stateTimer;
    private STATE state;

    public Shooter(final Intake intake) {
        this.intake = intake;

        left  = new DoubleSolenoid(PCM_B, 4, 1);
        right = new DoubleSolenoid(PCM_A, 0, 1);

        stateTimer = new Timer();
        stateTimer.start();
    }

    public void update() {
        switch (state) {
        case IN_IN:
            if ((intake.getState() == Intake.RETRACT) || timePassed(0.3))
                state = STATE.IN_OUT;

            intake.retract();
        case IN_OUT:
            if (timePassed(0.1))
                state = STATE.INTAKING;

            setCata(UP);
        case INTAKING:
            if (timePassed(0.3))
                state = STATE.INTAKING;

            intake.roll(0.2);
        case OUT_OUT:
            if (timePassed(0.3))
                state = STATE.PASSIVE;

            intake.extend();
        case PASSIVE:
            setCata(DOWN);
        default: return;
        }
    }

    public void shoot() {
        if (state != STATE.PASSIVE) {
            return;
        }

        state = STATE.IN_IN;
        stateTimer.reset();
    }

    private boolean timePassed(double time) {
        boolean ret = (stateTimer.get() > time);

        if (ret)
            stateTimer.reset();

        return ret;
    }

    private void setCata(DoubleSolenoid.Value value) {
        left.set(value);
        right.set(value);
    }
}
