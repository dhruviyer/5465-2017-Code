package org.usfirst.frc.team5465.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private PIDSubsystem motorSpeed;
	
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
		
		motorSpeed = new PIDSubsystem(5, 0, 0) {
			
			
			
			@Override
			protected void initDefaultCommand() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void usePIDOutput(double output) {
				shooterMotor1.pidWrite(output);
				shooterMotor2.pidWrite(output);
				
			}
			
			@Override
			protected double returnPIDInput() {
				return shooterMotor1.getSpeed();
			}
		};
		
		motorSpeed.setInputRange(0, 26040);
		motorSpeed.setOutputRange(0, 1);
		motorSpeed.setSetpoint(0);
		motorSpeed.enable();
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
		shooterMotor1.set(speed*10/12);
		shooterMotor2.set(speed*10/12);
		aggetate.updateState(true);
	}
	
	public void getMap()
	{
		double lowerspeed = 0;
		double upperspeed = 22640;
		double newspin = this.speed/22640 * -1;
		
	}
	public void feedbackSpinEnable(double rpm)
	{
		motorSpeed.setSetpoint(rpm);
		
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
		motorSpeed.disable();
		aggetate.updateState(false);
	}
	
	public double getVoltage()
	{
		return shooterMotor1.getOutputVoltage();
	}

	
	
	public void changeState(boolean x)
	{
		go = x;
	}
	
	public double getSpeed()
	{
		return shooterMotor1.getSpeed();
	}
}
