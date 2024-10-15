package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class MainViewController implements Initializable {

    @FXML
    private AnchorPane panelGeneral;

    public void onMenuItemContaAction() {
        try {
            Parent panelAccount = FXMLLoader.load(getClass().getResource("/view/ContaView.fxml"));
            panelGeneral.getChildren().clear();
            panelGeneral.getChildren().add(panelAccount);
        } catch (IOException e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
