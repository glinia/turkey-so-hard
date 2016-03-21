package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;

import java.util.HashMap;
import java.util.function.DoubleFunction;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DrivetrainAlgorithm {

    private static double oldTurn, quickStopAccumulator; // 254 stuff

    public static HashMap<String, Double> drive(
            double forward,
            double turn,
            DoubleSolenoid.Value shifterState,
            boolean flipped) {

        HashMap<String, Double> ret = new HashMap<String, Double>();

        if (Math.abs(forward) < MIN_DRIVE_JOYSTICK_INPUT)
            forward = 0;
        if (Math.abs(turn) < MIN_DRIVE_JOYSTICK_INPUT)
            turn = 0;

        forward *= (shifterState == LOW_GEAR) ? LOW_GEAR_POWER_COEFF
                                              : HIGH_GEAR_POWER_COEFF;

        if (flipped)
            forward *= -1;

        // begin code from github.com/Team254/FRC-2014

        // might want this to be different for high / low gear
        final double wheelNonLinearity = 0.5;

        double negInertia = turn - oldTurn;
        oldTurn = turn;

        // Sin function that's scaled to make it feel better.
        DoubleFunction<Double> scale = (double x) -> {
            return Math.sin(Math.PI / 2.0 * wheelNonLinearity * x)
                   / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        };

        if (shifterState == HIGH_GEAR) {
            turn = scale.apply(scale.apply(turn));
        } else {
            turn = scale.apply(scale.apply(scale.apply(turn)));
        }

        double leftPwm, rightPwm;
        final double sensitivity = 0.75;

        double angularPower, linearPower;

        double negInertiaAccumulator = 0.0;
        double negInertiaScalar;

        if (shifterState == HIGH_GEAR) {
            negInertiaScalar = 5.0;
        } else {
            if (turn * negInertia > 0) {
                negInertiaScalar = 2.5;
            } else {
                if (Math.abs(turn) > 0.65) {
                    negInertiaScalar = 5.0;
                } else {
                    negInertiaScalar = 3.0;
                }
            }
        }

        double negInertiaPower = negInertia * negInertiaScalar;
        negInertiaAccumulator += negInertiaPower;

        turn += negInertiaAccumulator;
        if (negInertiaAccumulator > 1) {
            negInertiaAccumulator -= 1;
        } else if (negInertiaAccumulator < -1) {
            negInertiaAccumulator += 1;
        } else {
            negInertiaAccumulator = 0;
        }

        linearPower = forward;

        // begin ignoring quickturn
        angularPower = Math.abs(forward) * turn * sensitivity
                       - quickStopAccumulator;

        if (quickStopAccumulator > 1) {
            quickStopAccumulator -= 1;
        } else if (quickStopAccumulator < -1) {
            quickStopAccumulator += 1;
        } else {
            quickStopAccumulator = 0.0;
        }
        // end ignoring quickturn

        rightPwm = leftPwm = linearPower;
        leftPwm  += angularPower;
        rightPwm -= angularPower;

        if (leftPwm > 1.0)
            leftPwm = 1.0;
        else if (rightPwm > 1.0)
            rightPwm = 1.0;
        else if (leftPwm < -1.0)
            leftPwm = -1.0;
        else if (rightPwm < -1.0)
            rightPwm = -1.0;
        // end code from github.com/Team254/FRC-2014

        ret.put("leftPwm", leftPwm);
        ret.put("rightPwm", rightPwm);

        return ret;
    }

}
