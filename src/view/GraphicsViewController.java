package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

public class GraphicsViewController implements Initializable {

    @FXML
    private PieChart pieChartExpenses;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       addDataGraphic();
    }    
    
    private void addDataGraphic() {
        ObservableList<PieChart.Data> pieCharData = FXCollections.observableArrayList(
           new PieChart.Data("Agua",56.90),
           new PieChart.Data("Luz",130)
        );
        
        pieCharData.forEach(data -> data.nameProperty().bind(
                Bindings.concat (
                        data.getName(),":",data.pieValueProperty()
                )
        ));
        
        pieChartExpenses.getData().addAll(pieCharData);
    }
}