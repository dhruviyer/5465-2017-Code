package org.usfirst.frc.team5465.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;

/***
 * 
 * @author Dhruv
 * Handles all robot targeting and shooting ops
 */
public class RobotShoot extends Thread
{
	private CANTalon shooterMotor1;
	private CANTalon shooterMotor2;
	//private Solenoid hoodforward;
	//private Solenoid hoodreverse;
	//private Solenoid feederforward;
	//private Solenoid feederreverse;
	
	private RobotAggetator aggetate;
	
	private double speed;
	private boolean angle;

	private boolean go;
	
	public RobotShoot(int shootermotorport, int shootermotor1port, int solenoidportforward, int solenoidportreverse, int aggetateport)
	{
		//shooterMotor1 = new CANTalon(shootermotorport);
		aggetate = new RobotAggetator(aggetateport);
		aggetate.start();
		
		shooterMotor1 = new CANTalon(shootermotorport);
		shooterMotor2 = new CANTalon(shootermotor1port);
		//hoodforward = new Solenoid(solenoidportforward);
		//hoodreverse = new Solenoid(solenoidportreverse);
		
		angle = false;
		speed = 0;
		go = false;
	}
	
	
	public void run()
	{
		while(true)
		{
			if(go) this.move();
			else this.idle();
		}
		
	}
	
	public void move()
	{
		/**
		if(angle)
		{
			hoodforward.set(true);
			hoodreverse.set(false);
		}
		else
		{
			hoodforward.set(false);
			hoodreverse.set(true);
		}
		**/
		
		shooterMotor1.set(speed);
		shooterMotor2.set(speed);
		aggetate.updateState(true);
	}
	
	public void feedbackSpin()
	{
		double projectedspin = this.speed;
		double realspin = this.getEncoderValue();
		
		while(Math.abs(projectedspin - realspin ) > 10)
		{
			if(projectedspin - realspin > 0)
			{
				
			}
		}
	}
	
	public double getEncoderValue()
	{
		//may need to map this value to a better range
		return shooterMotor1.getAnalogInVelocity();
	}
	
	public void updateVals(boolean angle,double speed)
	{
		this.angle = angle;
		this.speed = speed;
	}
	
	public void idle()
	{
		shooterMotor1.set(0);
		shooterMotor2.set(0);
		aggetate.updateState(false);
	}
	
	
	
	public void changeState(boolean x)
	{
		go = x;
	}
}
