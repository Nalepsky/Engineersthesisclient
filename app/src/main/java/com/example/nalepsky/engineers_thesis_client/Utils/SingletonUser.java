package com.example.nalepsky.engineers_thesis_client.Utils;

public class SingletonUser  {
    private static final SingletonUser instance = new SingletonUser();
    private static Long userId;

    // Private constructor prevents instantiation from other classes
    private SingletonUser() {
    }

    public static SingletonUser getInstance() {
        return instance;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long userId) {
        SingletonUser.userId = userId;
    }
}
