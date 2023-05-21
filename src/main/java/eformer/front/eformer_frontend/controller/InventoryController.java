package eformer.front.eformer_frontend.controller;

import eformer.front.eformer_frontend.Main;
import eformer.front.eformer_frontend.connector.ItemsConnector;
import eformer.front.eformer_frontend.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InventoryController implements Initializable {

    @FXML
    private AnchorPane acContent;

    @FXML
    private BorderPane bpRoot;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnUpdate;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TextArea taDescription;

    @FXML
    private TableColumn<?, ?> tblClmCostPrice;

    @FXML
    private TableColumn<?, ?> tblClmDescription;

    @FXML
    private TableColumn<?, ?> tblClmItemId;

    @FXML
    private TableColumn<?, ?> tblClmItemName;

    @FXML
    private TableColumn<?, ?> tblClmQuantity;

    @FXML
    private TableColumn<?, ?> tblClmUnitPrice;

    @FXML
    private TableView<Item> tblInventory;

    @FXML
    private TextField tfCostPrice;

    @FXML
    private TextField tfItemName;

    @FXML
    private TextField tfQuantity;

    @FXML
    private TextField tfSearch;

    @FXML
    private TextField tfUnitPrice;

    private final ObservableList<Item> items = FXCollections.observableArrayList();

    private Item currentSelectedItem = null;

    public void activateTableFunctionalities() {
        /* Once an item is selected display its properties */
        tblInventory.getSelectionModel().selectedItemProperty().addListener((observable, oldSelected, selected) -> {
            currentSelectedItem = selected;

            if (selected != null) {
                setFields(selected);
            }
        });

        /* Search item by name */
        tblInventory.getItems()
                .stream()
                .filter(item -> item != null &&
                        tfSearch.getLength() != 0 &&
                        item.getName().contains(tfSearch.getText()))
                .findAny()
                .ifPresent(item -> {
                    tblInventory.getSelectionModel().select(item);
                    tblInventory.scrollTo(item);
        });
    }

    public static Optional<Pair<Item, Integer>> getNewItem() {
        // Create the custom dialog.
        Dialog<Pair<Item, Integer>> dialog = new Dialog<>();
        dialog.setTitle("New Item");
        dialog.setHeaderText("Add item");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(Main.getImage("cart.png")));

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
                            setText(String.format("%s, Up: $%.2f,\nCo $%.2f, Av: %d",
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

        // Enable/Disable login button depending on whether a username was entered.
        var confirmButton = dialog.getDialogPane().lookupButton(loginButtonType);
        confirmButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        cbItemName.valueProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(cbItemName.getValue() == null || cbQuantity.getValue() == null);
        });

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(cbItemName.getValue(), cbQuantity.getValue());
            }

            return null;
        });

        return dialog.showAndWait();
    }

    public void refresh(ActionEvent ignored) {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        System.out.println(result);

        refreshTable();
    }

    public void refreshTable() {
        items.clear();
        items.addAll(Objects.requireNonNull(ItemsConnector.getAll()));
        clearFields();
        tblInventory.setItems(items);
    }

    public void setFields(Item item) {
        tfCostPrice.setText(String.format("$%.2f", item.getCost()));
        tfQuantity.setText(item.getQuantity().toString());
        tfItemName.setText(item.getName());
        tfUnitPrice.setText(String.format("$%.2f", item.getUnitPrice()));
        taDescription.setText(item.getDescription());
    }

    public void clearFields() {
        tfCostPrice.clear();
        tfQuantity.clear();
        tfItemName.clear();
        tfUnitPrice.clear();
        taDescription.clear();
        tfSearch.clear();
        currentSelectedItem = null;
    }

    public Item fetchItemFromFields() {
        var costStr = '$' + tfCostPrice.getText();
        Double cost = Double.parseDouble(costStr.substring(costStr.lastIndexOf('$') + 1));

        Integer quantity = Integer.parseInt(tfQuantity.getText());
        var name = tfItemName.getText();

        var unitPriceStr = '$' + tfUnitPrice.getText();
        Double unitPrice = Double.parseDouble(unitPriceStr.substring(unitPriceStr.lastIndexOf('$') + 1));

        var description = taDescription.getText();

        return new Item(name, description, quantity, unitPrice, cost);
    }

    public void btnCancelAction(ActionEvent ignored) {
        setFields(currentSelectedItem);
    }

    public void btnUpdateAction(ActionEvent ignored) {
        var fieldsItem = fetchItemFromFields();

        if (currentSelectedItem != null) {
            fieldsItem.setItemId(currentSelectedItem.getItemId());

            var item = ItemsConnector.update(fieldsItem);

            items.set(items.indexOf(currentSelectedItem), item);
            currentSelectedItem = item;
        } else {
            var item = ItemsConnector.create(fieldsItem);
            items.add(item);
            currentSelectedItem = item;
        }

        tblInventory.setItems(items);
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
        tblClmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblClmItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblClmCostPrice.setCellValueFactory(new PropertyValueFactory<>("cost"));
        tblClmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        refreshTable();
        activateTableFunctionalities();
    }
}

