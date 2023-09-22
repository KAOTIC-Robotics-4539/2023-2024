package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;
public class TurnToDeg extends CommandBase 
{
    private final Swerve m_swerve;
    private double m_target;
    private boolean m_isLeft;
    public TurnToDeg(Swerve subsystem, int targetangle, boolean isLeft) 
    {
        m_target = targetangle;
        m_swerve = subsystem;
        m_isLeft = isLeft;
        addRequirements(m_swerve);
    }
    @Override
    public void initialize() 
    {
        m_swerve.zeroGyro();
    }
    @Override
    public void execute() 
    {
        int getHeadingInt = (int) Math.round(m_swerve.getYaw().getDegrees()); // TODO: Test this call
        DriverStation.reportWarning("Heading: " + getHeadingInt, false);
        if (m_swerve.getYaw().getDegrees() != m_target)
        {
            if (m_isLeft){
                //m_driveTrain.arcadeDrive(0.0, -0.3); TODO: Get values from TeleopSwerve() testing
            }
            else if (!m_isLeft){
                //m_driveTrain.arcadeDrive(0.0, 0.3); TODO: Get values from TeleopSwerve() testing
            }
        }
    }
    @Override
    public void end(boolean interrupted) 
    {
        //m_driveTrain.arcadeDrive(0, 0); TODO: Get values from TeleopSwerve() testing
    }

    @Override
    public boolean isFinished() {
        int getHeadingInt = (int) Math.round(m_swerve.getYaw().getDegrees()); // TODO: Test this call
        if (getHeadingInt == m_target)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}