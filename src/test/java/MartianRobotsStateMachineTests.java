import com.redbadger.gunjan.martianrobots.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedList;

public class MartianRobotsStateMachineTests {

    @Test
    public void triggerStateMachineShouldExecuteInstructionsInCorrectOrder() {
        //Arrange
        MartianRobotState state = new MartianRobotState(MartianRobotOrientations.E, new MartianRobotCoordinates(1, 1));
        LinkedList<MartianRobotInstructions> instructionList = new LinkedList<MartianRobotInstructions>();
        instructionList.add(MartianRobotInstructions.R);
        instructionList.add(MartianRobotInstructions.F);
        instructionList.add(MartianRobotInstructions.L);
        state.addInstructions(instructionList);

        MartianRobotState state2 = new MartianRobotState(MartianRobotOrientations.N, new MartianRobotCoordinates(3, 2));
        LinkedList<MartianRobotInstructions> instructionList2 = new LinkedList<MartianRobotInstructions>();
        instructionList2.add(MartianRobotInstructions.F);
        instructionList2.add(MartianRobotInstructions.R);
        state2.addInstructions(instructionList2);

        LinkedList<MartianRobotState> robotStates = new LinkedList<MartianRobotState>();
        robotStates.add(state);
        robotStates.add(state2);

        MartianRobotStateMachine stateMachine = new MartianRobotStateMachine(robotStates, new MartianRobotCoordinates(5, 3));

        String expectedOutput = "1 0 E\n3 3 E\n";

        //Act
        String output = stateMachine.triggerStateMachine();

        //Assert
        Assert.assertEquals(expectedOutput, output);
    }

}
