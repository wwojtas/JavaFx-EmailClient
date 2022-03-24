package com.email;

import com.email.controller.persistance.PersistanceAccess;
import com.email.controller.persistance.ValidAccount;
import com.email.controller.services.LoginService;
import com.email.model.EmailAccount;
import com.email.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private PersistanceAccess persistanceAccess = new PersistanceAccess();
    private EmailManager emailManager = new EmailManager();


    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory(emailManager);
//        viewFactory.showLoginWindow();
//        viewFactory.updateStyles();
        List<ValidAccount> validAccountList = persistanceAccess.loadFromPersistance();
        if(validAccountList.size() > 0){
            viewFactory.showMainWindow();
            for(ValidAccount validAccount : validAccountList){
                EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                loginService.start();
            }
        } else {
            viewFactory.showLoginWindow();
        }

    }


    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for(EmailAccount emailAccount: emailManager.getEmailAccounts()){
            validAccountList.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword()));
        }
        persistanceAccess.saveToPersistance(validAccountList);
    }
}
