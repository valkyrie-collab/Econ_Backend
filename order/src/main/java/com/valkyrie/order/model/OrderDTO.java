package com.valkyrie.order.model;

public class OrderDTO {
    private String orderId;
    private String productId;
    private String customerId;
    private String sellerId;
    private String address;
    private boolean cancel;

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getAddress() {
        return address;
    }

    public boolean getCancel() {
        return cancel;
    }

    public OrderDTO setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderDTO setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public OrderDTO setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderDTO setSellerId(String sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public OrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public OrderDTO setCancel(boolean cancel) {
        this.cancel = cancel;
        return this;
    }
}
