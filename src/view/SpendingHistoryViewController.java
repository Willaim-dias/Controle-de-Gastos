package view;

import config.DbIntegrityException;
import java.io.File;
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
import javafx.stage.FileChooser;
import model.entities.Extract;
import model.services.ExtractServices;
import view.util.Alerts;
import view.util.CreatePdf;
import view.util.Utils;

public class SpendingHistoryViewController implements Initializable {

    private final ExtractServices service = new ExtractServices();

    @FXML
    private Label labelTotalValue;

    @FXML
    private TableView<Extract> tableDate;

    @FXML
    private TableColumn<Extract, Integer> columnId;

    @FXML
    private TableColumn<Extract, Date> columnDate;

    @FXML
    private TableColumn<Extract, Extract> columnRemo;

    @FXML
    private PieChart pieChartExpenses;

    private ObservableList<Extract> obsList;

    public void onBtExtract(ActionEvent event) {

        Extract obj = tableDate.getSelectionModel().getSelectedItem();

        if (obj != null) {
            String value = labelTotalValue.getText();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar arquivo");
            fileChooser.setInitialFileName("Extrato "+obj.getDate()+".pdf");
            File url = fileChooser.showSaveDialog(Utils.currentStage(event));

            Date date = new Date();

            CreatePdf.createHistoryExtract(obj, value, date, url);
        } else {
            Alerts.showAlert("Info", "", "Selecione uma Linha", Alert.AlertType.INFORMATION);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();

        tableDate.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectionId) -> {
            if (selectionId != null) {
                addDataGraphic(selectionId);
            }
        });
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }

        List<Extract> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableDate.setItems(obsList);
        initRemoveButtons();
    }

    private void initializeNodes() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        Utils.formatTableColumnDate(columnDate, "dd/MM/yyyy");
        updateTableView();
    }

    private void initRemoveButtons() {
        columnRemo.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        columnRemo.setCellFactory(param -> new TableCell<Extract, Extract>() {
            private final Button button = new Button("remover");

            {
                button.setPrefWidth(100);
                button.setPrefHeight(20);
            }

            @Override
            protected void updateItem(Extract obj, boolean empty) {
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

    private void removeEntity(Extract obj) {
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

    private void addDataGraphic(Extract id) {
        Extract targetHistory = obsList.stream().filter(history -> id.getId().equals(history.getId())).findFirst().orElse(null);

        if (targetHistory != null) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            Arrays.stream(targetHistory.getDataList().split("\n")).map(entry -> entry.split(","))
                    .filter(parts -> parts.length == 2)
                    .forEach(parts -> pieChartData.add(new PieChart.Data(parts[0], Double.parseDouble(parts[1]))));

            pieChartData.forEach(data -> data.nameProperty().bind(
                    Bindings.concat(data.getName(), ": ", data.pieValueProperty())
            ));

            double total = pieChartData.stream().mapToDouble(PieChart.Data::getPieValue).sum();

            labelTotalValue.setText("R$ " + String.format("%.2f", total));
            pieChartExpenses.getData().setAll(pieChartData);
        }
    }

}
