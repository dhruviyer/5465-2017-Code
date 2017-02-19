package org.usfirst.frc.team54652017.robot;

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
	private static RobotConveyClimb robotConvey;
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
	
	final static int LEFT_SIDE_DRIVE_PWM_PORT = 0;
	final static int RIGHT_SIDE_DRIVE_PWM_PORT = 1;
	
	final static int SHOOTER_TALONSRX_CAN_PORT = 0;
	final static int SHOOTER_VICTOR_PWM_PORT = 1;
	final static int AGGETATE_TALONSR_PWM_PORT = 2;
	final static int CONVEYOR_TALONSR_PWMPORT =3 ;
	

	final static int HOOD_SOLENOID_PORT =0;
	final static int FEEDER_SOLENOID_PORT =1;
	final static int CONVEYER_SOLENOID_PORT = 2;
	
	//miscellaneous variables
    static double forwardMag = 0.0;
	static double turnMag = 0.0;

	static boolean setPointIsSet = false;
	static double setPoint = 0;
	static int encCount = 0;
	
	static boolean climbing = false;
	//if false robot will go to conveyor (which is wanted for most of the match) , else go into climber
	static boolean state = false;
	//if false it will go into conveyor||climber mode, else go into shooter mode
	static boolean hood = false;
	//hood is true when button2 is pressed which extends the piston, else retracts it;
	static double shooterspeed = 0.0;
	
	SmartDashboard dashboard;

	
    public void robotInit() 
    {
    	leftDriveJoystick = new Joystick(LEFT_JOYSTICK_PORT);
    	rightDriveJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
    	
    	gyro = new ADXRS450_Gyro();
    	gyro.calibrate();
    	
     	robotDrive = new RobotDrive(LEFT_SIDE_DRIVE_PWM_PORT, RIGHT_SIDE_DRIVE_PWM_PORT, gyro);
     	robotDrive.start();
     	
     	robotShoot = new RobotShoot(SHOOTER_TALONSRX_CAN_PORT,SHOOTER_VICTOR_PWM_PORT,AGGETATE_TALONSR_PWM_PORT,HOOD_SOLENOID_PORT,FEEDER_SOLENOID_PORT);
     	robotShoot.start();
     	
     	robotConvey = new RobotConveyClimb(CONVEYOR_TALONSR_PWMPORT,CONVEYER_SOLENOID_PORT);
     	robotConvey.start();
     	
    	dashboard = new SmartDashboard();
    	
    }
    
    public void autonomousInit() {
    	
    }

    public void autonomousPeriodic() 
    {
    	robotDrive.updateVals(0, 0, 0);
    	robotShoot.startstop(false);
    }
    
    public void teleopPeriodic() 
    {
    	updateMags();
    	updateVALS();
    	
    	/**
    	boolean turning = Math.abs(turnMag - 0.0) > 0.01;
    	
    	if(!turning)
    	{
    		robotDrive.updateVals(1,setPoint, forwardMag);
    	}
    	else
    	{
    		robotDrive.updateVals(2,-1*forwardMag+turnMag, forwardMag+turnMag);
    		setPoint = gyro.getAngle();
    	}
    	**/
    	
    	if(state == false) 
    	{
    		//go in here if state is false, or u wanna do the conveyor or climber system rather than shoot
    		//!state = True
    		// The robotshooter will go into its stop state which will close the feeder and stop the motors in the shooter thread class
    		// if conveyor is false then it will go to conveyor mode, else climber mode
    		robotShoot.startstop(false);
    		robotConvey.startstop(true);
    		robotConvey.updateState(climbing);
    	}
    	
    	else
    	{
    		//go here if u wanna do shooter
    		
    		//stop the conveyor
    		robotConvey.startstop(false);
    		
    		//start the shooter
    		robotShoot.startstop(true);
    		//update speed and if hood is true, then it will shoot high, else shoot low
    		robotShoot.updateSpeed(shooterspeed,hood);
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
    	}
    	else
    	{
    		forwardMag = leftDriveJoystick.getY();
    	}
    	
    	if(rightDriveJoystick.getRawButton(1))
    	{
    		turnMag = rightDriveJoystick.getX()*0.25;
    	}
    	else
    	{
    		turnMag = rightDriveJoystick.getX();
    	}
    	
    }
    
    public void updateVALS()
    {
    	// if pressed it goes into climb mode, else it goes into conveyor mode
    	climbing = leftDriveJoystick.getRawButton(3);
    	// if pressed it goes into shooter mode, else goes into conveyor/climb mode
    	state = leftDriveJoystick.getRawButton(2);
    	// if pressed then hood is up, else hood is down
    	hood = rightDriveJoystick.getRawButton(2);
    	// not sure about the mapping for dis
    	shooterspeed = rightDriveJoystick.getThrottle();
    }
    
    public void teleopInit()
    {
    	setPoint = gyro.getAngle();
  
    }
    public void testPeriodic() {
    
    }
}

