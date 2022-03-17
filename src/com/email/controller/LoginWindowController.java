package com.email.controller;


import com.email.EmailManager;
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
            System.out.println("click !!");
            viewFactory.showMainWindow();
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            viewFactory.closeStage(stage);

        }
}


