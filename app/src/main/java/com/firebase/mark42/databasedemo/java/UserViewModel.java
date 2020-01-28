package com.firebase.mark42.databasedemo.java;

import android.os.AsyncTask;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.firebase.mark42.databaseassistant.DatabaseResult;
import com.firebase.mark42.databasedemo.DATABASE_KEY;
import com.firebase.mark42.databasedemo.R;
import com.firebase.mark42.databasedemo.User;

import java.net.URL;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends ViewModel {

    //private UserRepo userRepo = new UserRepo();
    private UserRepo userRepo = UserRepo.getInstance();

    public void getUserFromDatabase(AppCompatActivity activity) {
        final TextView fName = activity.findViewById(R.id.firstName);
        final TextView lName = activity.findViewById(R.id.lastName);
        final TextView email = activity.findViewById(R.id.email);

        userRepo.getFromDatabase("users/-LxjJXsJdbZiEjjh1A89").observe(activity, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                fName.setText(user.getFirstName());
                lName.setText(user.getLastName());
                email.setText(user.getEmail());
            }
        });
    }

    public void getUserFromCache(final AppCompatActivity activity) {
        getUserFromCache().observe(activity, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                TextView fName = activity.findViewById(R.id.firstName);
                TextView lName = activity.findViewById(R.id.lastName);
                TextView email = activity.findViewById(R.id.email);

                fName.setText(user.getFirstName());
                lName.setText(user.getLastName());
                email.setText(user.getEmail());
            }
        });
    }

    private static LiveData<User> getUserFromCache() {
        final UserRepo userRepo = UserRepo.getInstance();
        final MutableLiveData<User> liveData = new MutableLiveData<>();
        new AsyncTask<Void, Void, LiveData<User>>() {

            @Override
            protected LiveData<User> doInBackground(Void... voids) {
                try {
                    User result = userRepo.getFromDatabaseCache
                            ("users/-LxjJXsJdbZiEjjh1A89").get();
                    liveData.postValue(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return liveData;
            }
        }.execute();

        return liveData;
    }

    public static LiveData<DatabaseResult> deleteFromDatabase() {
        final UserRepo userRepo = UserRepo.getInstance();
        final MutableLiveData<DatabaseResult> liveData = new MutableLiveData<>();
        new AsyncTask<Void, Void, LiveData<DatabaseResult>>() {

            @Override
            protected LiveData<DatabaseResult> doInBackground(Void... voids) {
                try {
                    DatabaseResult result = userRepo.deleteFromDatabase("").get();
                    liveData.postValue(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return liveData;
            }
        }.execute();
        return liveData;
    }
}
