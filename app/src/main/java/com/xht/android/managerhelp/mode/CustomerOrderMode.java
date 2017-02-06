package com.xht.android.managerhelp.mode;

/**
 * Created by Administrator on 2017/1/6.
 */
public class CustomerOrderMode {

    private String orderStyle;
    private String orderId;
    private String companyId;
    private String orderEndTime;
    private String orderStartTime;
    private String orderMoney;
    private String businezzType;

    public String getBusinezzType() {
        return businezzType;
    }

    public void setBusinezzType(String businezzType) {
        this.businezzType = businezzType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public String getOrderStyle() {
        return orderStyle;
    }

    public void setOrderStyle(String orderStyle) {
        this.orderStyle = orderStyle;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }
}
