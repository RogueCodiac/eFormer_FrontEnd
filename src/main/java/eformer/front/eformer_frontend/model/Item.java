package eformer.front.eformer_frontend.model;

import eformer.front.eformer_frontend.connectors.ItemsConnector;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Item {
    private Integer itemId;

    private String name;

    private String description;

    private Integer quantity;

    private Double unitPrice;

    private LocalDateTime introductionDate;

    public Item(JSONObject json) {
        ItemsConnector.mapToObject(json, this);
    }

    public Item(String name, String description,
                Integer quantity, Double unitPrice) {
        setName(name);
        setDescription(description);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        setUnitPrice(unitPrice.doubleValue());
    }

    public LocalDateTime getIntroductionDate() {
        return introductionDate;
    }

    public void setIntroductionDate(LocalDateTime introductionDate) {
        this.introductionDate = introductionDate;
    }

    public void setIntroductionDate(String introductionDate) {
        setIntroductionDate(LocalDateTime.parse(introductionDate));
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
