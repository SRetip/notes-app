package com.example.lab2.model;

public enum Permission {
    ADD_SELF_NOTE("ADD_SELF_NOTE"),
    EDIT_SELF_NOTE("EDIT_SELF_NOTE"),
    DELETE_SELF_NOTE("DELETE_SELF_NOTE"),
    GET_SELF_NOTE("GET_SELF_NOTES"),
    GET_SELF_NOTES("GET_SELF_NOTES"),

    GET_NOTES("GET_NOTES"),
    DELETE_NOTES("DELETE_NOTES"),
    ADD_NOTE("ADD_NOTE"),
    EDIT_NOTE("EDIT_NOTE"),
    GET_NOTE("GET_NOTE"),
;


    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
