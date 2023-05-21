package eformer.front.eformer_frontend.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {

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

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

