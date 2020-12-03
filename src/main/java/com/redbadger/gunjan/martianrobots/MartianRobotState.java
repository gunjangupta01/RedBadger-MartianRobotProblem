package com.redbadger.gunjan.martianrobots;

import java.util.LinkedList;

import static com.redbadger.gunjan.martianrobots.MartianRobotInstructions.F;
import static com.redbadger.gunjan.martianrobots.MartianRobotInstructions.R;
import static com.redbadger.gunjan.martianrobots.MartianRobotOrientations.antiClockwiseRotation;
import static com.redbadger.gunjan.martianrobots.MartianRobotOrientations.clockwiseRotation;

public class MartianRobotState {

    private LinkedList<MartianRobotInstructions> instructions;
    private MartianRobotOrientations orientation;
    private MartianRobotCoordinates currentPosition;
    private MartianRobotCoordinates previousPosition;
    private boolean isLost;

    public MartianRobotState(MartianRobotOrientations orientation, MartianRobotCoordinates position) {
        this.orientation = orientation;
        this.currentPosition = position;
        this.instructions = new LinkedList<MartianRobotInstructions>();
        this.isLost = false;
        this.previousPosition = null;
    }

    public MartianRobotOrientations getOrientation() {
        return orientation;
    }

    public MartianRobotCoordinates getCurrentPosition() {
        return currentPosition;
    }

    public MartianRobotCoordinates getPreviousPosition() {
        return previousPosition;
    }

    public void setLostState(boolean isLost) {
        this.isLost = isLost;
    }

    public LinkedList<MartianRobotInstructions> getInstructions() {
        return instructions;
    }

    public void addInstructions(LinkedList<MartianRobotInstructions> instructions) {
        this.instructions.addAll(instructions);
    }

    public MartianRobotInstructions deListNextInstruction() {
        assert(!instructions.isEmpty());
        return instructions.poll();
    }


    public boolean canExecuteNextInstruction() {
        return (instructions.size() > 0);
    }

    public void executeNextInstruction() {
        assert(!instructions.isEmpty());

        MartianRobotInstructions i = deListNextInstruction();

        switch (i) {
            case F:
                previousPosition = currentPosition;
                currentPosition = move(i);
                break;
            default:
                orientation = rotate(i);
        }
    }

    public MartianRobotCoordinates peekNextInstructionExecutionPositionResult() {
        assert(!instructions.isEmpty());
        return move(instructions.peek());
    }

    private MartianRobotOrientations rotate(MartianRobotInstructions i) {
        if(i == R) {
            return clockwiseRotation(orientation);
        } else {
            return antiClockwiseRotation(orientation);
        }
    }

    private MartianRobotCoordinates move(MartianRobotInstructions i) {
        int x = currentPosition.x;
        int y = currentPosition.y;

        if(i == F) {
            switch (getOrientation()) {
                case N: return new MartianRobotCoordinates(x, y + 1);
                case S: return new MartianRobotCoordinates(x, y - 1);
                case E: return new MartianRobotCoordinates(x + 1, y);
                case W: return new MartianRobotCoordinates(x - 1, y);
            }
        }
        return currentPosition;
    }

    @Override
    public String toString() {
        if(isLost) {
            return String.format("%d %d %s LOST", previousPosition.x, previousPosition.y, orientation.toString());
        } else {
            return String.format("%d %d %s", currentPosition.x, currentPosition.y, orientation.toString());
        }
    }
}
