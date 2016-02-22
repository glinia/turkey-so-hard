package org.usfirst.frc.team3501.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Consts {
    // pneumatics
    final static int PCM_A = 9, PCM_B = 10;

    // joystick control
    final static int LEFT_JOYSTICK_PORT = 0, RIGHT_JOYSTICK_PORT = 1;

    final static double TOGGLE_TIME = 0.2;
    final static int UP = 0, UP_RIGHT = 45, RIGHT = 90, DOWN_RIGHT = 135,
            DOWN = 180, DOWN_LEFT = 225, LEFT = 270, UP_LEFT = 315,
            NOT_PRESSED = -1;

    // drivetrain
    final static double MIN_DRIVE_JOYSTICK_INPUT = 0.1;

    final static int FRONT_LEFT_ADDR = 1, FRONT_RIGHT_ADDR = 5,
                     REAR_LEFT_ADDR  = 2, REAR_RIGHT_ADDR  = 6;

    final static DoubleSolenoid.Value
        HIGH_GEAR = DoubleSolenoid.Value.kForward,
        LOW_GEAR  = DoubleSolenoid.Value.kReverse;

    // shooter
    final static int FLYWHEEL_ADDR = 4;
    final static int FLYWHEEL_SPEED = 1;

    // intake
    final static int INTAKE_LEFT_ADDR = 3, INTAKE_RIGHT_ADDR = 4;
}
