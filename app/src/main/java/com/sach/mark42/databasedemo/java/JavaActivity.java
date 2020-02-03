package com.sach.mark42.databasedemo.java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sach.mark42.databasedemo.DATABASE_KEY;
import com.sach.mark42.databasedemo.R;
import com.sach.mark42.databasedemo.User;

import java.util.HashMap;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        Button button = findViewById(R.id.button);

        final UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        final UsersViewModel usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        //usersViewModel.getUsersFromDatabase(this);
        //usersViewModel.getUsersFromCache(this);
        //usersViewModel.queryUsersFromDatabase(this);
        //usersViewModel.queryUsersFromCache(this);

        //userViewModel.getUserFromDatabase(this);
        //userViewModel.getUserFromCache(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pushUser(userViewModel);
                //updateChild(userViewModel);
                //updateUser(userViewModel);
                //deleteUserField(userViewModel);
            }
        });
    }

    private void deleteUserField(UserViewModel userViewModel) {
        String path = "users/-Lzhc1j7MwE5BVeDPtdb/" + DATABASE_KEY.USER.email;
        userViewModel.deleteFromDatabase(this, path);
    }

    private void updateUser(UserViewModel userViewModel) {
        String path = "users/-LzhYTCGOOYIQt1uOq7B";
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(DATABASE_KEY.USER.firstName, "asdf");
        updates.put(DATABASE_KEY.USER.email, 5);
        userViewModel.updateUserToDatabase(this, path, updates);
    }

    private void updateChild(UserViewModel userViewModel) {
        String path = "users/-LxjJoZry_g7uqwP3RRZ/" + DATABASE_KEY.USER.firstName;
        userViewModel.updateUserFieldToDatabase(this, path, "Sachin");
    }

    private void pushUser(UserViewModel userViewModel) {
        User user = new User();
        user.setFirstName("Sach");
        user.setLastName("Sahu");
        user.setEmail("android@gmail.com5");
        userViewModel.pushUserToDatabase(this, user);
    }
}
