package com.redbadger.gunjan.martianrobots;

public class MartianRobotApplication {

    public static void main(String[] args) {

        MartianRobotDataProcessor dataProcessor = new MartianRobotDataProcessor(args[0]);

        MartianRobotStateMachine stateMachine = dataProcessor.generateApplicationState();

        String output = stateMachine.triggerStateMachine();

        System.out.println(output);
    }
}
