package frc.robot.commands.drive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class brakeCommand extends CommandBase 
{
    private final Swerve m_swerve;

    public brakeCommand(Swerve mswerve) 
    {
        m_swerve = mswerve;
    }
    
    @Override
    public void initialize() {
    }
    @Override
    public void execute() 
    {
        m_swerve.Brake();
    }
    @Override
    public void end(boolean interrupted) {
    }
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}