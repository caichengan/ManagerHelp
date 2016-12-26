package com.xht.android.managerhelp.mode;

/**
 * Created by Administrator on 2016/12/17.  an
 */

public class NewDataBean {
    private String ComName;
    private String ComAddress;
    private String Contacts;
    private String Style;
    private String Time;

    private String OrderMoney;//订单金额


    private String StepName;//步骤名
    private String BanZhengContact;//办证人


    private String Comment;//评语
    private String Level;//得分

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    private String Status;

    private String orderId;

    private String CommentId;

    public String getCommentId() {
        return CommentId;
    }

    public void setCommentId(String commentId) {
        CommentId = commentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStepName() {
        return StepName;
    }

    public void setStepName(String stepName) {
        StepName = stepName;
    }

    public String getBanZhengContact() {
        return BanZhengContact;
    }

    public void setBanZhengContact(String banZhengContact) {
        BanZhengContact = banZhengContact;
    }

    public String getComName() {
        return ComName;
    }

    public void setComName(String comName) {
        ComName = comName;
    }

    public String getComAddress() {
        return ComAddress;
    }

    public void setComAddress(String comAddress) {
        ComAddress = comAddress;
    }

    public String getContacts() {
        return Contacts;
    }

    public void setContacts(String contacts) {
        Contacts = contacts;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String style) {
        Style = style;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getOrderMoney() {
        return OrderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        OrderMoney = orderMoney;
    }
}
