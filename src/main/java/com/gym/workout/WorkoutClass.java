package com.gym.workout;

import java.time.LocalDateTime;

public class WorkoutClass {
    private int id;
    private final String className;
    private final int trainerId;
    private final LocalDateTime schedule;

    public WorkoutClass(int id, String className, int trainerId, LocalDateTime schedule) {
        this.id = id;
        this.className = className;
        this.trainerId = trainerId;
        this.schedule = schedule;
    }

    public WorkoutClass(String className, int trainerId, LocalDateTime schedule) {
        this.className = className;
        this.trainerId = trainerId;
        this.schedule = schedule;
    }

    public int getId() { return id; }
    public String getClassName() { return className; }
    public int getTrainerId() { return trainerId; }
    public LocalDateTime getSchedule() { return schedule; }
}
