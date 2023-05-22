package eformer.front.eformer_frontend.controller;

import eformer.front.eformer_frontend.Main;
import eformer.front.eformer_frontend.connector.ItemsConnector;
import eformer.front.eformer_frontend.connector.OrdersConnector;
import eformer.front.eformer_frontend.connector.UsersConnector;
import eformer.front.eformer_frontend.model.Item;
import eformer.front.eformer_frontend.model.Order;
import eformer.front.eformer_frontend.model.User;
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
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDateTime;
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
    private ComboBox<?> cbFilterCustomer;

    // TODO IMPLEMENT
    @FXML
    private ComboBox<?> cbFilterEmployee;

    @FXML
    private Label lblActualTotalSales;

    @FXML
    private Label lblNoItems;

    @FXML
    private Label lblNoOrders;

    @FXML
    private Label lblProfit;

    @FXML
    private Label lblTotalSales;

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnRemoveItem;

    private final ObservableList<Item> items = FXCollections.observableArrayList();

    private final ObservableList<Order> orders = FXCollections.observableArrayList();

    private Order currentSelectedOrder = null;

    private Item currentSelectedItem = null;

    public void activateTableFunctionalities() {
        /* Once an order is selected display its items */
        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldSelected, selected) -> {
            currentSelectedOrder = selected;

            if (selected != null) {
                setItems(selected);
            }
        });

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldSelected, selected) -> {
            currentSelectedItem = selected;
        });
    }

    public Optional<Item> getNewItem() {
        // Create the custom dialog.
        Dialog<Item> dialog = new Dialog<>();
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
                var result = cbItemName.getValue();
                result.setRequestedQuantity(cbQuantity.getValue());

                return result;
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

    public void refresh(ActionEvent ignored) {
        refreshTables();
    }

    public void btnAddItemAction(ActionEvent ignored) {
        if (currentSelectedOrder == null) {
            displayWarning("No order selected", "Please select an order & try again");
            return;
        }

        var item = getNewItem();

        if (item.isPresent()) {
            items.add(item.get());
            tblItems.setItems(items);
            changeTotalToItems();
        }
    }

    public Optional<Pair<User, String>> getCustomer() {
        var cbCustomer = new ComboBox<User>();

        Dialog<Pair<User, String>> dialog = new Dialog<>();
        dialog.setTitle("New Order");
        dialog.setHeaderText("Choose customer");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        var image = new ImageView(Main.getImage("cart.png"));
        image.setFitHeight(50);
        image.setFitWidth(50);

        dialog.setGraphic(image);

        var cellFactory = new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> l) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        if (user == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(String.format("# %d, %s\n%s",
                                    user.getUserId(),
                                    user.getUsername(),
                                    user.getFullName()
                            ));
                        }
                    }
                } ;
            }
        };

        cbCustomer.setButtonCell(cellFactory.call(null));
        cbCustomer.setCellFactory(cellFactory);

        ObservableList<User> allCustomers = FXCollections.observableArrayList();
        allCustomers.setAll(UsersConnector.getCustomers());
        cbCustomer.setItems(allCustomers);

        TextField note = new TextField();
        note.setPromptText("(optional)");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Customer: "), 0, 0);
        grid.add(cbCustomer, 1, 0);
        grid.add(new Label("Note:"), 0, 1);
        grid.add(note, 1, 1);

        var confirmButton = dialog.getDialogPane().lookupButton(loginButtonType);
        confirmButton.setDisable(true);

        cbCustomer.valueProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(cbCustomer.getValue() == null);
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(cbCustomer.getValue(), note.getText());
            }

            return null;
        });

        return dialog.showAndWait();
    }

    public void btnCreateOrderAction(ActionEvent ignored) {
        var customer = getCustomer();

        customer.ifPresent(userStringPair -> orders.add(OrdersConnector.create(
                userStringPair.getKey().getUserId(),
                new JSONObject(),
                userStringPair.getValue()))
        );

        tblOrders.setItems(orders);
    }

    public void btnRemoveItemAction(ActionEvent ignored) {
        if (currentSelectedItem == null) {
            displayWarning("No item selected", "Please select an item & try again");
            return;
        }

        items.remove(currentSelectedItem);
        currentSelectedOrder = null;
        changeTotalToItems();
    }

    public void btnCancelOrderAction(ActionEvent ignored) {
        if (currentSelectedOrder == null) {
            displayWarning("No order selected", "Please select an order & try again");
            return;
        }

        OrdersConnector.cancel(currentSelectedOrder.getOrderId());
        refreshTables();
    }

    public void btnConfirmOrderAction(ActionEvent ignored) {
        if (currentSelectedOrder == null) {
            displayWarning("No order selected", "Please select an order & try again");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(currentSelectedOrder.getTotal().toString());
        dialog.setTitle("Paid");
        dialog.setHeaderText("Order payment");
        dialog.setContentText("Please enter the total paid:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
                var amount = Double.parseDouble(result.get());
                OrdersConnector.confirm(currentSelectedOrder.getOrderId(), amount);
                refreshTables();
            } catch (Exception ignored2) {
                displayWarning("An error occurred", "Enter a valid amount");
            }
        }
    }

    public void btnCancelAction(ActionEvent ignored) {
        if (currentSelectedOrder == null) {
            displayWarning("No order selected", "Please select an order & try again");
            return;
        }

        refreshOrderItems();
        currentSelectedItem = null;
    }

    public void btnShowRangeAction(ActionEvent ignored) {
        LocalDateTime start = null, end = null;

        if (dpDateFrom.getValue() != null) {
            start = LocalDateTime.parse(dpDateFrom.getValue().toString() + "T00:00:00");
        }

        if (dpDateTo.getValue() != null) {
            end = LocalDateTime.parse(dpDateTo.getValue().toString() + "T00:00:00");
        }

        if (start != null && end != null && end.isAfter(start)) {
            orders.clear();
            orders.setAll(OrdersConnector.getAllBetween(start, end));
        } else if (start == null && end != null) {
            orders.clear();
            orders.setAll(OrdersConnector.getAllBeforeDate(end));
        } else if (end == null && start != null && LocalDateTime.now().isAfter(start)) {
            orders.clear();
            orders.setAll(OrdersConnector.getAllAfterDate(start));
        } else {
            displayWarning("Invalid dates", "Please choose correct dates");
            refreshTables();
            return;
        }

        recalibrate();
    }

    public void recalibrate() {
        Double actualTotalSales = 0.0, totalSales = 0.0, profit = 0.0;
        Integer nbItems = 0;

        for (var order: orders) {
            actualTotalSales += order.getAmountPaid();
            totalSales += order.getTotal();
            profit += order.getProfit();
            nbItems += order.getNumberOfItems();
        }

        lblActualTotalSales.setText(String.format("%.2f", actualTotalSales));
        lblTotalSales.setText(String.format("%.2f", totalSales));
        lblProfit.setText(String.format("%.2f", profit));
        lblNoItems.setText(nbItems.toString());
        lblNoOrders.setText(orders.size() + "");
    }

    public void btnUpdateAction(ActionEvent ignored) {
        if (currentSelectedOrder == null) {
            displayWarning("No order selected", "Please select an order & try again");
            return;
        }

        var itemIds = new JSONObject();

        for (var item: items) {
            itemIds.put(item.getItemId().toString(), item.getRequestedQuantity());
        }

        var temp = OrdersConnector.update(currentSelectedOrder.getOrderId(), itemIds);

        if (temp != null) {
            orders.set(orders.indexOf(currentSelectedOrder), temp);
            currentSelectedOrder = temp;
        }
    }

    public void changeTotalToItems() {
        var total = 0.0;

        for (var item: items) {
            total += item.getRequestedQuantity() * item.getUnitPrice();
        }

        lblTotal.setText(String.format("$%.2f", total));
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

        lblActualTotalSales.setText(String.format("%.2f", OrdersConnector.getTotalActualSales()));
        lblTotalSales.setText(String.format("%.2f", OrdersConnector.getTotalSales()));
        lblProfit.setText(String.format("%.2f", OrdersConnector.getTotalProfit()));
        lblNoItems.setText(OrdersConnector.getTotalSoldQuantity().toString());
        lblNoOrders.setText(orders.size() + "");
    }

    private void clearFields() {
        currentSelectedOrder = null;
        currentSelectedItem = null;
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

