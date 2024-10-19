package view;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.entities.Account;
import model.services.AccountServices;
import view.util.Alerts;

public class ContaViewController implements Initializable {

    private Account account;
    private AccountServices service;
    
    @FXML
    private TextField txtAccount;

    @FXML
    private TextField txtValue;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Account> tableAccount;

    @FXML
    private TableColumn<Account, Integer> columnId;
    
    @FXML
    private TableColumn<Account, String> conlumnAccount;

    @FXML
    private TableColumn<Account, Double> columnValue;

    @FXML
    private TableColumn<Account, Account> columnEdi;

    @FXML
    private TableColumn<Account, Account> columnRemo;

    @FXML
    private Label labelResult;

    @FXML
    private Label labelTotal;

    @FXML
    private TextField txtIncome;
    
    private ObservableList<Account> obsList;

    public void onBtnSaveAccount() {
        if (txtAccount.getText().equals("")||txtValue.getText().equals("")) {
            Alerts.showAlert("Info","", "Preencha ambos os campos", Alert.AlertType.INFORMATION);
        } else {
            Account obj = new Account(null,"luz",12.90);
            service.saveOrUpdate(obj);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new AccountServices();
    }    
    
    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        
        List<Account> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
    }
}
