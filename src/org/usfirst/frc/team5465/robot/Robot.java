
package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/***
 * 
 * @author Dhruv
 * @version 1.0
 * 
 * Main robot class, integrates all functionality
 * 
 */

public class Robot extends IterativeRobot {
	//high level class ops variables
	private static RobotDrive robotDrive;
	private static RobotShoot robotShoot;
	DigitalInput dio5;
	DigitalInput dio6;
	DigitalInput dio0;
    
	//lower level controller ops variables
	private static Joystick leftDriveJoystick;
	private static Joystick rightDriveJoystick;
	private static ADXRS450_Gyro gyro;

	
	//important port constants, check with electrical
	final static int LEFT_JOYSTICK_PORT = 0;
	final static int RIGHT_JOYSTICK_PORT = 1;
	final static int SECOND_JOYSTICK_PORT = 2;
	final static int TALON_ENCODER_SHOOTER_PWM = 0;
	final static int TALON_NO_ENCODER_SHOOTER_PWM = 1;
	final static int LEFT_SIDE_DRIVE_PWM_PORT = 0;
	final static int RIGHT_SIDE_DRIVE_PWM_PORT = 1;
	
	//miscellaneous variables
    static double forwardMag = 0.0;
	static double turnMag = 0.0;

	static boolean setPointIsSet = false;
	static double setPoint = 0;
	static int encCount = 0;
	
	SmartDashboard dashboard;

	
    public void robotInit() {
   
    	
    	leftDriveJoystick = new Joystick(LEFT_JOYSTICK_PORT);
    	rightDriveJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
    	
    	gyro = new ADXRS450_Gyro();
    	gyro.calibrate();
    	
     	robotDrive = new RobotDrive(LEFT_SIDE_DRIVE_PWM_PORT, RIGHT_SIDE_DRIVE_PWM_PORT, gyro);
     	robotShoot = new RobotShoot(TALON_ENCODER_SHOOTER_PWM, TALON_NO_ENCODER_SHOOTER_PWM);
     	
    	dashboard = new SmartDashboard();

    
    }
    
    public void autonomousInit() {
    	
    }

    public void autonomousPeriodic() {
    	robotDrive.stopMotors();
    }
    
    public void teleopPeriodic() {
    	updateMags();
    	/*
    	boolean turning = Math.abs(turnMag - 0.0) > 0.01;
    	
    	if(!turning)
    	{
    		robotDrive.driveStraight(setPoint, forwardMag);
    	}
    	else
    	{
    		robotDrive.drive(-1*forwardMag+turnMag, forwardMag+turnMag);
    		setPoint = gyro.getAngle();
    	}*/
    
    	if(leftDriveJoystick.getRawButton(2))
    	{
    		robotShoot.setSpeed(leftDriveJoystick.getThrottle());
    	}
    	else
    	{
    		robotShoot.setSpeed(0);
    	}
    	
    	SmartDashboard.putNumber("Gyro", gyro.getAngle());
    	SmartDashboard.putNumber("Set Point", setPoint);
    	SmartDashboard.putNumber("TurnMag", turnMag);
    	SmartDashboard.putNumber("ForwardMag", forwardMag);
    	//SmartDashboard.putBoolean("Turning?", turning);
    	SmartDashboard.putNumber("Shooter", robotShoot.getEncoderValue());

    }
   
    public void updateMags()
    {
    	if(leftDriveJoystick.getRawButton(1))
    	{
    		forwardMag = leftDriveJoystick.getY()*0.25;
    	}else
    	{
    		forwardMag = leftDriveJoystick.getY();
    	}
    	
    	if(rightDriveJoystick.getRawButton(1))
    	{
    		turnMag = rightDriveJoystick.getX()*0.25;
    	}else
    	{
    		turnMag = rightDriveJoystick.getX();
    	}
    }
    
    public void teleopInit()
    {
    	setPoint = gyro.getAngle();
  
    }
    public void testPeriodic() {
    
    }
}
