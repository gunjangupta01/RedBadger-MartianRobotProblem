package com.redbadger.gunjan.martianrobots;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class MartianRobotDataProcessor {
    private final String fileDir;
    private MartianRobotCoordinates gridBounds;
    private LinkedList<MartianRobotState> robotStates;

    public MartianRobotDataProcessor(String fileDir) {
        this.fileDir = fileDir;
        this.robotStates = new LinkedList<MartianRobotState>();
        this.gridBounds = null;
    }

    public MartianRobotStateMachine generateApplicationState() {

        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(fileDir);
            br = new BufferedReader(fr);

            String gridBoundsString;

            if ((gridBoundsString = br.readLine()) != null) {
                gridBounds = generateGrid(gridBoundsString);
            }

            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                if(!currentLine.equals("")) {
                    String initialRobotState = currentLine;
                    String instructions = br.readLine();
                    MartianRobotState rs = generateRobotState(initialRobotState);
                    rs.addInstructions(generateInstructionList(instructions));
                    robotStates.add(rs);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return new MartianRobotStateMachine(robotStates, gridBounds);
    }

    private MartianRobotState generateRobotState(String initialState) {
        String delimiters = " ";
        StringTokenizer tokenizer = new StringTokenizer(initialState, delimiters);

        int xCoordinate = Integer.parseInt(tokenizer.nextToken());
        int yCoordinate = Integer.parseInt(tokenizer.nextToken());
        MartianRobotCoordinates startCoordinates = new MartianRobotCoordinates(xCoordinate, yCoordinate);

        MartianRobotOrientations orientation = MartianRobotOrientations.valueOf(tokenizer.nextToken());

        return new MartianRobotState(orientation, startCoordinates);
    }

    private LinkedList<MartianRobotInstructions> generateInstructionList(String instructions) {

        LinkedList<MartianRobotInstructions> instructionList = new LinkedList<MartianRobotInstructions>();

        for (char c : instructions.toCharArray()) {
            MartianRobotInstructions i = MartianRobotInstructions.valueOf(Character.toString(c));
            instructionList.add(i);
        }

        return instructionList;
    }

    private MartianRobotCoordinates generateGrid(String gridString) {
        String delimiters = " ";
        StringTokenizer tokenizer = new StringTokenizer(gridString, delimiters);

        int xBounds = Integer.parseInt(tokenizer.nextToken());
        int yBounds = Integer.parseInt(tokenizer.nextToken());

        return new MartianRobotCoordinates(xBounds, yBounds);
    }

}
