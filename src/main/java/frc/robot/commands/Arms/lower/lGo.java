package frc.robot.commands.Arms.lower;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arms.lower;

public class lGo extends CommandBase 
{
    private final lower m_lowerarm;
    private double m_speed;
    private double m_target;
    private boolean m_resetEncoder;
    private boolean Finished;

    public lGo(double speed, double target, lower subsystem, boolean resetEncoder) 
    {
        m_speed = speed;
        m_target = target;
        m_resetEncoder = resetEncoder;
        m_lowerarm = subsystem;
        addRequirements(m_lowerarm);
    }

    @Override
    public void initialize() {
        if (m_resetEncoder){
            m_lowerarm.resetEncoder();
        }
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Lower Arm Rev", m_lowerarm.getEncoder());
        if ((m_lowerarm.getEncoder() > m_target - 300) && (m_lowerarm.getEncoder() < m_target + 300))
        {
            Finished = true;
        }
        else
        {
            if (m_lowerarm.getEncoder() < m_target){
                m_lowerarm.setArm(-m_speed);
            }
            else if (m_lowerarm.getEncoder() > m_target)
            {
                m_lowerarm.setArm(m_speed);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_lowerarm.setArm(0.0);
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