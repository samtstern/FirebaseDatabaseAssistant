package com.sachi.mark42.databasedemo.java;

import com.sachi.mark42.databaseassistant.java.DatabaseRepo;
import com.sachi.mark42.databasedemo.User;
import com.google.firebase.database.DataSnapshot;

import org.jetbrains.annotations.Nullable;

public class UserRepo extends DatabaseRepo<User> {

    private static UserRepo INSTANCE;

    @Nullable
    @Override
    public User convertDatabaseSnapshot(@Nullable DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(User.class);
    }

    public static UserRepo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepo();
        }
        return INSTANCE;
    }
}
