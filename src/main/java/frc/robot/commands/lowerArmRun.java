package frc.robot.commands;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.lowerArm;

public class lowerArmRun extends CommandBase 
{
    private final lowerArm m_lowerarm;
    private DoubleSupplier m_setpoint;

    public lowerArmRun(DoubleSupplier setpoint, lowerArm subsystem) 
    {
        m_setpoint = setpoint;
        m_lowerarm = subsystem;
        addRequirements(m_lowerarm);
    }

    @Override
    public void initialize() {m_lowerarm.resetEncoder();}

    @Override
    public void execute() {
        double currentcommand = m_setpoint.getAsDouble();
        if (Math.abs(currentcommand) < .25 && Math.abs(currentcommand) >= 0.0){
            currentcommand = 0;
        }
        m_lowerarm.setArm(currentcommand);
    }

    @Override
    public void end(boolean interrupted) {
        m_lowerarm.setArm(0.0);
    }

    @Override
    public boolean isFinished() { return false; }

    @Override
    public boolean runsWhenDisabled() { return false; }
}