package eformer.front.eformer_frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EmployeesController {

    @FXML
    private AnchorPane acContent;

    @FXML
    private AnchorPane bpRightAnchor;

    @FXML
    private BorderPane bpRoot;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cbRole;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TableColumn<?, ?> tblClmEmail;

    @FXML
    private TableColumn<?, ?> tblClmFullName;

    @FXML
    private TableColumn<?, ?> tblClmIEmployeeNumber;

    @FXML
    private TableColumn<?, ?> tblClmRole;

    @FXML
    private TableColumn<?, ?> tblClmUsername;

    @FXML
    private TableView<?> tblEmployees;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFullName;

    @FXML
    private TextField tfSearch;

    @FXML
    private TextField tfUsername;

}

