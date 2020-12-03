package com.redbadger.gunjan.martianrobots;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MartianRobotStateMachine {

    private final LinkedList<MartianRobotState> robotStates;
    private final MartianRobotCoordinates gridBounds;
    private Set<MartianRobotCoordinates> robotScents;

    public MartianRobotStateMachine(LinkedList<MartianRobotState> robotStates, MartianRobotCoordinates gridBounds) {
        this.robotStates = robotStates;
        this.gridBounds = gridBounds;
        this.robotScents = new HashSet<MartianRobotCoordinates>();
    }

    public MartianRobotCoordinates getGridBounds() {
        return gridBounds;
    }

    public Set<MartianRobotCoordinates> getRobotScents() {
        return robotScents;
    }

    public LinkedList<MartianRobotState> getRobotStates() {
        return robotStates;
    }

    public String triggerStateMachine() {
        StringBuilder sb = new StringBuilder();
        for (MartianRobotState state : robotStates) {
            executeRobotInstructions(state);
            sb.append(state.toString() + '\n');
        }
        return sb.toString();
    }

    private void executeRobotInstructions(MartianRobotState state) {

        while(state.canExecuteNextInstruction()) {
            if(isOnScentedPosition(state.getCurrentPosition())
                    && isOffGridPosition(state.peekNextInstructionExecutionPositionResult())) {
                state.deListNextInstruction();
            } else {
                state.executeNextInstruction();
                if(isOffGridPosition(state.getCurrentPosition())) {
                    state.setLostState(true);
                    //Add last valid grid position to robotScent set
                    addRobotScent(state.getPreviousPosition());
                    break;
                }
            }
        }
    }

    private boolean isOnScentedPosition(MartianRobotCoordinates p) {
        return getRobotScents().contains(p);
    }

    private boolean isOffGridPosition(MartianRobotCoordinates p) {
        return (p.x > gridBounds.x
                || p.y > gridBounds.y
                || p.x < 0
                || p.y < 0);
    }

    private void addRobotScent(MartianRobotCoordinates p) {
        robotScents.add(p);
    }

}
