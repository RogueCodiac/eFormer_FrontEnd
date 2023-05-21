package eformer.front.eformer_frontend.controller;

import eformer.front.eformer_frontend.Main;
import eformer.front.eformer_frontend.connector.ItemsConnector;
import eformer.front.eformer_frontend.connector.OrdersConnector;
import eformer.front.eformer_frontend.model.Item;
import eformer.front.eformer_frontend.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnRemoveItem;

    private ObservableList<Item> items = FXCollections.observableArrayList();

    private ObservableList<Order> orders = FXCollections.observableArrayList();

    private Order currentSelectedOrder = null;

    public void activateTableFunctionalities() {
        /* Once an order is selected display its items */
        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldSelected, selected) -> {
            currentSelectedOrder = selected;

            if (selected != null) {
                setItems(selected);
            }
        });
    }

    public Optional<Pair<Item, Integer>> getNewItem() {
        // Create the custom dialog.
        Dialog<Pair<Item, Integer>> dialog = new Dialog<>();
        dialog.setTitle("New Item");
        dialog.setHeaderText("Add item");

        var image = new ImageView(Main.getImage("cart.png"));
        image.setFitHeight(50);
        image.setFitWidth(50);

        dialog.setGraphic(image);

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        var cbItemName = new ComboBox<Item>();

        var cellFactory = new Callback<ListView<Item>, ListCell<Item>>() {

            @Override
            public ListCell<Item> call(ListView<Item> l) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(String.format("%s, Price: $%.2f\nCost $%.2f, Quantity: %d",
                                    item.getName(),
                                    item.getUnitPrice(),
                                    item.getCost(),
                                    item.getQuantity())
                            );
                        }
                    }
                } ;
            }
        };

        cbItemName.setButtonCell(cellFactory.call(null));
        cbItemName.setCellFactory(cellFactory);

        ObservableList<Item> allItems = FXCollections.observableArrayList();
        allItems.setAll(ItemsConnector.getAll());
        cbItemName.setItems(allItems);

        var cbQuantity = new ComboBox<Integer>();

        cbItemName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue.getQuantity() == 0) {
                return;
            }

            cbQuantity.getItems().setAll(
                    IntStream.rangeClosed(1, newValue.getQuantity()).boxed().collect(Collectors.toList())
            );
        });

        grid.add(new Label("Item: "), 0, 0);
        grid.add(cbItemName, 1, 0);
        grid.add(new Label("Quantity: "), 0, 1);
        grid.add(cbQuantity, 1, 1);

        var confirmButton = dialog.getDialogPane().lookupButton(loginButtonType);
        confirmButton.setDisable(true);

        cbItemName.valueProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(cbItemName.getValue() == null);
        });

        cbQuantity.valueProperty().addListener(((observable, oldValue, newValue) -> {
            confirmButton.setDisable(cbQuantity.getValue() == null);
        }));

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(cbItemName.getValue(), cbQuantity.getValue());
            }

            return null;
        });

        return dialog.showAndWait();
    }

    public void displayWarning(String header, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public void btnAddItemAction(ActionEvent ignored) {
        if (currentSelectedOrder == null) {
            displayWarning("No order selected", "Please select an order & try again");
            return;
        }

        var item = getNewItem();
    }

    private void refreshOrderItems() {
        if (currentSelectedOrder != null) {
            setItems(currentSelectedOrder);
        }
    }

    private void refreshTables() {
        items.clear();
        orders.clear();
        clearFields();
        orders.addAll(Objects.requireNonNull(OrdersConnector.getAll()));
        tblOrders.setItems(orders);
        tblItems.setItems(items);
    }

    private void clearFields() {
        currentSelectedOrder = null;
        lblTotal.setText("$0.00");
        lblAmountPaid.setText("$0.00");
    }

    public void setItems(Order order) {
        items.clear();
        items.addAll(Objects.requireNonNull(OrdersConnector.getItems(order.getOrderId())));
        lblTotal.setText(String.format("$%.2f", order.getTotal()));
        lblAmountPaid.setText(String.format("%.2f", order.getAmountPaid()));
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
        tblClmItemQuantity.setCellValueFactory(new PropertyValueFactory<>("requestedQuantity"));
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
        activateTableFunctionalities();
    }
}

