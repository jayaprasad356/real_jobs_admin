package com.app.realjobadmin.models;

public class Messages {
    public Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public boolean isMessageSelected() {
        return messageSelected;
    }

    public void setMessageSelected(boolean messageSelected) {
        this.messageSelected = messageSelected;
    }

    boolean messageSelected = false;
}
