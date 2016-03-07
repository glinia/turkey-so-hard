package org.usfirst.frc.team3501.robot;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class FireSolenoid extends DoubleSolenoid {

    private double speed = 1; // full speed

    public FireSolenoid(int forwardChannel, int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    public FireSolenoid(int moduleNum, int forwardChannel, int reverseChannel) {
        super(moduleNum, forwardChannel, reverseChannel);
    }

    public FireSolenoid(int moduleNum, int forwardChannel, int reverseChannel,
                        double speed) {
        super(moduleNum, forwardChannel, reverseChannel);
        this.speed = speed;
    }

    public void setSpeed(double speed) {
        assert(speed <= 1);
        assert(speed > 0);
        this.speed = speed;
    }

    /**
     * Call this with a toggleButton with a time of at least 1.0 secs
     */
    public void set(Value value) {
        if (speed == 1) {
            super.set(value);
            return;
        }

        Thread t = new Thread() {
            class Movement extends TimerTask {
                private Value v;
                public Movement(Value v) { this.v = v; }

                public void run() { FireSolenoid.this.set(v); }
            }

            private Value otherValue(Value v) {
                if (v == Value.kForward)
                    return Value.kReverse;

                return Value.kForward;
            }

            // currently, where char = 10ms: f.bf.bf.b etc (f 20 then b 10)
            public void run() {
                Timer t = new Timer();
                long now = System.currentTimeMillis();

                Date startDesired = new Date(now + 10);
                Date startCounter = new Date(now + 30);

                Movement desired = new Movement(value);
                Movement counter = new Movement(otherValue(value));

                t.schedule(desired, startDesired, 30);
                t.schedule(counter, startCounter, 30);

                try {
                    sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                t.cancel();

                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                FireSolenoid.this.set(value);
            }
        };

        t.start();
    }

}
