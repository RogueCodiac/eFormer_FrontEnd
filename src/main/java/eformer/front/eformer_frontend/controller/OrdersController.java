package eformer.front.eformer_frontend.controller;

import eformer.front.eformer_frontend.connector.OrdersConnector;
import eformer.front.eformer_frontend.model.Item;
import eformer.front.eformer_frontend.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

public class OrdersController implements Initializable {

    @FXML
    private AnchorPane acContent;

    @FXML
    private AnchorPane bpRightAnchor;

    @FXML
    private BorderPane bpRoot;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCancelOrder;

    @FXML
    private Button btnConfirmOrder;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnShowRange;

    @FXML
    private Button btnUpdate;

    @FXML
    private DatePicker dpDateFrom;

    @FXML
    private DatePicker dpDateTo;

    @FXML
    private Label lblAmountPaid;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblTotal;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TableColumn<?, ?> tblClmAmountPaid;

    @FXML
    private TableColumn<?, ?> tblClmOrderStatus;

    @FXML
    private TableColumn<?, ?> tblClmCustomer;

    @FXML
    private TableColumn<?, ?> tblClmDate;

    @FXML
    private TableColumn<?, ?> tblClmEmployee;

    @FXML
    private TableColumn<?, ?> tblClmItemId;

    @FXML
    private TableColumn<?, ?> tblClmItemName;

    @FXML
    private TableColumn<?, ?> tblClmNoOfItems;

    @FXML
    private TableColumn<?, ?> tblClmOrderId;

    @FXML
    private TableColumn<?, ?> tblClmProfit;

    @FXML
    private TableColumn<?, ?> tblClmItemQuantity;

    @FXML
    private TableColumn<?, ?> tblClmTotal;

    @FXML
    private TableColumn<?, ?> tblClmItemUnitPrice;

    @FXML
    private TableView<Item> tblItems;

    @FXML
    private TableView<Order> tblOrders;

    @FXML
    private TextField tfSearch;

    private ObservableList<Item> items = FXCollections.observableArrayList();

    private ObservableList<Order> orders = FXCollections.observableArrayList();

    private void refreshTables() {
        items.clear();
        orders.clear();
        clearFields();
        orders.addAll(Objects.requireNonNull(OrdersConnector.getAll()));
        tblOrders.setItems(orders);
        tblItems.setItems(items);
    }

    private void clearFields() {

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
        tblClmItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        tblClmItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblClmItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        tblClmOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblClmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblClmAmountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        tblClmCustomer.setCellValueFactory(new PropertyValueFactory<>("customerUsername"));
        tblClmEmployee.setCellValueFactory(new PropertyValueFactory<>("employeeUsername"));
        tblClmNoOfItems.setCellValueFactory(new PropertyValueFactory<>("numberOfItems"));
        tblClmProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));
        tblClmTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblClmOrderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        refreshTables();
    }
}

