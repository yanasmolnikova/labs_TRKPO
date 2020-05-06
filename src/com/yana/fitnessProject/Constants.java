package com.yana.fitnessProject;

public final class Constants {

    public static final int TIMER_STEP = 1000;
    public static final float CALORIES_PER_HOUR_PUSH_UPS = 30;
    public static final float CALORIES_PER_HOUR_JUMP_ROPE = 100;
    public static final float CALORIES_PER_HOUR_SQUATS = 200;

    public static final float CALORIES_PER_SECOND_PUSH_UPS = CALORIES_PER_HOUR_PUSH_UPS / 3600;
    public static final float CALORIES_PER_SECOND_JUMP_ROPE = CALORIES_PER_HOUR_JUMP_ROPE / 3600;
    public static final float CALORIES_PER_SECOND_SQUATS = CALORIES_PER_HOUR_SQUATS / 3600;

    public static final String PUSH_UPS = "PUSH UPS - ";
    public static final String JUMP_ROPE = "JUMP ROPE - ";
    public static final String SQUATS = "SQUATS - ";
    public static final String ALL_RESULTS = "All results - ";
    public static final String CALORIES = ", calories ";
    public static final String TIME = "time: ";

    private Constants(){
    }
}
