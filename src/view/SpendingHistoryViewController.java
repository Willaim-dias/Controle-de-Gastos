package view;

import config.DbIntegrityException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.History;
import model.services.HistoryServices;
import view.util.Alerts;

public class SpendingHistoryViewController implements Initializable {

    private final HistoryServices service = new HistoryServices();

    @FXML
    private TableView<History> tableDate;

    @FXML
    private TableColumn<History, Integer> columnId;

    @FXML
    private TableColumn<History, Date> columnDate;

    @FXML
    private TableColumn<History, History> columnRemo;

    @FXML
    private PieChart pieChartExpenses;

    @FXML
    private Label labelSum;

    private ObservableList<History> obsList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();

        tableDate.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectionId) -> {
            if (selectionId != null) {
                addDataGraphic(selectionId);
            }
        });
    }

    private void initializeNodes() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        updateTableView();
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        List<History> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableDate.setItems(obsList);
        initRemoveButtons();
    }

    private void initRemoveButtons() {
        columnRemo.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        columnRemo.setCellFactory(param -> new TableCell<History, History>() {
            private final Button button = new Button("remover");

            {
                button.setPrefWidth(100);
                button.setPrefHeight(20);
            }

            @Override
            protected void updateItem(History obj, boolean empty) {
                super.updateItem(obj, empty);

                if (obj == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(History obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza de que deseja excluir?");
        if (result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Service was null");
            }
            try {
                service.remove(obj);
                updateTableView();
            } catch (DbIntegrityException e) {
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void addDataGraphic(History id) {
        // Encontra o objeto 'History' correspondente
        History targetHistory = obsList.stream().filter(history -> id.getId().equals(history.getId())).findFirst().orElse(null);

        if (targetHistory != null) {
            // Converte a lista de dados em objetos PieChart.Data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            Arrays.stream(targetHistory.getDataList().split(":"))
                    .map(entry -> entry.split(";"))
                    .filter(parts -> parts.length == 2) // Garante que os dados estejam no formato esperado
                    .forEach(parts -> pieChartData.add(new PieChart.Data(parts[0], Double.parseDouble(parts[1]))));

            // Atualiza a exibição do gráfico com os dados
            pieChartData.forEach(data -> data.nameProperty().bind(
                    Bindings.concat(data.getName(), ": ", data.pieValueProperty())
            ));

            pieChartExpenses.getData().setAll(pieChartData); // Define todos os dados de uma vez
        }
    }

}
