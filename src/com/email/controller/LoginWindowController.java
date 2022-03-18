package com.email.controller;


import com.email.EmailManager;
import com.email.controller.services.LoginService;
import com.email.model.EmailAccount;
import com.email.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

        @FXML
        private Button errorLabel;

        @FXML
        private TextField emailAddressField;

        @FXML
        private PasswordField passwordField;

        public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
                super(emailManager, viewFactory, fxmlName);
        }

        @FXML
        void loginButtonAction() {
            if(fieldsAreValid()){
                EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                EmailLoginResult emailLoginResult = LoginService.login();
                switch (emailLoginResult){
                    case SUCCESS:
                        System.out.println("Login success: " + emailAccount);
                        return;
                }
            }
            System.out.println("click !!");
            viewFactory.showMainWindow();
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            viewFactory.closeStage(stage);

        }

    private boolean fieldsAreValid() {
            if(emailAddressField.getText().isEmpty()){
                errorLabel.setText("Please fill email");
                return false;
            }
        if(passwordField.getText().isEmpty()){
            errorLabel.setText("Please fill password");
            return false;
        }
        return true;
    }
}


