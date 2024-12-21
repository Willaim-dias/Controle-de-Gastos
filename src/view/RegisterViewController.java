package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.User;
import model.services.UserServices;
import view.util.Alerts;
import view.util.Utils;

public class RegisterViewController implements Initializable {

    private final UserServices services = new UserServices();
    
    @FXML
    private AnchorPane anchorRegister;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSave;

    @FXML
    private Label labelMenssage;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPasswordConf;

    @FXML
    private TextField txtUser;


    @FXML
    public void onBtnExiet(ActionEvent event) {
       Utils.currentStage(event).close();
    }
    
    @FXML
    public void onBtnRegisterUser() {
        if (txtUser.getText().equals("")||txtPassword.getText().equals("")||txtPasswordConf.getText().equals("")) {
          labelMenssage.setText("Preencha ambos os campos");
          labelMenssage.setStyle("-fx-text-fill: red;");
        } else {
            if (txtPassword.getText().equals("")&&txtPasswordConf.getText().equals("")) {
                labelMenssage.setText("as senhas não são iguais");
            } else {
                labelMenssage.setText("");
                String user = txtUser.getText().toLowerCase();
                String password = Utils.hashPassword(txtPassword.getText());
                String code = Utils.codeUser(txtUser.getText());
                User obj = new User(null, user, password, code);
                if(services.saveOrUpdate(obj)) {
                    labelMenssage.setText("Usuario criado.");
                    labelMenssage.setStyle("-fx-text-fill: green;");
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
