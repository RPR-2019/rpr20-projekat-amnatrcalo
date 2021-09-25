package ba.unsa.etf.rpr.project.controller;

import ba.unsa.etf.rpr.project.database.AppDAO;
import ba.unsa.etf.rpr.project.enums.content.LoginMessages;
import ba.unsa.etf.rpr.project.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class EditProfileController {

        public TextField fldUsername=new TextField();
        public Label errorUsername;
        public TextField fldFirstName=new TextField();
        public Label errorFirstName;
        public TextField fldLastName=new TextField();
        public Label errorLastName;
        public TextField fldMail=new TextField();
        public Label errorMail;
        public PasswordField fldPassword=new PasswordField();
        public Label errorPassword;
        public PasswordField fldConfirmPassword=new PasswordField();
        public Label errorConfirmPassword;

        private ArrayList<User> users=new ArrayList<>();
        private final String exUsername;
        private final User user;
        private final AppDAO dao;



        public EditProfileController(User user, ArrayList<User>users, AppDAO dao) {
            this.user=user;
            this.users=users;
            this.dao=dao;
            this.exUsername=user.getUsername();
        }

        @FXML
        public void initialize(){
            fldFirstName.setText(user.getFirstName());
            fldLastName.setText(user.getLastName());
            fldUsername.setText(user.getUsername());
            fldMail.setText(user.getMail());
            fldPassword.setText(user.getPassword());
            fldConfirmPassword.setText(user.getPassword());
        }



        public User getUser() {
            return user;
        }


        public boolean isUsernameFree(String username){
            boolean free=true;
            for(User u:users){
                if(u.getUsername().equals(username) && u.getId()!= user.getId()) free=false;
            }
            return free;
        }

        public static boolean emailValidation(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                return false;
            return pat.matcher(email).matches();
        }

        public void btnSaveAction(ActionEvent actionEvent) throws IOException {
            boolean ok=true;

            if(fldFirstName.getText().trim().isEmpty()){
                errorFirstName.setText(LoginMessages.FIRST_NAME_EMPTY.toString());
                ok=false;
            } else{
                errorFirstName.setText(" ");
            }

            if(fldLastName.getText().trim().isEmpty()){
                errorLastName.setText(LoginMessages.LAST_NAME_EMPTY.toString());
                ok=false;
            }else{
                errorLastName.setText(" ");
            }

            if(fldUsername.getText().trim().isEmpty()) {
                errorUsername.setText(LoginMessages.USERNAME_EMPTY.toString());
                ok=false;
            }else if(!fldUsername.getText().trim().isEmpty() && !isUsernameFree(fldUsername.getText())) {
                errorUsername.setText(LoginMessages.USERNAME_ERROR.toString());
                ok=false;
            }
            else{
                errorUsername.setText(" ");
            }

            if(fldMail.getText().trim().isEmpty()) {
                errorMail.setText(LoginMessages.MAIL_EMPTY.toString());
                ok = false;
            }else if(!emailValidation(fldMail.getText())){
                errorMail.setText(LoginMessages.INVALID_MAIL.toString());
                ok=false;
            } else{
                errorMail.setText(" ");
            }


            if(fldPassword.getText().trim().isEmpty()){
                errorPassword.setText(LoginMessages.PASSWORD_EMPTY.toString());
                ok=false;
            } else if(!fldPassword.getText().isEmpty()&& fldPassword.getText().length()<4){
                ok=false;
                errorPassword.setText(LoginMessages.PASSWORD_ENTRY.toString());
            }else{
                errorPassword.setText(" ");
            }

            if(fldConfirmPassword.getText().trim().isEmpty() && !fldPassword.getText().isEmpty() ) {
                errorConfirmPassword.setText(LoginMessages.CONFIRM_PASSWORD_EMPTY.toString());
                ok=false;
            } else if(!fldPassword.getText().isEmpty() && !fldConfirmPassword.getText().isEmpty() && !fldPassword.getText().equals(fldConfirmPassword.getText())){
                errorConfirmPassword.setText(LoginMessages.PASSWORD_MISMATCH.toString());
                ok=false;
            } else if (fldConfirmPassword.getText().trim().isEmpty() && fldPassword.getText().trim().isEmpty()){
                errorConfirmPassword.setText(LoginMessages.CONFIRM_PASSWORD_EMPTY.toString());
                ok=false;
            } else{
                errorConfirmPassword.setText(" ");
            }

            if(!ok) return;


            user.setFirstName(fldFirstName.getText());
            user.setLastName(fldLastName.getText());
            user.setUsername(fldUsername.getText());
            user.setMail(fldMail.getText());
            user.setPassword(fldPassword.getText());

            dao.editUser(user,exUsername);

            Stage stage = (Stage) fldUsername.getScene().getWindow();
            stage.close();


        }

        public void actionCancel(ActionEvent actionEvent){
            Stage stage = (Stage) fldUsername.getScene().getWindow();
            stage.close();
        }





}
