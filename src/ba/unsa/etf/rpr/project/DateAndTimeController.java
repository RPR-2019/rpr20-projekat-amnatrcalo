package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class DateAndTimeController {
    public CheckBox checkBoxAllDayTask;
    public DatePicker startDatePicker;
    public Spinner<String> startHour;
    public Spinner <String> startMins;
    public DatePicker endDatePicker;
    public Spinner <String> endHour;
    public Spinner <String> endMins;
    public CheckBox checkBoxReminder;
    public ComboBox <Integer> comboDigitBefore;
    public ChoiceBox<String> choicePeriodBefore;
    public RadioButton radioNotification;
    public RadioButton radioEmail;
    public Button btnSave;

    private AppDAO dao;
    private Task task;
    private boolean selectAllDay =false;
    private boolean selectReminder=false;

    private final int currentHour= LocalDateTime.now().getHour();
    private final int currentMins=LocalDateTime.now().getMinute();

    private ObservableList<String> hours = FXCollections.observableArrayList(
            "00", "01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23");
    private ObservableList<String> mins = FXCollections.observableArrayList(
            "00", "01", "02", "03", "04", "05", "06", "07","08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53","54", "55", "56", "57", "58", "59");

    @FXML
    public void initialize(){
        comboDigitBefore.setDisable(true);
        choicePeriodBefore.setDisable(true);
        radioEmail.setDisable(true);
        radioNotification.setDisable(true);

        SpinnerValueFactory<String> valueFactoryStartHours =new SpinnerValueFactory.ListSpinnerValueFactory<String>(hours);
        startHour.setValueFactory(valueFactoryStartHours);
        valueFactoryStartHours.setValue(String.valueOf(currentHour));

        SpinnerValueFactory<String> valueFactoryEndHours =new SpinnerValueFactory.ListSpinnerValueFactory<String>(hours);
        endHour.setValueFactory(valueFactoryEndHours);
        valueFactoryEndHours.setValue(String.valueOf(currentHour+1));

        SpinnerValueFactory<String> valueFactoryStartMins =new SpinnerValueFactory.ListSpinnerValueFactory<String>(mins);
        startMins.setValueFactory(valueFactoryStartMins);
        valueFactoryStartMins.setValue(String.valueOf(currentMins));

        SpinnerValueFactory<String> valueFactoryEndMins =new SpinnerValueFactory.ListSpinnerValueFactory<String>(mins);
        endMins.setValueFactory(valueFactoryEndMins);
        valueFactoryEndMins.setValue(String.valueOf(currentMins));
    }

    public DateAndTimeController(Task task){
        this.task=task;
    }

    public void actionAllDayTask(ActionEvent actionEvent) {
        selectAllDay =!selectAllDay;
        if(selectAllDay){
            endDatePicker.setDisable(true);
            endHour.setDisable(true);
            endMins.setDisable(true);
        }
        if(!selectAllDay){
            endDatePicker.setDisable(false);
            endHour.setDisable(false);
            endMins.setDisable(false);
        }

    }

    public Task getTask() {
        return task;
    }

    public void actionSave(ActionEvent actionEvent) {
        //voditi racuna o tome je li null za svaki
        if(task==null) task=new Task();
        if(startDatePicker!=null){
            task.setStartYear(startDatePicker.getValue().getYear());
            task.setStartMonth(startDatePicker.getValue().getMonthValue());
            task.setStartDay(startDatePicker.getValue().getDayOfMonth());
        } else{
            task.setStartYear(null);
            task.setStartMonth(null);
            task.setStartDay(null);
        }


        task.setStartHour(Integer.parseInt(startHour.getValue()));
        task.setStartMin(Integer.parseInt(startMins.getValue()));

        if(endDatePicker!=null){
            task.setEndYear(endDatePicker.getValue().getYear());
            task.setEndMonth(endDatePicker.getValue().getMonthValue());
            task.setEndDay(endDatePicker.getValue().getDayOfMonth());
        } else{
            task.setEndYear(null);
            task.setEndMonth(null);
            task.setEndDay(null);
        }

        task.setEndHour(Integer.parseInt(endHour.getValue()));
        task.setEndMin(Integer.parseInt(endMins.getValue()));
        task.setReminder(checkBoxReminder.isSelected());
        task.setReminderDigit(comboDigitBefore.getValue());
        task.setReminderPeriod(choicePeriodBefore.getValue());
        task.setAlertNotification(radioNotification.isSelected());
        task.setAlertEmail(radioEmail.isSelected());

        Stage stage = (Stage) radioEmail.getScene().getWindow();
        stage.close();
    }

    public void actionRemindMe(ActionEvent actionEvent){
        selectReminder=!selectReminder;

        if(selectReminder){
            comboDigitBefore.setDisable(false);
            choicePeriodBefore.setDisable(false);
            radioEmail.setDisable(false);
            radioNotification.setDisable(false);
        }

        if(!selectReminder){
            comboDigitBefore.setDisable(true);
            choicePeriodBefore.setDisable(true);
            radioEmail.setDisable(true);
            radioNotification.setDisable(true);
        }

    }


}
