package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.*;
import frc.robot.commands.Arms.setIntake;
import frc.robot.commands.Arms.lower.lGo;
import frc.robot.commands.Arms.upper.uGo;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Arms.lower;
import frc.robot.subsystems.Arms.upper;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

public class pickupCube extends SequentialCommandGroup 
{
    public pickupCube(lower lower, upper upper, Swerve m_driveTrain)
    {
        addCommands(
            // set arms to pickup position
            new ParallelDeadlineGroup(
                new uGo(1.3, 118000, upper, false),
                // run intake
                new setIntake(0.7, m_driveTrain).withTimeout(3.5)
            ),
            // set arm back to normal
            new uGo(1.3, 6000, upper, false)
        );
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}