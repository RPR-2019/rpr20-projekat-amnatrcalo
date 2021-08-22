package ba.unsa.etf.rpr.project;

import java.sql.Time;
import java.sql.Date;

public class Task {
    private Integer id;
    private String username;
    private String taskName;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private String note;
    private boolean reminder;
    private boolean freeTask;

    public Task(Integer id, String username, String taskName, Date startDate, Time startTime, Date endDate, Time endTime, String note, boolean reminder, boolean freeTask) {
        this.id = id;
        this.username = username;
        this.taskName = taskName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.note = note;
        this.reminder = reminder;
        this.freeTask = freeTask;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public boolean isFreeTask() {
        return freeTask;
    }

    public void setFreeTask(boolean freeTask) {
        this.freeTask = freeTask;
    }
}
