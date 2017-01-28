
package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;


public class Robot extends IterativeRobot {
	private static RobotDrive robotDrive;
    
	private static Joystick leftDriveJoystick;
	private static Joystick rightDriveJoystick;
	
	final static int LEFT_JOYSTICK_PORT = 0;
	final static int RIGHT_JOYSTICK_PORT = 1;
	
	final static int LEFT_SIDE_DRIVE_PWM_PORT = 0;
	final static int RIGHT_SIDE_DRIVE_PWM_PORT = 1;
	
    
    public void robotInit() {
    	robotDrive = new RobotDrive(LEFT_SIDE_DRIVE_PWM_PORT, RIGHT_SIDE_DRIVE_PWM_PORT);
    	
    	leftDriveJoystick = new Joystick(LEFT_JOYSTICK_PORT);
    	rightDriveJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
    }
    
    public void autonomousInit() {
    	
    }

    public void autonomousPeriodic() {
    	robotDrive.stopMotors();
    }
    public void teleopPeriodic() {
        robotDrive.drive(leftDriveJoystick.getY(), rightDriveJoystick.getY());
    }

    public void testPeriodic() {
    
    }
}
