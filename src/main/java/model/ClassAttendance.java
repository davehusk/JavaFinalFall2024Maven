package model;

import java.time.LocalDate;

public class ClassAttendance {
    private int id;
    private int userId;
    private int classId;
    private LocalDate attendedAt;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public LocalDate getAttendedAt() { return attendedAt; }
    public void setAttendedAt(LocalDate attendedAt) { this.attendedAt = attendedAt; }
}
