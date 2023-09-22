package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Arms.lower;
import frc.robot.subsystems.Arms.upper;

public class ResetPosition extends CommandBase 
{
    private final lower m_lowerArm;
    private final upper m_upperArm;
    private final Swerve m_swerve;

    public ResetPosition(Swerve mswerve, lower larmsub, upper uarmsub) 
    {
        m_swerve = mswerve;
        m_lowerArm = larmsub;
        m_upperArm = uarmsub;
    }
    
    @Override
    public void initialize() {
        m_swerve.zeroGyro(); //TODO: Test this call
        m_lowerArm.resetEncoder();
        m_upperArm.resetEncoder();
    }
    @Override
    public void execute() 
    {

    }
    @Override
    public void end(boolean interrupted) {
    }
    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}