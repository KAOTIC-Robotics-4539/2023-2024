package frc.robot.commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.upperArm;

public class upperArmGoTo extends CommandBase 
{
    private final upperArm m_upperarm;
    private double m_speed;
    private double m_target;
    private boolean m_resetEncoder;
    private boolean Finished;

    public upperArmGoTo(double speed, double target, upperArm subsystem, boolean resetEncoder) 
    {
        m_speed = speed;
        m_target = target;
        m_resetEncoder = resetEncoder;
        m_upperarm = subsystem;
        addRequirements(m_upperarm);
    }

    @Override
    public void initialize() {
        if (m_resetEncoder){
            m_upperarm.resetEncoder();
        }
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Upper Arm Rev", m_upperarm.getEncoder());
        if ((m_upperarm.getEncoder() > m_target - 300) && (m_upperarm.getEncoder() < m_target + 300))
        {
            Finished = true;
        }
        else
        {
            if (m_upperarm.getEncoder() < m_target)
            {
                m_upperarm.setArm(-m_speed);
            }
            else if (m_upperarm.getEncoder() > m_target)
            {
                m_upperarm.setArm(m_speed);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_upperarm.setArm(0.0);
    }

    @Override
    public boolean isFinished() 
    { 
        if (Finished){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean runsWhenDisabled() { return false; }
}