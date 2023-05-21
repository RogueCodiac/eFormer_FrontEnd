package eformer.front.eformer_frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class OrdersController {

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
    private TableColumn<?, ?> tblClmCustomer;

    @FXML
    private TableColumn<?, ?> tblClmDate;

    @FXML
    private TableColumn<?, ?> tblClmEmployee;

    @FXML
    private TableColumn<?, ?> tblClmItemId;

    @FXML
    private TableColumn<?, ?> tblClmName;

    @FXML
    private TableColumn<?, ?> tblClmNoOfItems;

    @FXML
    private TableColumn<?, ?> tblClmOrderId;

    @FXML
    private TableColumn<?, ?> tblClmProfit;

    @FXML
    private TableColumn<?, ?> tblClmQuantity;

    @FXML
    private TableColumn<?, ?> tblClmTotal;

    @FXML
    private TableColumn<?, ?> tblClmUnitPrice;

    @FXML
    private TableView<?> tblItems;

    @FXML
    private TableView<?> tblOrders;

    @FXML
    private TextField tfSearch;

}

