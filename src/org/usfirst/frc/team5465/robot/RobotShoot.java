package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;

/***
 * 
 * @author Dhruv
 * Handles all robot targeting and shooting ops
 */
public class RobotShoot {
	protected TalonSRX shooterMotor;
	protected Solenoid feeder;
	protected Solenoid hood;
	
	private final double SHOOTER_LOW_SPEED = 0.5;
	private final double SHOOTER_HIGH_SPEED = 1.0;
	
	public RobotShoot(int motorPort, int feederPort, int hoodPort)
	{
		shooterMotor = new TalonSRX(motorPort);
		feeder = new Solenoid(feederPort);
		hood = new Solenoid(hoodPort);
	}
	
	
	public void setHoodLowAngle()
	{
		hood.set(false);
	}
	
	public void setHoodHighAngle()
	{
		hood.set(true);
	}
	
	
	public void setShooterSpeedNone()
	{
		shooterMotor.stopMotor();
	}
	
	
	public void setShooterSpeedLow()
	{
		shooterMotor.setSpeed(SHOOTER_LOW_SPEED);
	}
	
	public void setShooterSpeedHigh()
	{
		shooterMotor.setSpeed(SHOOTER_HIGH_SPEED);
	}
	
	/***
	 * Opens and closes piston door to shoot/not shoot balls without stopping flywheel
	 */
	public void shoot()
	{
		feeder.set(true);
	}
	
	public void noShoot()
	{
		feeder.set(false);
	}
	
	public double aquireDistance()
	{
		return 0.0;
	}
}
