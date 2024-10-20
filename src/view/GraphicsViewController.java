package view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.entities.Account;
import model.services.AccountServices;

public class GraphicsViewController implements Initializable {

    private final AccountServices service = new AccountServices();
    private Double sum = 0.0;
    
    @FXML
    private Label labelValue;

    @FXML
    private PieChart pieChartExpenses;

    @FXML
    private Button btnShowValue;

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
            labelValue.setText("R$ "+sum);
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
