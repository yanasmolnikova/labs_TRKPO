package com.yana.fitnessProject;

public final class Constants {

    public static final int TIMER_STEP = 1000;
    public static final int CALORIES_PER_HOUR_PUSH_UPS = 30;
    public static final int CALORIES_PER_HOUR_JUMP_ROPE = 100;
    public static final int CALORIES_PER_HOUR_SQUATS = 200;

    public static final int CALORIES_PER_SECOND_PUSH_UPS = CALORIES_PER_HOUR_PUSH_UPS / 3600;
    public static final int CALORIES_PER_SECOND_JUMP_ROPE = CALORIES_PER_HOUR_JUMP_ROPE / 3600;
    public static final int CALORIES_PER_SECOND_SQUATS = CALORIES_PER_HOUR_SQUATS / 3600;

    private Constants(){
    }
}
