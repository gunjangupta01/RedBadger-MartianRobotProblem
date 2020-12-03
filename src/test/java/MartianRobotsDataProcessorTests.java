import com.redbadger.gunjan.martianrobots.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedList;

public class MartianRobotsDataProcessorTests {

    @Test
    public void ShouldGenerateGridBoundsCorrectly() {
        //Arrange
        MartianRobotDataProcessor dataProcessor = new MartianRobotDataProcessor("src/test/resources/test_input.txt");
        //Act
        MartianRobotStateMachine stateMachine = dataProcessor.generateApplicationState();

        //Assert
        Assert.assertEquals(5, stateMachine.getGridBounds().getX());
        Assert.assertEquals(3, stateMachine.getGridBounds().getY());
    }

    @Test
    public void ShouldGenerateRobotStatesCorrectly() {
        //Arrange
        MartianRobotDataProcessor dataProcessor = new MartianRobotDataProcessor("src/test/resources/test_input.txt");

        //Act
        MartianRobotStateMachine stateMachine = dataProcessor.generateApplicationState();

        //Assert
        Assert.assertEquals(stateMachine.getRobotStates().size(), 3);

        MartianRobotState r1 = stateMachine.getRobotStates().poll();
        MartianRobotState r2 = stateMachine.getRobotStates().poll();
        MartianRobotState r3 = stateMachine.getRobotStates().poll();

        Assert.assertEquals(MartianRobotOrientations.E, r1.getOrientation());

        Assert.assertEquals(MartianRobotOrientations.N, r2.getOrientation());

        Assert.assertEquals(0, r3.getCurrentPosition().getX());
        Assert.assertEquals(3, r3.getCurrentPosition().getY());
        Assert.assertEquals(MartianRobotOrientations.W, r3.getOrientation());
    }

    @Test
    public void ShouldGenerateRobotInstructionsCorrectly() {
        //Arrange
        MartianRobotDataProcessor dataProcessor = new MartianRobotDataProcessor("src/test/resources/test_input.txt");

        //Act
        MartianRobotStateMachine stateMachine = dataProcessor.generateApplicationState();

        //Assert
        LinkedList<MartianRobotInstructions> list = stateMachine.getRobotStates().poll().getInstructions();

        Assert.assertEquals(MartianRobotInstructions.R, list.poll());
        Assert.assertEquals(MartianRobotInstructions.F, list.poll());
        Assert.assertEquals(MartianRobotInstructions.L, list.poll());
    }

}
