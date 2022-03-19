package com.email.view;

import com.email.EmailManager;
import com.email.controller.BaseController;
import com.email.controller.LoginWindowController;
import com.email.controller.MainWindowController;
import com.email.controller.OptionsWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private EmailManager emailManager;
    private ArrayList<Stage> avtiveStages;
    private boolean mainViewInitialized = false;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        avtiveStages = new ArrayList<Stage>();
    }

    public boolean isMainViewInitialized(){
        return mainViewInitialized;
    }

    // View options handling
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public void showLoginWindow(){
        System.out.println("show login window called");

        BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml");
        intializeStage(controller);
    }

    public void showMainWindow(){
        System.out.println("main window called");

        BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
        intializeStage(controller);
        mainViewInitialized = true;
    }

    public void showOptionsWindow(){
        System.out.println("options widnow called");
        BaseController controller = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");
        intializeStage(controller);
    }

    private void intializeStage(BaseController baseController){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        avtiveStages.add(stage);
    }

    public void closeStage(Stage stageToClose){
        stageToClose.close();
        avtiveStages.remove(stageToClose);
    }

    public void updateStyles() {
        for(Stage stage: avtiveStages) {
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }
    }
}
