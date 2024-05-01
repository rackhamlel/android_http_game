package com.example.minidevgame;

public class UserSession {
    private static int userId = -1;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int id) {
        userId = id;
    }
}