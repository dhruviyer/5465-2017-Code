package org.usfirst.frc.team54652017.robot;


import edu.wpi.first.wpilibj.*;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;


/**
 * 
 * @author Dhruv
 * Handles all robot targeting and shooting ops
 */

public class RobotShoot extends Thread
{
	protected CANTalon shooterMotor1;
	protected Victor shooterMotor2;
	
	protected Solenoid feeder;
	protected Solenoid hood;
	protected RobotAggetator aggetate;
	
	private final double SHOOTER_LOW_SPEED = 0.5;
	private final double SHOOTER_HIGH_SPEED = 1.0;
	
	protected double speed;
	protected boolean angle;
	protected boolean start;
	public RobotShoot(int shootermotorport, int shootermotor2port,int aggetatorport, int solenoidhood, int solenoidfeed)
	{
		shooterMotor1 = new CANTalon(shootermotorport);
		shooterMotor2 = new Victor(shootermotor2port);
		aggetate = new RobotAggetator(aggetatorport);
		aggetate.start();
		
		hood = new Solenoid(solenoidhood);
		feeder = new Solenoid(solenoidfeed);
		angle = false;
		start = false;
		speed = 0;
	}
	
	public void run()
	{
		System.out.println("Starting Shooter Thread");
		
		while(true)
		{
			if(start) this.runShooter();
			else this.stopStuff();
		}
	}
	
	public void startstop(boolean stop)
	{
		this.start = stop;
	}
	
	public void updateSpeed(double speed, boolean angle)
	{
		this.speed = speed;
		this.angle = angle;
	}
	
	public void runShooter()
	{
		//Add: While loop that runs until wheel rpm reached input speed parameter
		if(angle)
		{
			hood.set(true);
			this.setShooterSpeed();
		}
		else
		{
			hood.set(false);
			this.setShooterSpeed();
		}
			
		Timer.delay(1); //delay can be changed
		// opens ball intake
		feeder.set(false);//opens the hole from hopper to shooter
		aggetate.updateState(true);//turns on the agitator
	}
	
	public void setShooterSpeed()
	{
		shooterMotor1.set(speed);
		shooterMotor2.set(speed);
	}
	
	public void stopStuff()
	{
		feeder.set(true);//Closes the hole from hopper to shooter
		aggetate.updateState(false);//stops aggetator
		shooterMotor1.set(0);
		shooterMotor2.set(0);
	}
	
	public double getEncoderValue()
	{
		return shooterMotor1.getAnalogInVelocity();
	}
}
