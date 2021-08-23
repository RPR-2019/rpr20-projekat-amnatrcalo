package ba.unsa.etf.rpr.project;

import java.time.LocalDate;

public class Task {
    private Integer id;
    private String username;
    private String taskName;
    private Integer startYear, startMonth, startDay, startHour, startMin;
    private Integer endYear, endMonth, endDay, endHour, endMin;
    private String note;
    private boolean reminder;
    private Integer reminderDigit;
    private String reminderPeriod;
    private boolean alertNotification;
    private boolean alertEmail;
    private String listName;

    public Task(Integer id, String username, String taskName, Integer startYear, Integer startMonth, Integer startDay, Integer startHour, Integer startMin, Integer endYear, Integer endMonth, Integer endDay, Integer endHour, Integer endMin, String note, boolean reminder, Integer reminderDigit, String reminderPeriod, boolean alertNotification, boolean alertEmail, String listName) {
        this.id = id;
        this.username = username;
        this.taskName = taskName;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endDay = endDay;
        this.endHour = endHour;
        this.endMin = endMin;
        this.note = note;
        this.reminder = reminder;
        this.reminderDigit = reminderDigit;
        this.reminderPeriod = reminderPeriod;
        this.alertNotification = alertNotification;
        this.alertEmail = alertEmail;
        this.listName=listName;
    }

    public Task() {
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

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMin() {
        return startMin;
    }

    public void setStartMin(Integer startMin) {
        this.startMin = startMin;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMin() {
        return endMin;
    }

    public void setEndMin(Integer endMin) {
        this.endMin = endMin;
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

    public Integer getReminderDigit() {
        return reminderDigit;
    }

    public void setReminderDigit(Integer reminderDigit) {
        this.reminderDigit = reminderDigit;
    }

    public String getReminderPeriod() {
        return reminderPeriod;
    }

    public void setReminderPeriod(String reminderPeriod) {
        this.reminderPeriod = reminderPeriod;
    }

    public boolean isAlertNotification() {
        return alertNotification;
    }

    public void setAlertNotification(boolean alertNotification) {
        this.alertNotification = alertNotification;
    }

    public boolean isAlertEmail() {
        return alertEmail;
    }

    public void setAlertEmail(boolean alertEmail) {
        this.alertEmail = alertEmail;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}



