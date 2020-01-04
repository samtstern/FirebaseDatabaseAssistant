package com.firebase.mark42.databasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.firebase.mark42.databaseassistant.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        val usersViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(UsersViewModel::class.java)
        usersViewModel.getUsersFromCache()
        //usersViewModel.getUsersFromDatabase(thisquery)
        //usersViewModel.queryUsersFromCache()
        //usersViewModel.queryUsersFromDatabase(this)

        //viewModel.getUserFromCache(this)
        viewModel.getUserFromDatabase(this)

        button.setOnClickListener {
            //pushUser(viewModel)
            //updateChild(viewModel)
            //updateUser(viewModel)
            deleteUserField(viewModel)
        }
    }

    private fun deleteUserField(viewModel: UserViewModel) {
        viewModel.deleteFromDatabase()
    }

    private fun updateUser(viewModel: UserViewModel) {
        val updates = HashMap<String, Any?>()
        updates[DATABASE_KEY.USER.firstName] = "abcd"
        updates[DATABASE_KEY.USER.lastName] = "mn"
        viewModel.updateUserToDatabase(updates)
    }

    private fun updateChild(viewModel: UserViewModel) {
        viewModel.updateUserFieldToDatabase()
    }

    private fun pushUser(viewModel: UserViewModel) {
        val user = User()
        user.firstName = "Sachidananda3"
        user.lastName = "Sahu3"
        user.email = "android@gmail.com3"
        viewModel.pushUserToDatabase(user)
    }
}
