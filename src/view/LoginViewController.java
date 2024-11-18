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

public class LoginViewController implements Initializable {

    private final UserServices services = new UserServices();

    @FXML
    private AnchorPane anchorLogin;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private Label labelMenssage;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @FXML
    public void onBtnShowRegister() {
        showScreen("/view/RegisterView.fxml", "Registro");
    }

    @FXML
    public void onBtnLogin(ActionEvent event) {
        if (txtUser.getText().equals("") || txtPassword.getText().equals("")) {
            labelMenssage.setText("Preencha ambos os campos");
            labelMenssage.setStyle("-fx-text-fill: red;");
        } else {
            User user = services.findById(txtUser.getText());
            CodeUserTemp.setCode(user.getCodeUser());
            if (Utils.validation(Utils.hashPassword(txtPassword.getText()), user.getPassword())) {
                showScreen("/view/HomeView.fxml", "Home");

            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void showScreen(String path, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            AnchorPane anchorPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.setScene(new Scene(anchorPane));
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
