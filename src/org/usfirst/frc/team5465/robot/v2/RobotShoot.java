package org.usfirst.frc.team5465.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Solenoid;

import org.usfirst.frc.team5465.robot.RobotAggetator;

import com.ctre.*;

public class RobotShoot extends Thread
{
	private CANTalon shooterMotor1;
	private CANTalon shooterMotor2;
	private Solenoid hoodforward;
	private Solenoid hoodreverse;
	private RobotAggetator aggetate;
	
	private final double SHOOTER_LOW_SPEED = 0.5;
	private final double SHOOTER_HIGH_SPEED = 1.0;
	
	private double speed;
	private boolean angle;
	private boolean start;
	private boolean go;
	
	public RobotShoot(int shootermotorport, int shootermotor1port, int solenoidportforward, int solenoidportreverse, int aggetateport)
	{
		//shooterMotor1 = new CANTalon(shootermotorport);
		aggetate = new RobotAggetator(aggetateport);
		aggetate.start();
		
		shooterMotor2 = new CANTalon(shootermotor1port);
		hoodforward = new Solenoid(solenoidportforward);
		hoodreverse = new Solenoid(solenoidportreverse);
		angle = false;
		speed = 0;
		go = true;
	}
	
	
	public void run()
	{
		while(go)
		{
			this.move();
		}
		
	}
	
	public void move()
	{
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
		
		//shooterMotor1.set(speed);
		//shooterMotor2.set(speed);
		aggetate.updateState(true);
	}
	
	public void updateVals(boolean angle,double speed)
	{
		this.angle = angle;
		this.speed = speed;
	}
	
	public void stopThread()
	{
		go = false;
	}
}

