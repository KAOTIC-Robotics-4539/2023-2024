package frc.robot.subsystems.Arms;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class upper extends SubsystemBase 
{
    private WPI_TalonFX upperArm;
    public upper() 
    {
        upperArm = new WPI_TalonFX(Constants.upperArmPort);
        upperArm.configFactoryDefault();
        upperArm.setInverted(true);
        upperArm.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void periodic() {
      SmartDashboard.putNumber("Upper Arm Revs", upperArm.getSelectedSensorPosition());
      upperArm.feed();
    }

    @Override
    public void simulationPeriodic() {}

    public void setArm(double speed)
    {
      if (speed != 0)
      {
        /* if Positive */
        if (speed > 0)
        {
          if (upperArm.getSelectedSensorPosition() > Constants.upperArmMin)
          {
            upperArm.set(-speed/2);
          }
          else
          {
            upperArm.set(0.20);
          }
        }
        /* if Negative */
        else if (speed < 0)
        {
          if (upperArm.getSelectedSensorPosition() < Constants.upperArmMax)
          {
            upperArm.set(-speed/2);
          }
          else
          {
            upperArm.set(-0.20);
          }
        }
      }
      else{
        upperArm.set(0.0);
      }
    }

    public void resetEncoder(){
      upperArm.setSelectedSensorPosition(0);
    }
    public double getEncoder(){
      return upperArm.getSelectedSensorPosition();
    }
    public void lockMotor(){
      upperArm.setNeutralMode(NeutralMode.Brake);
    }
}