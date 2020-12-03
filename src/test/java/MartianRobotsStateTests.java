import com.redbadger.gunjan.martianrobots.MartianRobotCoordinates;
import com.redbadger.gunjan.martianrobots.MartianRobotInstructions;
import com.redbadger.gunjan.martianrobots.MartianRobotOrientations;
import com.redbadger.gunjan.martianrobots.MartianRobotState;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class MartianRobotsStateTests {

    @Test
    public void getOrientationShouldRe7teturnRobotStateCurrentOrientation() throws Exception {
        //Arrange
        MartianRobotState state = new MartianRobotState(MartianRobotOrientations.E, null);

        //Act
        MartianRobotOrientations o = state.getOrientation();

        //Assert
        Assert.assertEquals(MartianRobotOrientations.E, o);
    }

    @Test
    public void getCurrentPositionShouldReturnRobotCurrentPosition() throws Exception {
        //Arrange
        MartianRobotState state = new MartianRobotState(MartianRobotOrientations.E, new MartianRobotCoordinates(1, 2));

        //Act
        MartianRobotCoordinates p = state.getCurrentPosition();

        //Assert
        Assert.assertEquals(new MartianRobotCoordinates(1, 2).getX(), p.getX());
        Assert.assertEquals(new MartianRobotCoordinates(1, 2).getY(), p.getY());
    }

    @Test
    public void getPreviousPositionShouldBeNullAtInitialisation() throws Exception {
        //Arrange
        MartianRobotState state = new MartianRobotState(MartianRobotOrientations.E, new MartianRobotCoordinates(1, 2));

        //Act
        MartianRobotCoordinates p = state.getPreviousPosition();

        //Assert
        Assert.assertEquals(null, p);
    }

    @Test
    public void AddInstructionsAndDeListNextInstructionShouldAddAndRemoveInstructionsInCorrectOrder() throws Exception {
        //Arrange
        MartianRobotState state = new MartianRobotState(MartianRobotOrientations.E, new MartianRobotCoordinates(1, 2));
        LinkedList<MartianRobotInstructions> list = new LinkedList<MartianRobotInstructions>();
        list.add(MartianRobotInstructions.F);
        list.add(MartianRobotInstructions.L);
        state.addInstructions(list);

        //Act
        MartianRobotInstructions x = state.deListNextInstruction();
        MartianRobotInstructions y = state.deListNextInstruction();

        //Assert
        Assert.assertEquals(MartianRobotInstructions.F, x);
        Assert.assertEquals(MartianRobotInstructions.L, y);
    }

    @Test
    public void canExecuteNextInstructionShouldReturnFalseIfInstructionSetIsEmpty() throws Exception {
        //Arrange
        MartianRobotState state = new MartianRobotState(MartianRobotOrientations.E, new MartianRobotCoordinates(1, 2));

        //Act
        boolean canExecuteNextInstruction = state.canExecuteNextInstruction();

        //Assert
        Assert.assertFalse(canExecuteNextInstruction);
    }

    @Test
    public void executeNextInstructionShouldChangeRobotStateCorrectly() throws Exception {
        //Arrange
        MartianRobotState state = new MartianRobotState(MartianRobotOrientations.E, new MartianRobotCoordinates(1, 2));
        LinkedList<MartianRobotInstructions> list = new LinkedList<MartianRobotInstructions>();
        list.add(MartianRobotInstructions.F);
        list.add(MartianRobotInstructions.L);
        state.addInstructions(list);

        //Act & Assert
        Assert.assertTrue(state.canExecuteNextInstruction());

        state.executeNextInstruction();
        Assert.assertEquals(2, state.getCurrentPosition().getX());
        Assert.assertEquals(2, state.getCurrentPosition().getY());
        Assert.assertEquals(MartianRobotOrientations.E, state.getOrientation());
        Assert.assertTrue(state.canExecuteNextInstruction());

        state.executeNextInstruction();
        Assert.assertEquals(MartianRobotOrientations.N, state.getOrientation());
        Assert.assertFalse(state.canExecuteNextInstruction());
    }

    @Test
    public void peekNextInstructionExecutionPositionResultShouldNotAlterRobotState() throws Exception {
        //Arrange
        MartianRobotState state = new MartianRobotState(MartianRobotOrientations.E, new MartianRobotCoordinates(1, 2));
        LinkedList<MartianRobotInstructions> list = new LinkedList<MartianRobotInstructions>();
        list.add(MartianRobotInstructions.L);
        list.add(MartianRobotInstructions.F);
        state.addInstructions(list);

        //Act & Assert
        MartianRobotCoordinates p = state.peekNextInstructionExecutionPositionResult();
        Assert.assertEquals(1, p.getX());
        Assert.assertEquals(2, p.getY());
        Assert.assertTrue(state.getInstructions().size() == 2);

        state.deListNextInstruction();

        p = state.peekNextInstructionExecutionPositionResult();
        Assert.assertTrue(state.getInstructions().size() == 1);

        Assert.assertEquals(1, state.getCurrentPosition().getX());
        Assert.assertEquals(2, state.getCurrentPosition().getY());
        Assert.assertEquals(MartianRobotOrientations.E, state.getOrientation());
    }

}