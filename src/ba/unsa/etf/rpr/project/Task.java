package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.MyDayMessages;
import ba.unsa.etf.rpr.project.enums.Period;
import ba.unsa.etf.rpr.project.enums.TaskMessages;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Task {
    private Integer id;
    private String username;
    private SimpleStringProperty taskName;
    private Integer startYear, startMonth, startDay, startHour, startMin;
    private Integer endYear, endMonth, endDay, endHour, endMin;
    private String note;
    private boolean reminder;
    private Integer reminderDigit;
    private String reminderPeriod;
    private boolean alertNotification;
    private boolean alertEmail;
    private String listName;
    private boolean allDay;

    private final DateTimeFormatter formatDate=DateTimeFormatter.ofPattern(MyDayMessages.DATE.toString());
    private final DateTimeFormatter formatTime=DateTimeFormatter.ofPattern(MyDayMessages.CLOCK.toString());


    public Task(Integer id, String username, String taskName, Integer startYear, Integer startMonth, Integer startDay, Integer startHour, Integer startMin, Integer endYear, Integer endMonth, Integer endDay, Integer endHour, Integer endMin, String note, boolean reminder, Integer reminderDigit, String reminderPeriod, boolean alertNotification, boolean alertEmail, String listName, boolean allday) {
        this.id = id;
        this.username = username;
        this.taskName = new SimpleStringProperty(taskName);
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
        this.allDay=allday;
    }

    public Task() {
        taskName=new SimpleStringProperty();
    }

    public LocalDateTime getStartDateAndTime(){
        return LocalDateTime.of(LocalDate.of(startYear,startMonth,startDay), LocalTime.of(startHour,startMin));

    }

    public LocalDateTime getEndDateAndTime(){
        return LocalDateTime.of(LocalDate.of(endYear,endMonth,endDay), LocalTime.of(endHour,endMin));
    }


    public LocalDateTime getReminderDateAndTime(){
        if(reminderPeriod.equals(Period.MINS.toString())){
            return getStartDateAndTime().minusMinutes(reminderDigit);
        } else if(reminderPeriod.equals(Period.HOURS.toString())){
            return getStartDateAndTime().minusHours(reminderDigit);
        } else return getStartDateAndTime().minusDays(reminderDigit);
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
        return taskName.get();
    }

    public void setTaskName(String taskName) {
         this.taskName.set(taskName);

    }

    public StringProperty taskNameProperty() {
        return taskName;
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
        if(reminderPeriod.equals("hours") || reminderPeriod.equals("sati")){
            return Period.HOURS.toString();
        }
        else if(reminderPeriod.equals("minutes") || reminderPeriod.equals("minuta")) return Period.MINS.toString();
        else return Period.DAYS.toString();
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

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(taskName.get().toUpperCase(Locale.ROOT)).append("\n");
        if(getStartYear()!=1){
            if(!allDay)
                sb.append(getStartDateAndTime().format(formatDate)).append(" ").append(getStartDateAndTime().format(formatTime));
            else
                sb.append(getStartDateAndTime().format(formatDate));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }

        if (!(o instanceof Task)) {
            return false;
        }

        Task t = (Task) o;

        return t.getTaskName().equals(((Task) o).getTaskName()) && t.getUsername().equals(((Task) o).getUsername());
    }

    public String getAllDetails(){

        StringBuilder sb=new StringBuilder();
        sb.append(getTaskName().toUpperCase(Locale.ROOT)).append(" (").append(getListName()).append(")\n");

        if(getStartYear()!=1){
            sb.append(TaskMessages.START_DATE.toString()).append(getStartDateAndTime().format(formatDate)).append("\n");
            if(isAllDay()){
               sb.append(TaskMessages.ALL_DAY.toString()).append("\n");
            } else{
                sb.append(TaskMessages.START_TIME.toString()).append(getStartDateAndTime().format(formatTime)).append("\n");
            }
        }
        if(getEndYear()!=1){
            sb.append(TaskMessages.END_DATE.toString()).append(getEndDateAndTime().format(formatDate)).append("\n");
            sb.append(TaskMessages.END_TIME.toString()).append(getEndDateAndTime().format(formatTime)).append("\n");
        }

        if(isReminder()){
            sb.append(TaskMessages.REMINDER_SET.toString()).append(getReminderDigit()).append(" ").append(getReminderPeriod()).append(TaskMessages.REMINDER_BEFORE.toString());
            if(isAlertEmail()) sb.append(" (").append(TaskMessages.EMAIL_ALERT.toString()).append(")\n");
            else sb.append(" (").append(TaskMessages.NOTIFICATION_ALERT.toString()).append(")\n");
        }

        if(!getNote().trim().isEmpty()){
            sb.append(getNote()).append("\n");
        }

        return sb.toString();

    }

}



