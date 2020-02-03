package com.sach.mark42.databasedemo.java;

import android.os.AsyncTask;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.sach.mark42.databaseassistant.DatabaseResult;
import com.sach.mark42.databasedemo.R;
import com.sach.mark42.databasedemo.User;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends ViewModel {

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
        getUserFromCache("users/-LxjJXsJdbZiEjjh1A89").observe(activity, new Observer<User>() {
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

    public void pushUserToDatabase(AppCompatActivity activity, User user) {
        pushUserToDatabase(user).observe(activity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //it return the firebase push id key
                String key = s;
                // show confirmation message to user
            }
        });
    }

    public void updateUserFieldToDatabase(AppCompatActivity activity, String path, String value) {
        updateUserFieldToDatabase(path, value).observe(activity, new Observer<DatabaseResult>() {
            @Override
            public void onChanged(DatabaseResult databaseResult) {
                if (databaseResult.isSuccess()) {
                    //Display success message
                } else {
                    //Display error message
                    databaseResult.getErrorMessage();
                }
            }
        });
    }

    public void updateUserToDatabase(AppCompatActivity activity, String path,
                                     HashMap<String, Object> updates) {
        updateUserToDatabase(path, updates).observe(activity, new Observer<DatabaseResult>() {
            @Override
            public void onChanged(DatabaseResult databaseResult) {
                if (databaseResult.isSuccess()) {
                    //Display success message
                } else {
                    //Display error message
                    databaseResult.getErrorMessage();
                }
            }
        });
    }

    public void deleteFromDatabase(AppCompatActivity activity, String path) {
        deleteFromDatabase(path).observe(activity, new Observer<DatabaseResult>() {
            @Override
            public void onChanged(DatabaseResult databaseResult) {
                if (databaseResult.isSuccess()) {
                    //Display success message
                } else {
                    //Display error message
                    databaseResult.getErrorMessage();
                }
            }
        });
    }

    private static LiveData<User> getUserFromCache(final String path) {
        final UserRepo userRepo = UserRepo.getInstance();
        final MutableLiveData<User> liveData = new MutableLiveData<>();
        new AsyncTask<Void, Void, LiveData<User>>() {

            @Override
            protected LiveData<User> doInBackground(Void... voids) {
                try {
                    User result = userRepo.getFromDatabaseCache(path).get();
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

    private static LiveData<String> pushUserToDatabase(final User user) {
        final UserRepo userRepo = UserRepo.getInstance();
        final MutableLiveData<String> liveData = new MutableLiveData<>();
        new AsyncTask<Void, Void, LiveData<String>>() {
            @Override
            protected LiveData<String> doInBackground(Void... voids) {
                try {
                    String key = userRepo.pushToDatabase("users", user).get();
                    liveData.postValue(key);
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

    private static LiveData<DatabaseResult> updateUserFieldToDatabase(final String path, final String value) {
        final UserRepo userRepo = UserRepo.getInstance();
        final MutableLiveData<DatabaseResult> liveData = new MutableLiveData<>();
        new AsyncTask<Void, Void, LiveData<DatabaseResult>>() {

            @Override
            protected LiveData<DatabaseResult> doInBackground(Void... voids) {
                try {
                    DatabaseResult result = userRepo.updateChildToDatabase(path, value).get();
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

    private static LiveData<DatabaseResult> updateUserToDatabase(final String path,
                                                                final HashMap<String, Object> updates) {
        final UserRepo userRepo = UserRepo.getInstance();
        final MutableLiveData<DatabaseResult> liveData = new MutableLiveData<>();
        new AsyncTask<Void, Void, LiveData<DatabaseResult>>() {

            @Override
            protected LiveData<DatabaseResult> doInBackground(Void... voids) {
                try {
                    DatabaseResult result = userRepo.updateChildrenToDatabase(path, updates).get();
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

    private static LiveData<DatabaseResult> deleteFromDatabase(final String path) {
        final UserRepo userRepo = UserRepo.getInstance();
        final MutableLiveData<DatabaseResult> liveData = new MutableLiveData<>();
        new AsyncTask<Void, Void, LiveData<DatabaseResult>>() {

            @Override
            protected LiveData<DatabaseResult> doInBackground(Void... voids) {
                try {
                    DatabaseResult result = userRepo.deleteFromDatabase(path).get();
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
