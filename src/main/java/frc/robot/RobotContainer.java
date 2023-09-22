package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.commands.Arms.setIntake;
import frc.robot.commands.Arms.lower.lRun;
import frc.robot.commands.Arms.upper.uRun;
import frc.robot.commands.drive.TeleopSwerve;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Arms.lower;
import frc.robot.subsystems.Arms.upper;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final XboxController driver = new XboxController(0);
    private final XboxController coDriver = new XboxController(1);

    /* Drive Controls */
    private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;

    /* Driver Buttons */
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    public final lower m_lowerArm = new lower();
    public final upper m_upperArm = new upper();



    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> driver.getRawAxis(translationAxis), 
                () -> driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), // possible fix for rotation
                () -> robotCentric.getAsBoolean()
            )
        );
        m_lowerArm.setDefaultCommand(new lRun(() ->  coDriver.getLeftY(), m_lowerArm));
        m_upperArm.setDefaultCommand(new uRun(() ->  coDriver.getRightY(), m_upperArm));
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        final JoystickButton headOpen_start = new JoystickButton(coDriver, XboxController.Button.kRightBumper.value);  
        final JoystickButton headClose_Back = new JoystickButton(coDriver, XboxController.Button.kLeftBumper.value);
        final JoystickButton resetButton = new JoystickButton(driver, XboxController.Button.kX.value);
        //final JoystickButton armPickupButton = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
        //final JoystickButton armPickupButton2 = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

        headClose_Back.whileTrue(new setIntake(0.7, s_Swerve));
        headOpen_start.whileTrue(new setIntake(-0.5, s_Swerve));
        resetButton.onTrue(new ResetPosition(s_Swerve, m_lowerArm, m_upperArm));
        //armPickupButton.onTrue(new upperArmGoTo(1, 170000, m_upperArm, false));
        //armPickupButton2.onTrue(new lowerArmGoTo(1, 179000, m_lowerArm, false));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve);
    }
}
