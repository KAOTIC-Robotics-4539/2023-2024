package frc.robot;
import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autos.placeCone;
import frc.robot.commands.*;
import frc.robot.commands.Arms.setIntake;
import frc.robot.commands.Arms.lower.lGo;
import frc.robot.commands.Arms.lower.lRun;
import frc.robot.commands.Arms.upper.uGo;
import frc.robot.commands.Arms.upper.uRun;
import frc.robot.commands.drive.TeleopSwerve;
import frc.robot.commands.drive.TurnToDeg;
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

    /* Auto List */
    SendableChooser<Command> m_chooser = new SendableChooser<>();

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
        SmartDashboard.putData("Auto Mode", m_chooser);
        // To add a new option: The first string is whatever you want to call it, the second must be the exact name of the file without .path
        m_chooser.setDefaultOption("Place Upper Cone", new placeCone(m_lowerArm, m_upperArm, s_Swerve));
        m_chooser.addOption("PCB BL", getPathPlannerCommand("PCB BL"));
        m_chooser.addOption("PCB BR", getPathPlannerCommand("PCB BR"));
        m_chooser.addOption("PCB RL", getPathPlannerCommand("PCB RL"));
        m_chooser.addOption("PCB RR", getPathPlannerCommand("PCB RR"));
        m_chooser.addOption("Dont click unless you know what", getPathPlannerCommand("Accident"));
        m_chooser.addOption("turn 2", getPathPlannerCommand("90 turn"));
        m_chooser.addOption("Turn 90", new TeleopSwerve(
            s_Swerve, 
            () -> 0, 
            () -> 0, 
            () -> -90, // possible fix for rotation
            () -> false
        ));
        m_chooser.addOption("New New", getPathPlannerCommand("New New Path"));
        //m_chooser.setDefaultOption("idk path", getPathPlannerCommand("New Path"));
        //m_chooser.addOption("Eric's Path", getPathPlannerCommand("Eric's Path"));
        //m_chooser.addOption("Turn 90 deg?", getPathPlannerCommand("90 turn"));
        // preloaded cone place cone drive back far side of field
        //m_chooser.addOption("Auto #1", getPathPlannerCommand("Auto 1"));
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
        resetButton.onTrue(new ResetPosition(s_Swerve));
        //armPickupButton.onTrue(new uGo(1, 170000, m_upperArm, false));
        //armPickupButton2.onTrue(new lGo(1, 179000, m_lowerArm, false));
    }


    private Command getPathPlannerCommand(String name)
    {
        List<PathPlannerTrajectory> pathGroup = PathPlanner.loadPathGroup(name, new PathConstraints(4, 3));
        HashMap<String, Command> eventMap = new HashMap<>();
        switch (name){
            case "New Path":
                // eventMap.put("marker1", new PrintCommand("Passed marker 1"));
                break;
            case "Eric's Path":
                // add events
                break;
            case "90 turn":
                // add events
                eventMap.put("test1", new setIntake(0.20, s_Swerve));
                eventMap.put("test2", new setIntake(0.0, s_Swerve));
                break;
            case "Auto 1":
                // add events
                eventMap.put("event1", new placeCone(m_lowerArm, m_upperArm, s_Swerve));
                break;
            default:
                break;
        }
        // catch all
        if (name.contains("PCB ")){
            eventMap.put("event1", new placeCone(m_lowerArm, m_upperArm, s_Swerve));
        }
       
// Create the AutoBuilder. This only needs to be created once when robot code starts, not every time you want to create an auto command. A good place to put this is in RobotContainer along with your subsystems.
        SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
            s_Swerve::getPose, // Pose2d supplier
            s_Swerve::resetOdometry, // Pose2d consumer, used to reset odometry at the beginning of auto
            Constants.Swerve.swerveKinematics, // SwerveDriveKinematics
            // normal is 5.0
            new PIDConstants(5, 0.0, 0.0), // PID constants to correct for translation error (used to create the X and Y PID controllers)
            // normal is 0.5
            new PIDConstants(6, 0.0, 0.0), // PID constants to correct for rotation error (used to create the rotation controller)
            s_Swerve::setModuleStates, // Module states consumer used to output to the drive subsystem
            eventMap,
            true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
            s_Swerve // The drive subsystem. Used to properly set the requirements of path following commands
        );

        Command fullAuto = autoBuilder.fullAuto(pathGroup);
        return fullAuto;
    }
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() { return m_chooser.getSelected(); }
}
