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

public class placeCubeMiddle extends SequentialCommandGroup 
{
    public placeCubeMiddle(lower lower, upper upper, Swerve m_driveTrain)
    {
        addCommands(
            // set arms to place position
            Commands.parallel(
                new lGo(1.3, 73000, lower, false),
                new uGo(1, 73000, upper, false)
            ),
            // place cone
            new setIntake(-0.5, m_driveTrain).withTimeout(0.5),
            // set arms back to normal, maybe 0 them?
            Commands.parallel(
                new uGo(1.3, 2000, upper, false),
                new lGo(1, 8000, lower, false)
            )
        );
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}