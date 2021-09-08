package ba.unsa.etf.rpr.project;

import ba.unsa.etf.rpr.project.enums.DateAndTimeAlertMessages;
import ba.unsa.etf.rpr.project.enums.Period;
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


    private Task task;

    private boolean selectAllDay =false;
    private boolean selectReminder=false;


    private final LocalDate currentDate=LocalDate.now();



    private final ObservableList<String> hours = FXCollections.observableArrayList(
            "--","00", "01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23");
    private final ObservableList<String> mins = FXCollections.observableArrayList(
            "--","00", "01", "02", "03", "04", "05", "06", "07","08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53","54", "55", "56", "57", "58", "59");

    private final ObservableList<String> periods=FXCollections.observableArrayList(Period.MINS.toString(), Period.HOURS.toString(), Period.DAYS.toString());
    private final ObservableList<Integer> valuesBefore=FXCollections.observableArrayList(1,2,3,4,5,10,15,20,25,30);

    private String setSprinnerHours(Integer value, Integer year){
        if(year==1) return "--";
        else return hours.get(value+1);
    }

    private String setSprinnerMins(Integer value, Integer year){
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

        choicePeriodBefore.setItems(periods);
        comboValueBefore.setItems(valuesBefore);
        choicePeriodBefore.getSelectionModel().selectFirst();
        comboValueBefore.getSelectionModel().select(6);

        if(task!=null){
            //edit current task
            checkBoxAllDayTask.setSelected(task.isAllDay());
            setAllDay(task.isAllDay());
            if(task.isAllDay()){
                selectAllDay=true;
                setAllDay(true);
            } else{
                setAllDay(false);

            }

            checkBoxReminder.setSelected(task.isReminder());
            if(task.isReminder()){
                selectReminder=true;
                remDisabled(false);
                comboValueBefore.getSelectionModel().select(task.getReminderDigit());
                choicePeriodBefore.getSelectionModel().select(task.getReminderPeriod());
                radioNotification.setSelected(task.isAlertNotification());
                radioEmail.setSelected(task.isAlertEmail());

            } else{
                remDisabled(true);
                radioNotification.setSelected(true);
                radioEmail.setSelected(false);
            }



            valueFactoryStartHours.setValue(setSprinnerHours(task.getStartHour(), task.getStartYear()));
            valueFactoryEndHours.setValue(setSprinnerHours(task.getEndHour(),task.getEndYear()));
            valueFactoryStartMins.setValue(setSprinnerMins(task.getStartMin(),task.getStartYear()));
            valueFactoryEndMins.setValue(setSprinnerMins(task.getEndMin(), task.getEndYear()));

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


    }

    public DateAndTimeController(Task task){
        this.task=task;
    }

    public void actionAllDayTask(ActionEvent actionEvent) {
        selectAllDay =!selectAllDay;
        if(selectAllDay){
            setAllDay(true);
        }
        if(!selectAllDay){
            setAllDay(false);
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
                AlertClass.alertERROR(DateAndTimeAlertMessages.INVALID_START_DATE.toString(),
                        " ","/img/road-sign-icon.png");
            }
        }

        if(selectAllDay){
            if(startDatePicker.getValue()==null){
                AlertClass.alertERROR(DateAndTimeAlertMessages.ALL_DAY_HEADER.toString(),
                        DateAndTimeAlertMessages.ALL_DAY_CONTENT.toString(),"/img/road-sign-icon.png");
                ok=false;
            }
        } else{
            if(startDatePicker.getValue()==null && endDatePicker.getValue()!=null){
                ok=false;
                AlertClass.alertERROR(DateAndTimeAlertMessages.START_DATE_NOT_SET_HEADER.toString(),
                        DateAndTimeAlertMessages.START_DATE_NOT_SET_CONTENT.toString(),"/img/road-sign-icon.png");
            }else if((!isTimeNull(startHour.getValue()) || !(isTimeNull(startMins.getValue()))) && startDatePicker.getValue()==null){
                ok=false;
                AlertClass.alertERROR(DateAndTimeAlertMessages.START_DATE_ERROR.toString(),
                        " ","/img/road-sign-icon.png");
            } else if((!isTimeNull(endHour.getValue()) || !(isTimeNull(endMins.getValue()))) && endDatePicker.getValue()==null){
                ok=false;
                AlertClass.alertERROR(DateAndTimeAlertMessages.END_DATE_ERROR.toString(),
                        " ","/img/road-sign-icon.png");
            } else if((isTimeNull(startHour.getValue()) || (isTimeNull(startMins.getValue()))) && startDatePicker.getValue()!=null){
                ok=false;
                AlertClass.alertERROR(DateAndTimeAlertMessages.START_TIME_ERROR.toString(),
                        " ","/img/road-sign-icon.png");
            } else if((isTimeNull(endHour.getValue()) || (isTimeNull(endMins.getValue()))) && endDatePicker.getValue()!=null){
                ok=false;
                AlertClass.alertERROR(DateAndTimeAlertMessages.END_TIME_ERROR.toString(),
                        " ","/img/road-sign-icon.png");
            } else if(startDatePicker.getValue()!=null && !checkTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),LocalDateTime.of(startDatePicker.getValue(),LocalTime.of(Integer.parseInt(startHour.getValue()), Integer.parseInt(startMins.getValue()))) )){
                ok=false;
                AlertClass.alertERROR(DateAndTimeAlertMessages.INVALID_START_TIME.toString(),
                        " ","/img/road-sign-icon.png");
            }
            else if (startDatePicker.getValue()!=null && endDatePicker.getValue()!=null){
               LocalDate startDate=LocalDate.of(startDatePicker.getValue().getYear(),startDatePicker.getValue().getMonthValue(),startDatePicker.getValue().getDayOfMonth());
               LocalTime startTime=LocalTime.of(Integer.parseInt(startHour.getValue()), Integer.parseInt(startMins.getValue()));

                LocalDate endDate=LocalDate.of(endDatePicker.getValue().getYear(),endDatePicker.getValue().getMonthValue(),endDatePicker.getValue().getDayOfMonth());
                LocalTime endTime=LocalTime.of(Integer.parseInt(endHour.getValue()), Integer.parseInt(endMins.getValue()));

                if(!checkTime(LocalDateTime.of(startDate,startTime), LocalDateTime.of(endDate,endTime))){
                    ok=false;
                    AlertClass.alertERROR(DateAndTimeAlertMessages.INVALID_END_DATE.toString(),
                            " ","/img/road-sign-icon.png");
                } else if(isTimeNull(startHour.getValue()) || isTimeNull(startMins.getValue()) || isTimeNull(endHour.getValue()) || isTimeNull(endMins.getValue())){
                    ok=false;
                    AlertClass.alertERROR(DateAndTimeAlertMessages.TIME_ERROR.toString(),
                            " ","/img/road-sign-icon.png");
                }
            } else if(selectReminder && (isTimeNull(startMins.getValue()) || isTimeNull(startHour.getValue()) || startDatePicker.getValue()==null)){
                ok=false;
                AlertClass.alertERROR(DateAndTimeAlertMessages.REMINDER_HEADER.toString(),
                        DateAndTimeAlertMessages.REMINDER_CONTENT.toString(),"/img/road-sign-icon.png");
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
        task.setAllDay(checkBoxAllDayTask.isSelected());

        Stage stage = (Stage) radioEmail.getScene().getWindow();
        stage.close();
    }

    private void remDisabled(boolean disable){
        comboValueBefore.setDisable(disable);
        choicePeriodBefore.setDisable(disable);
        radioEmail.setDisable(disable);
        radioNotification.setDisable(disable);
    }

    private void setAllDay(boolean disable){
        checkBoxReminder.setDisable(disable);
        remDisabled(disable);
        startMins.setDisable(disable);
        startHour.setDisable(disable);
        endDatePicker.setDisable(disable);
        endMins.setDisable(disable);
        endHour.setDisable(disable);
    }

    public void actionRemindMe(ActionEvent actionEvent){
        selectReminder=!selectReminder;

        if(selectReminder){
            remDisabled(false);
        }

        if(!selectReminder){
            remDisabled(true);
        }

    }

    public void actionCancel(ActionEvent actionEvent){
        Stage stage = (Stage) radioEmail.getScene().getWindow();
        stage.close();
    }


}
