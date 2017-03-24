package org.usfirst.frc.team5465.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

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
	private RobotAggetator aggetate;
	
	private double speed;

	private boolean go;
	
	public RobotShoot(int shootermotorport, int shootermotor1port, int solenoidportforward, int solenoidportreverse, int aggetateport)
	{
		//shooterMotor1 = new CANTalon(shootermotorport);
		aggetate = new RobotAggetator(aggetateport);
		aggetate.start();
		
		shooterMotor1 = new CANTalon(shootermotorport);
		shooterMotor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooterMotor1.reverseSensor(true);

		shooterMotor1.setPosition(0);
		shooterMotor2 = new CANTalon(shootermotor1port);
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
		shooterMotor1.set(speed);
		shooterMotor2.set(speed);
		aggetate.updateState(false);
	}
	
	public void feedbackSpin()
	{
		double startpoint = -0.5;
		double speed1 = this.speed;
		double error = speed1 - this.getEncSpeed1();
		
		while(Math.abs(error) > 10)
		{
			if(error >= 0)
			{
				shooterMotor1.set(startpoint +getIncrement(error) );
				shooterMotor2.set(startpoint +getIncrement(error));
			}
			else
			{
				shooterMotor1.set(startpoint  - getIncrement(error) );
				shooterMotor2.set(startpoint - getIncrement(error));
			}
			error = this.speed - this.getEncSpeed1();
		}
	}
	
	public double getIncrement(double error)
	{
		double a = 0;
		double abserror = Math.abs(error);
		if(abserror > 500) a = 0.01;
		else if(abserror > 100) a= 0.005;
		else if(abserror > 10) a = 0.0001;
		return a;
	}
	
	
	public void updateVals(double speed)
	{
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
	
	public double getEncSpeed1()
	{
		return shooterMotor1.getSpeed();
	}
}
