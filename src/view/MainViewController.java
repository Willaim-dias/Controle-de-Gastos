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
        loadPanel("/view/ContaView.fxml");
    }

    public void onMenuItemCreditAction() {
        loadPanel("/view/CreditCardView.fxml");
    }
    
    public void onMenuItemGraphicsAction(){
        loadPanel("/view/GraphicsView.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPanel("/view/GraphicsView.fxml");
    }
    
    private void loadPanel(String path) {
        try {
            Parent panelAccount = FXMLLoader.load(getClass().getResource(path));
            panelGeneral.getChildren().clear();
            panelGeneral.getChildren().add(panelAccount);
        } catch (IOException e) {  
            System.out.println(e.getMessage());
        }
    }
}
