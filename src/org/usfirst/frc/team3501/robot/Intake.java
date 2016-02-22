package org.usfirst.frc.team3501.robot;

import static org.usfirst.frc.team3501.robot.Consts.*;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Relay;

public class Intake {

    CANTalon left, right;
    Relay roller;

    // add pots

    public Intake() {
        left  = new CANTalon(INTAKE_LEFT_ADDR);
        right = new CANTalon(INTAKE_RIGHT_ADDR);

        roller = new Relay(0);
    }

    public void intake() {

    }
}
