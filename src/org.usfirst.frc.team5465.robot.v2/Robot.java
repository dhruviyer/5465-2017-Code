package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
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
	
	final static int CAMERA_CENTER = 320;
	
	static boolean setPointIsSet = false;
	static double setPoint = 0;
	static int encCount = 0;
	
	SmartDashboard dashboard;

	static double highestSpeed = 0;
	
	
    public void robotInit() 
    {
    	gyro = new ADXRS450_Gyro();
    	gyro.calibrate();
    	
     	robotDrive = new RobotDrive(LEFT_SIDE_DRIVE_PWM_PORT, RIGHT_SIDE_DRIVE_PWM_PORT, gyro);
     	
     	convey = new ConveyorTester(CONVEYOR1_TALONSR_PWM_PORT, CONVEYOR2_TALONSR_PWM_PORT);
     	convey.start();
     	
     	robotShoot = new RobotShoot(SHOOTER1_TALONSRX_CAN_PORT, SHOOTER2_TALONSRX_CAN_PORT, HOOD_SOLENOID_FORWARD_PORT, HOOD_SOLENOID_REVERSE_PORT, AGGETATE_TALONSRX_CAN_PORT);
     	robotShoot.start();
     	
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
    	
    	boolean conveyor = joystick.getConveyor();
    	boolean shooter = joystick.getShooter();
    	double conveyspeed = (-1*joystick.getRightSpeed() + 1) * 0.5;
    	double shootspeed = (-1*joystick.getSpeed() + 1) * 0.5;
    	
    	if(conveyor)
    	{
    		convey.changeState(true);
    		convey.updateVals(conveyspeed);
    	}
    	else
    	{
    		convey.changeState(false);
    	}
    	
    	if(shooter)
    	{
    		robotShoot.changeState(true);
    		robotShoot.updateVals(-1*shootspeed);
    	}
    	else
    	{
    		robotShoot.changeState(false);
    	}
    	
    	boolean status = false;
    	//if(joystick.getAuto()) status = robotAim();
    	
    	highestSpeed = Math.max(highestSpeed, robotShoot.getEncSpeed1());
    	if(robotShoot.getEncSpeed1() == 0)
    		highestSpeed = 0;
    	SmartDashboard.putBoolean("Conveyor On or Off", conveyor);
    	SmartDashboard.putBoolean("Shooter on or off", shooter);
    	SmartDashboard.putNumber("Gyro", gyro.getAngle());
    	SmartDashboard.putNumber("Set Point", setPoint);
    	SmartDashboard.putNumber("TurnMag", turnMag);
    	SmartDashboard.putNumber("ForwardMag", forwardMag);
    	SmartDashboard.putNumber("Conveyor data", conveyspeed);
    	SmartDashboard.putNumber("Shooter data", shootspeed);
    	SmartDashboard.putNumber("Real Shooter Speed 1", robotShoot.getEncSpeed1());
    	//SmartDashboard.putNumber("Real Shooter Speed 2", robotShoot.getEncSpeed2());
    	SmartDashboard.putBoolean("Auto Status", status);
    }
   
    public boolean robotAim()
    {
    	//also set time out, this should go for less than 10s
		double center = udp.getCenterX();
		Timer timeout = new Timer();
		timeout.start();
    	while(Math.abs(center - CAMERA_CENTER)  >= 5 && timeout.get() < 10 && joystick.getAuto() && udp.getRawSentence() != null)
    	{
    		if(udp.getInframe())
    		{
    			if(center - CAMERA_CENTER >= 0)
    			{
    				//turn left
    				robotDrive.drive(-0.08, 0.08);
    			}
    			else
    			{
    				//turn right slowly
    				robotDrive.drive(0.08, -0.08);
    			}
    		}
    		else
    		{
    			//turn right fast
    			robotDrive.drive(-0.25, 0.25);
    		}
    		center = udp.getCenterX();
    	}
    	
    	timeout.stop();
    	boolean status = false;
    	if(udp.getInframe() && Math.abs(udp.getCenterX() - CAMERA_CENTER) <= 5) status = true;
    	return status;
    }
    
    public boolean autoShoot()
    {
    	double distance = convertInchestoMeters(udp.getDistance());
    	boolean status = false;
    	
    	if(distance < 1.2)
    	{
    		Timer timeout = new Timer();
    		timeout.start();
    		while(distance <= 1.2)
    		{
    			if(timeout.get() < 6)
    			{
    				robotDrive.drive(-0.1, -0.1);
    			}
    			
    			else
    			{
    				status = false;
    				return status;
    			}
    		}
    		timeout.stop();
    	}
    	
    	
		if(distance < 2.6)
		{
			//robotShoot.updateVals(true, 0);
			// function that converts distance measurements to a rpm for update vals
		}
		
		else
		{
			//robotShoot.updateVals(false, 0);
			// function that converts distance measurements to a rpm for update vals
		}
    	
    	
    	status = true;
    	return status;
    }
    
    
    public static double convertInchestoMeters(double inches)
    {
    	return inches * 2.54/100;
    }
    /**
    public boolean autoSequence()
    {
    	boolean a = robotAim();
    	boolean b;
    	boolean overallstatus;
    	if(a)
    	{
    		b = autoShoot();
    		if(!b) overallstatus = false;
    		else overallstatus = true;
    	}
    	
    	else overallstatus = false;
    	
    	return overallstatus;
    	
    	
    }**/
    
    public void teleopInit()
    {
    	setPoint = gyro.getAngle();
    	joystick.changeState(true);
    	convey.changeState(true);
    	robotShoot.changeState(true);
    }
    
    public void disabledPeriodic()
    {
    	joystick.changeState(false);
    	robotDrive.stopMotors();
    	convey.changeState(false);
    	robotShoot.changeState(false);
    }
    public void testPeriodic() {
    
    }
}
