package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

public class DateAndTimeController {
    public CheckBox checkBoxAllDayTask;
    public DatePicker startDatePicker;
    public Spinner<String> startHour;
    public Spinner <String> startMins;
    public DatePicker endDatePicker;
    public Spinner <String> endHour;
    public Spinner <String> endMins;
    public CheckBox checkBoxReminder;
    public ComboBox <Integer> comboValueBefore;
    public ChoiceBox<String> choicePeriodBefore;
    public RadioButton radioNotification;
    public RadioButton radioEmail;
    public Label startErrorDateMessage;
    public Button btnSave;

    private AppDAO dao;
    private Task task;
    private AlertClass alertClass=new AlertClass();
    private boolean selectAllDay =false;
    private boolean selectReminder=false;

    private final int currentHour= LocalDateTime.now().getHour();
    private final int currentMins=LocalDateTime.now().getMinute();
    private final LocalDate currentDate=LocalDate.now();

    private Integer exampleClass= 10;

    private ObservableList<String> hours = FXCollections.observableArrayList(
            "--","00", "01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23");
    private ObservableList<String> mins = FXCollections.observableArrayList(
            "--","00", "01", "02", "03", "04", "05", "06", "07","08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53","54", "55", "56", "57", "58", "59");

    private ObservableList<String> periods=FXCollections.observableArrayList("minutes", "hours", "days");
    private ObservableList<Integer> valuesBefore=FXCollections.observableArrayList(1,2,3,4,5,10,15,30);
    @FXML
    public void initialize(){
        comboValueBefore.setDisable(true);
        choicePeriodBefore.setDisable(true);
        radioEmail.setDisable(true);
        radioNotification.setDisable(true);

        SpinnerValueFactory<String> valueFactoryStartHours =new SpinnerValueFactory.ListSpinnerValueFactory<String>(hours);
        startHour.setValueFactory(valueFactoryStartHours);
        valueFactoryStartHours.setValue("--");

        SpinnerValueFactory<String> valueFactoryEndHours =new SpinnerValueFactory.ListSpinnerValueFactory<String>(hours);
        endHour.setValueFactory(valueFactoryEndHours);
        valueFactoryEndHours.setValue("--");

        SpinnerValueFactory<String> valueFactoryStartMins =new SpinnerValueFactory.ListSpinnerValueFactory<String>(mins);
        startMins.setValueFactory(valueFactoryStartMins);
        valueFactoryStartMins.setValue("--");

        SpinnerValueFactory<String> valueFactoryEndMins =new SpinnerValueFactory.ListSpinnerValueFactory<String>(mins);
        endMins.setValueFactory(valueFactoryEndMins);
        valueFactoryEndMins.setValue("--");

        choicePeriodBefore.setItems(periods);
        choicePeriodBefore.getSelectionModel().selectFirst();
        comboValueBefore.setItems(valuesBefore);
        comboValueBefore.getSelectionModel().select(6);
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



    private boolean checkDate(LocalDate start, LocalDate end){
        boolean ok=true;
        if(start.isAfter(end)) ok=false;
        return ok;
    }

    private boolean checkTime(Integer startHour, Integer startMins, Integer endHour, Integer endMins){
        boolean ok=true;
        if(endHour<startHour) ok=false;
        else if(endHour==startHour && endMins<startMins) ok=false;
        return ok;
    }

    private boolean checkIsTimeNull(String timeValue){
        boolean ok=false;
        if(timeValue.equals("--")) ok=true;
        return ok;
    }

    public void actionSave(ActionEvent actionEvent) {
        boolean ok=true;

        if(task==null) task=new Task();
        if(selectAllDay && startDatePicker.getValue()==null){
            alertClass.alertERROR("The start of the task is not set",
                    "The start of the task must be set because this is an all day activity.");
           ok=false;
        }
        else if(startDatePicker.getValue()==null && endDatePicker.getValue()!=null){
            ok=false;
            alertClass.alertERROR("Start date is not set",
                    "End date is set, but start date is not.");
        } else if((!checkIsTimeNull(startHour.getValue()) || !(checkIsTimeNull(startMins.getValue()))) && startDatePicker.getValue()==null){
            ok=false;
            alertClass.alertERROR("Time error",
                    "Start time is not set proprerly.");
        } else if((!checkIsTimeNull(endHour.getValue()) || !(checkIsTimeNull(endMins.getValue()))) && endDatePicker.getValue()==null){
            ok=false;
            alertClass.alertERROR("Time error",
                    "End time is not set proprerly.");
        }else if (startDatePicker.getValue()!=null && endDatePicker.getValue()!=null){
            if(!checkDate(currentDate,startDatePicker.getValue())){
                ok=false;
                alertClass.alertERROR("Date error",
                        "The start of the task can't be before current date");
            } else if(!checkDate(startDatePicker.getValue(),endDatePicker.getValue())){
                ok=false;
                alertClass.alertERROR("Date error",
                        "The end of the task can't be before its start date");
            }else if(startHour.getValue().equals("--") || endHour.getValue().equals("--") || startMins.getValue().equals("--") ||endMins.getValue().equals("--")){
                ok=false;
                alertClass.alertERROR("Time error",
                        "Start time or end time are not set proprerly.");
            }
            else if(!checkIsTimeNull(endHour.getValue()) && !checkIsTimeNull(endMins.getValue()) && !checkTime(Integer.parseInt(startHour.getValue()),Integer.parseInt(startMins.getValue()),Integer.parseInt(endHour.getValue()), Integer.parseInt(endMins.getValue())) && endDatePicker.getValue().isEqual(startDatePicker.getValue())){
                ok=false;
                alertClass.alertERROR("The end time of the task can't be before its start time",
                        "Your task starts at "+startHour.getValue()+":"+startMins.getValue());
            }
            else if((!checkIsTimeNull(startHour.getValue()) || !(checkIsTimeNull(startMins.getValue()))) && startDatePicker.getValue()==null){
                ok=false;
                alertClass.alertERROR("Time error",
                        "Start time is not set proprerly.");
            } else if((!checkIsTimeNull(endHour.getValue()) || !(checkIsTimeNull(endMins.getValue()))) && endDatePicker.getValue()==null){
                ok=false;
                alertClass.alertERROR("Time error",
                        "End time is not set proprerly.");
            }
            else if(!checkTime(currentHour,currentMins,Integer.parseInt(startHour.getValue()), Integer.parseInt(startMins.getValue())) && currentDate.isEqual(startDatePicker.getValue())){
               ok=false;
                alertClass.alertERROR("The start of the task can't be before current time",
                        "Your task starts at "+startHour.getValue()+":"+startMins.getValue());
            }
        } else if(startDatePicker.getValue()!=null && endDatePicker.getValue()==null){
            if(!checkDate(currentDate,startDatePicker.getValue())){
                ok=false;
                alertClass.alertERROR("Date error",
                        "The start of the task can't be before current date");
            }  else if(startHour.getValue().equals("--")  || startMins.getValue().equals("--") ){
                ok=false;
                alertClass.alertERROR("Time error",
                        "Start time is not set proprerly.");
            } else if(!checkTime(currentHour,currentMins,Integer.parseInt(startHour.getValue()), Integer.parseInt(startMins.getValue())) && currentDate.isEqual(startDatePicker.getValue())){
                ok=false;
                alertClass.alertERROR("The start of the task can't be before current time",
                        "Your task starts at "+startHour.getValue()+":"+startMins.getValue());
            }
        } else if(selectReminder && (startHour.getValue().equals("--")  || startMins.getValue().equals("--") || startDatePicker.getValue()==null)){
            ok=false;
            alertClass.alertERROR("Reminder can't be set",
                    "You didn't specify start time of Your task.");

        }

        if(!ok) return;

        if(startDatePicker.getValue()!=null){
            task.setStartYear(startDatePicker.getValue().getYear());
            task.setStartMonth(startDatePicker.getValue().getMonthValue());
            task.setStartDay(startDatePicker.getValue().getDayOfMonth());

        } else{
            task.setStartYear(null);
            task.setStartMonth(null);
            task.setStartDay(null);
        }

        if(startHour.getValue().equals("--") && startMins.getValue().equals("--")){
            task.setStartHour(null);
            task.setStartMin(null);
        } else{
            task.setStartHour(Integer.parseInt(startHour.getValue()));
            task.setStartMin(Integer.parseInt(startMins.getValue()));
        }


        if(endDatePicker.getValue()!=null){
            task.setEndYear(endDatePicker.getValue().getYear());
            task.setEndMonth(endDatePicker.getValue().getMonthValue());
            task.setEndDay(endDatePicker.getValue().getDayOfMonth());
        } else{
            task.setEndYear(null);
            task.setEndMonth(null);
            task.setEndDay(null);
        }

        if(endHour.getValue().equals("--") && endMins.getValue().equals("--")){
            task.setEndHour(null);
            task.setEndMin(null);
        } else{
            task.setEndHour(Integer.parseInt(endHour.getValue()));
            task.setEndMin(Integer.parseInt(endMins.getValue()));
        }

        task.setReminder(checkBoxReminder.isSelected());

        try{
            task.setReminderDigit(comboValueBefore.getValue());
        }catch(Exception e){
            ok=false;
            alertClass.alertERROR("Reminder can't be set",
                    "Entered value is not a number");
        }

        task.setReminderPeriod(choicePeriodBefore.getValue());
        task.setAlertNotification(radioNotification.isSelected());
        task.setAlertEmail(radioEmail.isSelected());

        Stage stage = (Stage) radioEmail.getScene().getWindow();
        stage.close();
    }

    public void actionRemindMe(ActionEvent actionEvent){
        selectReminder=!selectReminder;

        if(selectReminder){
            comboValueBefore.setDisable(false);
            choicePeriodBefore.setDisable(false);
            radioEmail.setDisable(false);
            radioNotification.setDisable(false);
        }

        if(!selectReminder){
            comboValueBefore.setDisable(true);
            choicePeriodBefore.setDisable(true);
            radioEmail.setDisable(true);
            radioNotification.setDisable(true);
        }

    }


}
