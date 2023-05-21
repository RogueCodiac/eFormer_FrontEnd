package eformer.front.eformer_frontend.controller;

import eformer.front.eformer_frontend.connector.UsersConnector;
import eformer.front.eformer_frontend.model.Item;
import eformer.front.eformer_frontend.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Objects;
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
    private ComboBox<String> cbRole;

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
    private TableView<User> tblEmployees;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFullName;

    @FXML
    private TextField tfSearch;

    @FXML
    private TextField tfUsername;

    private final ObservableList<User> users = FXCollections.observableArrayList();

    private User currentSelectedUser = null;

    public void activateTableFunctionalities() {
        /* Once an item is selected display the its properties */
        tblEmployees.getSelectionModel().selectedItemProperty().addListener((observable, oldSelected, selected) -> {
            currentSelectedUser = selected;

            if (selected != null) {
                setFields(selected);
            }
        });

        /* Search item by name */
        tblEmployees.getItems()
                .stream()
                .filter(item -> item != null &&
                        tfSearch.getLength() != 0 &&
                        item.getUsername().contains(tfSearch.getText()))
                .findAny()
                .ifPresent(item -> {
                    tblEmployees.getSelectionModel().select(item);
                    tblEmployees.scrollTo(item);
                });
    }

    public void setFields(User user) {
        tfEmail.setText(user.getEmail());
        tfFullName.setText(user.getFullName());
        tfUsername.setText(user.getUsername());
        cbRole.setValue(user.getRole());
        pfPassword.setText(user.getPassword());
    }

    public void clearFields() {
        tfSearch.clear();
        tfEmail.clear();
        tfFullName.clear();
        tfUsername.clear();
        cbRole.setValue(null);
        pfPassword.clear();
    }

    public void refreshTable() {
        users.clear();
        users.addAll(Objects.requireNonNull(UsersConnector.getEmployees()));
        clearFields();
        tblEmployees.setItems(users);
    }

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
        tblClmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblClmFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tblClmUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tblClmRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        tblClmIEmployeeNumber.setCellValueFactory(new PropertyValueFactory<>("userId"));


    }
}

