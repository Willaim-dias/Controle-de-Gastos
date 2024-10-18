package view;

import java.net.URL;
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

public class GraphicsViewController implements Initializable {

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
    
    private boolean showValue  = false;
    public void totalValueShow() {
        if (showValue) {
            labelValue.setText("");
            showValue = false;
        } else {
            labelValue.setText("");
            showValue = true;
        }
    }
    
    Account a1 = new Account("Agua",56.90);
    Account a2 = new Account("Luz",130.00);
    
    private void addDataGraphic() {
        ObservableList<PieChart.Data> pieCharData = FXCollections.observableArrayList(
           new PieChart.Data(a1.getAccount(),a1.getValue()),
           new PieChart.Data(a2.getAccount(),a2.getValue())
        );
        
        pieCharData.forEach(data -> data.nameProperty().bind(
                Bindings.concat (
                        data.getName(),":",data.pieValueProperty()
                )
        ));
        
        pieChartExpenses.getData().addAll(pieCharData);
    }
}