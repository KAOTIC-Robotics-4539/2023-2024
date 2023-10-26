package frc.robot.autos;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
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
        if (m_driveTrain.getPitch() > 5.0){ // TODO: TEST CALL
            m_driveTrain.drive(new Translation2d(-0.3, 0).times(Constants.Swerve.maxSpeed), 0, false, true);
        }
        else if (m_driveTrain.getPitch() < -5.0){ // TODO: TEST CALL
           m_driveTrain.drive(new Translation2d(0.3, 0).times(Constants.Swerve.maxSpeed), 0, false, true);
        }
        else{
            m_driveTrain.drive(new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 0, false, true);
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_driveTrain.drive(new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 0, false, true);
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