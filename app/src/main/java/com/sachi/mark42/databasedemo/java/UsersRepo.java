package com.sachi.mark42.databasedemo.java;

import com.sachi.mark42.databaseassistant.java.DatabaseRepo;
import com.sachi.mark42.databasedemo.User;
import com.google.firebase.database.DataSnapshot;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UsersRepo extends DatabaseRepo<List<User>> {

    private static UsersRepo INSTANCE;

    @Nullable
    @Override
    public List<User> convertDatabaseSnapshot(@Nullable DataSnapshot dataSnapshot) {
        ArrayList<User> users = new ArrayList<>();
        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
            try {
                User user = snapshot.getValue(User.class);
                users.add(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public static UsersRepo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsersRepo();
        }
        return INSTANCE;
    }
}
