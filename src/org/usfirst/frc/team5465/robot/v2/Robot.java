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
 * @author Sachin Konan
 * @version 1.0
 * 
 * Main robot class, integrates all functionality
 * 
 */

public class Robot extends IterativeRobot 
{
	//high level class ops variables
	private static RobotDrive robotDrive;
	private static RobotShoot robotShoot;
	private static JoystickThread joystick;
	private static ConveyorTester convey;
	
	private static ADXRS450_Gyro gyro;

	private static RobotUDP udp;
	
	//important port constants, check with electrical
	final static int LEFT_JOYSTICK_PORT = 0;
	final static int RIGHT_JOYSTICK_PORT = 1;
	
	final static int LEFT_SIDE_DRIVE_PWM_PORT = 0;
	final static int RIGHT_SIDE_DRIVE_PWM_PORT = 1;
	final static int CONVEYOR1_TALONSR_PWM_PORT = 2;
	final static int CONVEYOR2_TALONSR_PWM_PORT = 3;

	final static int SHOOTER1_TALONSRX_CAN_PORT = 3;
	final static int SHOOTER2_TALONSRX_CAN_PORT = 2;
	final static int AGGETATE_TALONSRX_CAN_PORT = 1;
	

	final static int HOOD_SOLENOID_FORWARD_PORT = 0;
	final static int HOOD_SOLENOID_REVERSE_PORT = 1;
	
	final static int FEEDER_SOLENOID_FORWARD_PORT = 2;
	final static int FEEDER_SOLENOID_REVERSE_PORT = 3;
	
	final static int CONVEYER_SOLENOID_FORWARD_PORT = 4;
	final static int CONVEYER_SOLENOID_REVERSE_PORT = 5;
	
	final static int UDP_PORT = 5800;
	final static int BUFF_SIZE = 1024;
	
	static boolean setPointIsSet = false;
	static double setPoint = 0;
	static int encCount = 0;
	
	SmartDashboard dashboard;

	
    public void robotInit() 
    {
    	gyro = new ADXRS450_Gyro();
    	gyro.calibrate();
    	
     	robotDrive = new RobotDrive(LEFT_SIDE_DRIVE_PWM_PORT, RIGHT_SIDE_DRIVE_PWM_PORT, gyro);
     	
     	//convey = new ConveyorTester(CONVEYOR1_TALONSR_PWM_PORT, CONVEYOR2_TALONSR_PWM_PORT);
     	//convey.start();
     	
     	//robotShoot = new RobotShoot(SHOOTER1_TALONSRX_CAN_PORT, SHOOTER2_TALONSRX_CAN_PORT, HOOD_SOLENOID_FORWARD_PORT, HOOD_SOLENOID_REVERSE_PORT, AGGETATE_TALONSRX_CAN_PORT);
     	//robotShoot.start();
     	
     	udp = new RobotUDP("UDP Thread", UDP_PORT, BUFF_SIZE);
     	udp.start();
     	
    	joystick = new JoystickThread(LEFT_JOYSTICK_PORT, RIGHT_JOYSTICK_PORT);
    	joystick.start();
    	
    	dashboard = new SmartDashboard();

    
    }
    
    public void autonomousInit() {
    	
    }

    public void autonomousPeriodic() 
    {
    	robotDrive.stopMotors();
    }
    
    public void teleopPeriodic() 
    {    	
    	double forwardMag = joystick.getForward();
    	double turnMag = joystick.getTurn();
    	
    	boolean turning = Math.abs(turnMag - 0.0) > 0.01;
    	
    	if(!turning)
    	{
    		robotDrive.driveStraight(setPoint, forwardMag);
    	}
    	else
    	{
    		robotDrive.drive(-1*forwardMag+turnMag, forwardMag+turnMag);
    		setPoint = gyro.getAngle();
    	}
    	
    	//robotShoot.updateVals(false, speed);
    	
    	/**
    	if(joystick.getConveyorShoot())
    	{
    		//robotShoot.changeState(false);
    		//convey.changeState(joystick.getConveyor());
    	}
    	
    	else
    	{
    		//convey.changeState(false);
    		//robotShoot.changeState(true);
    		//robotShoot.updateVals(false, joystick.getSpeed());
    	}**/
    	
    	
    	SmartDashboard.putBoolean("COnvey or Shoot?", joystick.getConveyorShoot());
    	SmartDashboard.putBoolean("Conveyor On or Off", joystick.getConveyor());
    	SmartDashboard.putNumber("Gyro", gyro.getAngle());
    	SmartDashboard.putNumber("Set Point", setPoint);
    	SmartDashboard.putNumber("TurnMag", turnMag);
    	SmartDashboard.putNumber("ForwardMag", forwardMag);
    	SmartDashboard.putString("Receive UDP String", udp.getRawSentence());
    	//SmartDashboard.putBoolean("Inframe", udp.getInframe());
    	//SmartDashboard.putNumber("Centerx", udp.getCenterX());
    	
    }
   
    
    public void teleopInit()
    {
    	setPoint = gyro.getAngle();
    	joystick.changeState(true);
    	//robotShoot.changeState(true);
    }
    
    public void disabledPeriodic()
    {
    	joystick.changeState(false);
    	robotDrive.stopMotors();
    	//robotShoot.stopThread();
    }
    public void testPeriodic() {
    
    }
}
