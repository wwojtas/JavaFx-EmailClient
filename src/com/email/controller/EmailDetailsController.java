package com.email.controller;

import com.email.EmailManager;
import com.email.controller.services.MessageRendererService;
import com.email.model.EmailMessage;
import com.email.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends BaseController implements Initializable {

    private String LOCATION_OF_DOWNLOADS = System.getProperty("D:\\Nowy folder\\attachment"); // use your folder to attachments

    @FXML
    private HBox hBoxDownloads;

    @FXML
    private Label attachmentLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private WebView webView;

    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EmailMessage emailMessage = emailManager.getSelectedMessage();
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());
        loadAttachments(emailMessage);

        MessageRendererService messageRendererService = new MessageRendererService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();

    }

    private void loadAttachments(EmailMessage emailMessage){
        if(emailMessage.hasAttachments()){
            for(MimeBodyPart mimeBodyPart: emailMessage.getAttachmentList()){
                try {
                    Button button = new Button(mimeBodyPart.getFileName());
                    hBoxDownloads.getChildren().add(button);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        } else {
            attachmentLabel.setText("");
        }
    }
}
