package org.usfirst.frc.team5465.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;

/***
 * 
 * @author Dhruv
 * Handles all robot targeting and shooting ops
 */
public class RobotShoot {
	protected CANTalon shooterMotor1;
	protected CANTalon shooterMotor2;
	protected Solenoid feeder;
	protected Solenoid hood;
	
	private final double SHOOTER_LOW_SPEED = 0.5;
	private final double SHOOTER_HIGH_SPEED = 1.0;
	
	public RobotShoot(int motor1Port, int motor2Port)
	{
		shooterMotor1 = new CANTalon(motor1Port);
		
		shooterMotor2 = new CANTalon(motor2Port);
		
	}
	
	public void setSpeed(double speed)
	{
		shooterMotor1.set(speed);
		shooterMotor2.set(speed);
	}
	
	public void stopMotors()
	{
		shooterMotor1.stopMotor();
		shooterMotor2.stopMotor();
	}
	
	public double getEncoderValue()
	{
		return shooterMotor1.getAnalogInVelocity();
	}
}
