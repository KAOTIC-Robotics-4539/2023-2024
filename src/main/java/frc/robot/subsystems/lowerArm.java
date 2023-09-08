package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class lowerArm extends SubsystemBase 
{
    private WPI_TalonFX m_lowerArm;
    DigitalInput m_home_lower;
    
    public lowerArm() 
    {
        m_lowerArm = new WPI_TalonFX(Constants.lowerArmPort);
        m_lowerArm.configFactoryDefault();
        m_lowerArm.setInverted(false);
        m_lowerArm.setNeutralMode(NeutralMode.Brake);
        m_home_lower = new DigitalInput(8);
    }

    @Override
    public void periodic() {
      SmartDashboard.putNumber("Lower Arm Revs", m_lowerArm.getSelectedSensorPosition());
      m_lowerArm.feed();
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
          if (m_lowerArm.getSelectedSensorPosition() > Constants.lowerArmMin)
          {
            m_lowerArm.set(-speed/2);
          }
          else
          {
            m_lowerArm.set(0.20);
          }
        }
        /* if Negative */
        else if (speed < 0)
        {
          if (m_lowerArm.getSelectedSensorPosition() < Constants.lowerArmMax)
          {
            m_lowerArm.set(-speed/2);
          }
          else
          {
            m_lowerArm.set(-0.20);
          }
        }
      }
      else{
        m_lowerArm.set(0.0);
      }
    }
    public void resetEncoder(){
      m_lowerArm.setSelectedSensorPosition(0);
    }
    public double getEncoder(){
      return m_lowerArm.getSelectedSensorPosition();
    }
    public void lockMotor(){
      m_lowerArm.setNeutralMode(NeutralMode.Brake);
    }
}