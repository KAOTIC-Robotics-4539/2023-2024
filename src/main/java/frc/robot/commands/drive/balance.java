package frc.robot.commands.drive;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;
public class balance extends CommandBase 
{
    private final Swerve m_driveTrain;
    public balance(Swerve subsystem) 
    {
        m_driveTrain = subsystem;
        addRequirements(m_driveTrain);
    }

    @Override
    public void initialize() {
        m_driveTrain.Brake();
    }
    @Override
    public void execute() {
        if (m_driveTrain.gyro.getPitch() > 5.0){ // TODO: TEST CALL
           // m_driveTrain.arcadeDrive(-0.27, 0);
        }
        else if (m_driveTrain.gyro.getPitch() < -5.0){ // TODO: TEST CALL
           // m_driveTrain.arcadeDrive(0.27, 0);
        }
        else{
           // m_driveTrain.arcadeDrive(0, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        //m_driveTrain.arcadeDrive(0, 0);
        //m_driveTrain.Brake(NeutralMode.Coast);
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