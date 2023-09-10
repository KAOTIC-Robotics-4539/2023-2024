package frc.robot.commands;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.upperArm;

public class upperArmRun extends CommandBase 
{
    private final upperArm m_upperarm;
    private DoubleSupplier m_setpoint;

    public upperArmRun(DoubleSupplier setpoint, upperArm subsystem) 
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