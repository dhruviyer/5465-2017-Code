package org.usfirst.frc.team5465.robot;


import edu.wpi.first.wpilibj.*;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

/***
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
	
	private final double SHOOTER_LOW_SPEED = 0.5;
	private final double SHOOTER_HIGH_SPEED = 1.0;
	
	protected double speed;
	public RobotShoot(int talonport, int victorport)
	{
		shooterMotor1 = new CANTalon(talonport);
		shooterMotor2 = new Victor(victorport);
		speed = 0;
		
	}
	
	public void run()
	{
		System.out.println("Starting Shooter Thread");
		
		while(true)
		{
			this.setSpeed(speed);
		}
	}
	
	public void updateSpeed(double x)
	{
		speed = x;
	}
	
	public void setSpeed(double speed)
	{
		shooterMotor1.set(speed);
		shooterMotor2.set(speed);
	}
	
	public void stopMotors()
	{
		shooterMotor1.set(0);
		shooterMotor2.set(0);
	}
	
	public double getEncoderValue()
	{
		return shooterMotor1.getAnalogInVelocity();
	}
}
