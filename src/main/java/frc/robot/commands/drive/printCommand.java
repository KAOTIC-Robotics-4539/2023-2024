package frc.robot.commands.drive;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class printCommand extends CommandBase 
{
    private final String m_message;

    public printCommand(String message) 
    {
        m_message = message;
    }
    
    @Override
    public void initialize() {
    }
    @Override
    public void execute() 
    {
        DriverStation.reportError(m_message, false);
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