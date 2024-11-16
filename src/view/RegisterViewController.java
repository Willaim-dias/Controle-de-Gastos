package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegisterViewController implements Initializable {

    @FXML
    private AnchorPane anchorRegister;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSave;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
