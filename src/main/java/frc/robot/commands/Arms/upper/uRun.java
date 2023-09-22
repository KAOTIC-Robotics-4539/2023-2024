package frc.robot.commands.Arms.upper;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arms.upper;

public class uRun extends CommandBase 
{
    private final upper m_upperarm;
    private DoubleSupplier m_setpoint;

    public uRun(DoubleSupplier setpoint, upper subsystem) 
    {
        m_setpoint = setpoint;
        m_upperarm = subsystem;
        addRequirements(m_upperarm);
    }

    @Override
    public void initialize() {m_upperarm.resetEncoder();}

    @Override
    public void execute() {
        double currentcommand = m_setpoint.getAsDouble();
        if (Math.abs(currentcommand) < .25 && Math.abs(currentcommand) >= 0.0){
            currentcommand = 0;
        }
        m_upperarm.setArm(currentcommand);
    }

    @Override
    public void end(boolean interrupted) {
        m_upperarm.setArm(0.0);
    }

    @Override
    public boolean isFinished() { return false; }

    @Override
    public boolean runsWhenDisabled() { return false; }
}