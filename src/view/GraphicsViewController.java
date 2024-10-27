package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Account;
import model.services.AccountServices;
import view.util.Alerts;

public class GraphicsViewController implements Initializable {

    private final AccountServices service = new AccountServices();
    private Double sum = 0.0;
    
    @FXML
    private Label labelValue;

    @FXML
    private PieChart pieChartExpenses;

    @FXML
    private Button btnShowValue;

    public void onBtnShowHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SpendingHistoryView.fxml"));
            AnchorPane anchorPane = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Historico de Gastos");
            dialogStage.setScene(new Scene(anchorPane));
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addDataGraphic();
    }

    private boolean showValue = false;

    public void totalValueShow() {
        if (showValue) {
            labelValue.setText("");
            showValue = false;
        } else {
            labelValue.setText("R$ "+String.format("%.2f", sum));
            showValue = true;
        }
    }

    private void addDataGraphic() {
        List<Account> list = service.findAll();

        ObservableList<PieChart.Data> pieCharData = FXCollections.observableArrayList();

        for (Account a : list) {
            sum += a.getValue();
            pieCharData.add(new PieChart.Data(a.getAccount(), a.getValue()));
        }

        pieCharData.forEach(data -> data.nameProperty().bind(
                Bindings.concat(
                        data.getName(), ": ", data.pieValueProperty()
                )
        ));

        pieChartExpenses.getData().addAll(pieCharData);
    }
}
