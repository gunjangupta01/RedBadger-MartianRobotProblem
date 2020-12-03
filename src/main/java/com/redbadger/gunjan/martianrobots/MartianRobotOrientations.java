package com.redbadger.gunjan.martianrobots;

public enum MartianRobotOrientations {
    N, E, S, W;

    public static MartianRobotOrientations clockwiseRotation(MartianRobotOrientations martianRobotOrientations) {

        switch(martianRobotOrientations) {
            case N: return E;
            case E: return S;
            case S: return W;
            case W: return N;
            default: return null;
        }
    }

    public static MartianRobotOrientations antiClockwiseRotation(MartianRobotOrientations martianRobotOrientations) {

        switch(martianRobotOrientations) {
            case N: return W;
            case E: return N;
            case S: return E;
            case W: return S;
            default: return null;
        }
    }
}
