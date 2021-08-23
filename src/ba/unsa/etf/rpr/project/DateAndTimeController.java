package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateAndTimeController {
    public CheckBox checkBoxAllDayTask;
    public DatePicker startDatePicker;
    public Spinner<Integer> startHour;
    public Spinner <Integer> startMins;
    public DatePicker endDatePicker;
    public Spinner <Integer> endHour;
    public Spinner <Integer> endMins;
    public CheckBox checkBoxReminder;
    public ComboBox <Integer> comboDigitBefore;
    public ChoiceBox<String> choicePeriodBefore;
    public RadioButton radioNotification;
    public RadioButton radioEmail;
    public Button btnSave;
    private AppDAO dao;
    private Task task;
    private boolean selectedAllDay=false;


    @FXML
    public void initialize(){


    }

    public DateAndTimeController(Task task){
        this.task=task;
    }

    public void actionAllDayTask(ActionEvent actionEvent) {
        selectedAllDay=!selectedAllDay;
        if(selectedAllDay){
            endDatePicker.setDisable(true);
            endHour.setDisable(true);
            endMins.setDisable(true);
        }
        if(!selectedAllDay){
            endDatePicker.setDisable(false);
            endHour.setDisable(false);
            endMins.setDisable(false);
        }

    }

    public Task getTask() {
        return task;
    }

    public void actionSave(ActionEvent actionEvent) {
        if(task==null) task=new Task();
        task.setStartYear(startDatePicker.getValue().getYear());
        task.setStartMonth(startDatePicker.getValue().getMonthValue());
        task.setStartDay(startDatePicker.getValue().getDayOfMonth());
        task.setStartHour(startHour.getValue());
        task.setStartMin(startMins.getValue());
        task.setEndYear(endDatePicker.getValue().getYear());
        task.setEndMonth(endDatePicker.getValue().getMonthValue());
        task.setEndDay(endDatePicker.getValue().getDayOfMonth());
        task.setEndHour(endHour.getValue());
        task.setEndMin(endMins.getValue());
        task.setReminder(checkBoxReminder.isSelected());
        task.setReminderDigit(comboDigitBefore.getValue());
        task.setReminderPeriod(choicePeriodBefore.getValue());
        task.setAlertNotification(radioNotification.isSelected());
        task.setAlertEmail(radioEmail.isSelected());

        Stage stage = (Stage) radioEmail.getScene().getWindow();
        stage.close();
    }


}
