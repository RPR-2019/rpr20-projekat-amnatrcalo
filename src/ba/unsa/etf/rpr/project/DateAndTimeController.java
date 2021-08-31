package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

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
    private ObservableList<Integer> valuesBefore=FXCollections.observableArrayList(1,2,3,4,5,10,15,20,25,30);

    public String setEditSprinnerHours(Integer value, Integer year){
        if(year==1) return "--";
        else return hours.get(value+1);
    }

    public String setEditSprinnerMins(Integer value, Integer year){
        if(year==1) return "--";
        else return mins.get(value+1);
    }
    @FXML
    public void initialize(){
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

        if(task!=null){
            //edit current task
            checkBoxReminder.setSelected(task.isReminder());
            if(task.isReminder()){
                selectReminder=true;
                comboValueBefore.setDisable(false);
                choicePeriodBefore.setDisable(false);
                radioNotification.setDisable(false);
                radioEmail.setDisable(false);
            } else{
                comboValueBefore.setDisable(true);
                choicePeriodBefore.setDisable(true);
                radioNotification.setDisable(true);
                radioEmail.setDisable(true);
                radioNotification.setSelected(true);
                radioEmail.setSelected(false);
            }
            comboValueBefore.setValue(task.getReminderDigit());
            choicePeriodBefore.setValue(task.getReminderPeriod());
            radioNotification.setSelected(task.isAlertNotification());
            radioEmail.setSelected(task.isAlertEmail());

            valueFactoryStartHours.setValue(setEditSprinnerHours(task.getStartHour(), task.getStartYear()));
            valueFactoryEndHours.setValue(setEditSprinnerHours(task.getEndHour(),task.getEndYear()));
            valueFactoryStartMins.setValue(setEditSprinnerMins(task.getStartMin(),task.getStartYear()));
            valueFactoryEndMins.setValue(setEditSprinnerMins(task.getEndMin(), task.getEndYear()));

            if(task.getStartYear()!=1){
                startDatePicker.setValue(LocalDate.of(task.getStartYear(),task.getStartMonth(),task.getStartDay()));
            }

            if(task.getEndYear()!=1){
                endDatePicker.setValue(LocalDate.of(task.getEndYear(),task.getEndMonth(),task.getEndDay()));
            }


        } else{
            // setting new task
            comboValueBefore.setDisable(true);
            choicePeriodBefore.setDisable(true);
            radioEmail.setDisable(true);
            radioNotification.setDisable(true);
        }


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
            startHour.setDisable(true);
            startMins.setDisable(true);
        }
        if(!selectAllDay){
            endDatePicker.setDisable(false);
            endHour.setDisable(false);
            endMins.setDisable(false);
            startHour.setDisable(false);
            startMins.setDisable(false);
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

    private boolean checkTime(LocalDateTime start, LocalDateTime end){

        boolean ok=true;
        if(start.isAfter(end)) ok=false;
        return ok;
    }

    private boolean isTimeNull(String timeValue){

        boolean ok=false;
        if(timeValue.equals("--")) ok=true;
        return ok;
    }

    public void actionSave(ActionEvent actionEvent) {
        boolean ok=true;
        if(startDatePicker.getValue()!=null){
            if(!checkDate(currentDate,startDatePicker.getValue())){
                ok=false;
                alertClass.alertERROR("Date error",
                        "The start of the task can't be before current date");
            }
        }

        if(selectAllDay){
            if(startDatePicker.getValue()==null){
                alertClass.alertERROR("The start of the task is not set",
                        "The start of the task must be set because this is an all day activity.");
                ok=false;
            }
        } else{
            if(startDatePicker.getValue()==null && endDatePicker.getValue()!=null){
                ok=false;
                alertClass.alertERROR("Start date is not set",
                        "End date is set, but start date is not.");
            }else if((!isTimeNull(startHour.getValue()) || !(isTimeNull(startMins.getValue()))) && startDatePicker.getValue()==null){
                ok=false;
                alertClass.alertERROR("Date error",
                        "Start date is not set proprerly.");
            } else if((!isTimeNull(endHour.getValue()) || !(isTimeNull(endMins.getValue()))) && endDatePicker.getValue()==null){
                ok=false;
                alertClass.alertERROR("Date error",
                        "End date is not set proprerly.");
            } else if((isTimeNull(startHour.getValue()) || (isTimeNull(startMins.getValue()))) && startDatePicker.getValue()!=null){
                ok=false;
                alertClass.alertERROR("Time error",
                        "Start time is not set proprerly.");
            } else if((isTimeNull(endHour.getValue()) || (isTimeNull(endMins.getValue()))) && endDatePicker.getValue()!=null){
                ok=false;
                alertClass.alertERROR("Time error",
                        "End time is not set proprerly.");
            } else if(startDatePicker.getValue()!=null && !checkTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),LocalDateTime.of(startDatePicker.getValue(),LocalTime.of(Integer.parseInt(startHour.getValue()), Integer.parseInt(startMins.getValue()))) )){
                ok=false;
                alertClass.alertERROR("Time error",
                        "The start of the task can't be before current date");
            }
            else if (startDatePicker.getValue()!=null && endDatePicker.getValue()!=null){
               LocalDate startDate=LocalDate.of(startDatePicker.getValue().getYear(),startDatePicker.getValue().getMonthValue(),startDatePicker.getValue().getDayOfMonth());
               LocalTime startTime=LocalTime.of(Integer.parseInt(startHour.getValue()), Integer.parseInt(startMins.getValue()));

                LocalDate endDate=LocalDate.of(endDatePicker.getValue().getYear(),endDatePicker.getValue().getMonthValue(),endDatePicker.getValue().getDayOfMonth());
                LocalTime endTime=LocalTime.of(Integer.parseInt(endHour.getValue()), Integer.parseInt(endMins.getValue()));

                if(!checkTime(LocalDateTime.of(startDate,startTime), LocalDateTime.of(endDate,endTime))){
                    ok=false;
                    alertClass.alertERROR("Date error",
                            "The end of the task can't be before its start date");
                } else if(isTimeNull(startHour.getValue()) || isTimeNull(startMins.getValue()) || isTimeNull(endHour.getValue()) || isTimeNull(endMins.getValue())){
                    ok=false;
                    alertClass.alertERROR("Time error",
                            "Start time or end time are not set proprerly.");
                }
            } else if(selectReminder && (isTimeNull(startMins.getValue()) || isTimeNull(startHour.getValue()) || startDatePicker.getValue()==null)){
                ok=false;
                alertClass.alertERROR("Reminder can't be set",
                        "You didn't specify start time of Your task.");
            }
        }

        if(!ok) return;

        if(task==null) task=new Task();

        if(startDatePicker.getValue()!=null){
            task.setStartYear(startDatePicker.getValue().getYear());
            task.setStartMonth(startDatePicker.getValue().getMonthValue());
            task.setStartDay(startDatePicker.getValue().getDayOfMonth());

        } else{
            task.setStartYear(1);
            task.setStartMonth(1);
            task.setStartDay(1);
        }

        if(startHour.getValue().equals("--") && startMins.getValue().equals("--")){
            task.setStartHour(1);
            task.setStartMin(1);
        } else{
            task.setStartHour(Integer.parseInt(startHour.getValue()));
            task.setStartMin(Integer.parseInt(startMins.getValue()));
        }


        if(endDatePicker.getValue()!=null){
            task.setEndYear(endDatePicker.getValue().getYear());
            task.setEndMonth(endDatePicker.getValue().getMonthValue());
            task.setEndDay(endDatePicker.getValue().getDayOfMonth());
        } else{
            task.setEndYear(1);
            task.setEndMonth(1);
            task.setEndDay(1);
        }

        if(endHour.getValue().equals("--") && endMins.getValue().equals("--")){
            task.setEndHour(1);
            task.setEndMin(1);
        } else{
            task.setEndHour(Integer.parseInt(endHour.getValue()));
            task.setEndMin(Integer.parseInt(endMins.getValue()));
        }

        task.setReminder(checkBoxReminder.isSelected());

        if(!checkBoxReminder.isSelected()){
            task.setReminderDigit(-1);
            task.setReminderPeriod("--");
        }


        task.setReminderDigit(comboValueBefore.getValue());

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

    public void actionCancel(ActionEvent actionEvent){
        Stage stage = (Stage) radioEmail.getScene().getWindow();
        stage.close();
    }


}
