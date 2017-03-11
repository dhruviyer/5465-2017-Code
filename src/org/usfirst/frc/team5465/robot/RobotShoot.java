package org.usfirst.frc.team5465.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/***
 * 
 * @author Dhruv
 * Handles all robot targeting and shooting ops
 */
public class RobotShoot {
	protected CANTalon shooterMotor1;
	protected Victor shooterMotor2;
	protected Solenoid feeder;
	protected Solenoid hood;
	
	private final double SHOOTER_LOW_SPEED = 0.5;
	private final double SHOOTER_HIGH_SPEED = 1.0;
	
	public RobotShoot(int CANTalonPort, int VictorPort)
	{
		shooterMotor1 = new CANTalon(CANTalonPort);
		
		shooterMotor2 = new Victor(VictorPort);
		
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
