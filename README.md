# FirebaseDatabaseAssistant
A Library to implement an Assistant for Firebase Realtime Database in Android.

## Demo
The Demo app is available [here](https://www.github.com/Im-Mark42/FirebaseDatabaseAssistant/tree/master/app/src/main/java/com/firebase/mark42/databasedemo) that demonstrates feature of this android library.

#### Note: Right now this library is only for kotlin based android app.

## How to?
Create a new project in Firebase console and link that project in to your android app.
#### Add it in your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}
```
#### Add the dependency
```
dependencies {
          implementation 'com.google.firebase:firebase-core:17.2.1'
          implementation 'com.google.firebase:firebase-database:19.2.0'
          
          implementation 'com.github.Im-Mark42:FirebaseDatabaseAssistant:0.1-Beta'
          
          //Kotlin ViewModelScope
          implementation group: 'androidx.lifecycle', name: 'lifecycle-viewmodel-ktx', version: '2.2.0-rc02'
}
```
## Getting Started
Create application class called `App.kt`and register it in your `AndroidManifest` file and implement below methods.
```
class App : Application() {

    companion object {
        var firebaseInstanceInitialized = false
    }

    override fun onCreate() {
        super.onCreate()

        if (!FirebaseApp.getApps(this).isEmpty() && !firebaseInstanceInitialized) {
            firebaseInstanceInitialized = true
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        }
    }

}
```
```
<application
        android:name=".App"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        ...
        
</application>
```
Create a data class. In this case i am creating a data class for user.
```
@Keep
data class User(
    @Exclude @set:Exclude @get:Exclude
    var userPushId: String? = null,

    @get:PropertyName(DATABASE_KEY.USER.firstName)
    @set:PropertyName(DATABASE_KEY.USER.firstName)
    var firstName: String = "",

    @get:PropertyName(DATABASE_KEY.USER.lastName)
    @set:PropertyName(DATABASE_KEY.USER.lastName)
    var lastName: String = "",

    @get:PropertyName(DATABASE_KEY.USER.email)
    @set:PropertyName(DATABASE_KEY.USER.email)
    var email: String = ""
)
```
Create a object to store the PropertyName. In this case i am creating a object called DATABASE_KEY
```
object DATABASE_KEY {

    object USER {
        const val firstName = "fN"
        const val lastName = "lN"
        const val email = "e"
    }
}
```
Create a Repository class and extend `DatabaseRepo<T>()` class (use your data class in case of T). Override `convertDatabaseSnapshot()` function and provide your data class, so that it convert dataSnapshot to your data class. If you want a singleton class then create a `companion object` and extend `DatabaseSingleton<T>(::T)` class. In this case i am creating a repo class for user.
```
class UserRepo : DatabaseRepo<User>() {

    override fun convertDatabaseSnapshot(dataSnapshot: DataSnapshot?): User? {
        return dataSnapshot?.getValue(User::class.java)
    }

    companion object : DatabaseSingleton<UserRepo>(::UserRepo) {
        fun userPath(path: String) = "users/$path"
        fun path() = "users"
    }
}
```
Create a `ViewModel` class and create a instance of your repo class. Implement some function as for your requirement. In this case i am creating a `UserViewModel` class.
```
class UserViewModel : ViewModel() {

    val userRepo by lazy {
        UserRepo.getInstance()
    }

    fun getUserFromDatabase(activity: AppCompatActivity) {
        val fName = activity.findViewById<TextView>(R.id.firstName)
        val lName = activity.findViewById<TextView>(R.id.lastName)
        val email = activity.findViewById<TextView>(R.id.email)
        
        userRepo.getFromDatabase(UserRepo.userPath("-LxjJXsJdbZiEjjh1A89")).observe(activity, Observer { user ->
            fName.text = user?.firstName
            lName.text = user?.lastName
            email.text = user?.email
        })
    }

    fun getUserFromCache(activity: AppCompatActivity) {
        viewModelScope.launch {
            val fName = activity.findViewById<TextView>(R.id.firstName)
            val lName = activity.findViewById<TextView>(R.id.lastName)
            val email = activity.findViewById<TextView>(R.id.email)
            
            val user = userRepo.getFromDatabaseCache(UserRepo.userPath("-LxjJXsJdbZiEjjh1A89"))
            
            fName.text = user?.firstName
            lName.text = user?.lastName
            email.text = user?.email
        }
    }

    fun pushUserToDatabase(user: User) {
        viewModelScope.launch {
            val key = userRepo.pushToDatabase(UserRepo.path(), user)
            user.userPushId = key
        }
    }
}
```
`userRepo.getFromDatabase()` return a `LiveData<T>` so that you will get realtime updates if anything changes happen in database. you need to observe it and update your UI when ever there is some change in database.

`userRepo.getFromDatabaseCache()` return `T` (in this case `User`) from your local database. It will not make any request to your Firebase database. 

`pushUserToDatabase(user: User)` push your data class to database an return the pushId. you can save pushId if you need it later.

For more detail refer to [UserViewModel.kt](https://github.com/Im-Mark42/FirebaseDatabaseAssistant/blob/master/app/src/main/java/com/firebase/mark42/databasedemo/UserViewModel.kt)

#### Initialize `ViewModel` class in your `activity` class and implement above methods.
```
val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
```
For more detail refer to [MainActivity.kt](https://github.com/Im-Mark42/FirebaseDatabaseAssistant/blob/master/app/src/main/java/com/firebase/mark42/databasedemo/MainActivity.kt)

### Work with list of data
Create a Repository class and extend `DatabaseRepo<T>()` and replace `T` with `List<User>` (use your data class in case of `User`). Override `convertDatabaseSnapshot()` and since `dataSnapshot` has list of data, you need to loop through the `dataSnapshot.children`. 
```
class UsersRepo : DatabaseRepo<List<User>>() {
    override fun convertDatabaseSnapshot(dataSnapshot: DataSnapshot?): List<User>? {
        val users = dataSnapshot?.children?.map {
            try {
                val user = it.getValue(User::class.java)
                user
            } catch (e: Exception) {
                null
            }
        }?.filterNotNull()

        return users
    }

    companion object : DatabaseSingleton<UsersRepo>(::UsersRepo) {
        fun path() = "users"
    }
}
```
Create a `ViewModel` class and create a instance of your repo class. Implement some function as for your requirement. In this case i am creating a `UsersViewModel` class.
```
class UsersViewModel : ViewModel() {

    val usersRepo by lazy {
        UsersRepo.getInstance()
    }

    fun getUsersFromDatabase(activity: AppCompatActivity) {
        usersRepo.getFromDatabase(UsersRepo.path()).observe(activity, Observer { users ->
            users?.forEach {
                //update UI
            }
        })
    }

    fun getUsersFromCache() {
        viewModelScope.launch {
            val users = usersRepo.getFromDatabaseCache(UsersRepo.path())
            users?.forEach {
                //update UI
            }
        }
    }
}
```
If you want to filter your data in database then use `query` method. `queryUsersFromDatabase()` function will return `LiveData<T>` while `queryUsersFromCache()` return data from Firebase cache.
```
fun queryUsersFromDatabase(activity: AppCompatActivity) {
        val query = FirebaseDatabase.getInstance().getReference("users").orderByKey().limitToLast(2)
        usersRepo.getQueryFromDatabase(query).observe(activity, Observer { users ->
            users?.forEach {
                //update UI
            }
        })
 }
 
 fun queryUsersFromCache() {
        viewModelScope.launch {
            val query = FirebaseDatabase.getInstance().getReference("users").orderByKey().limitToLast(2)
            val users = usersRepo.getQueryFromDatabaseCache(query)
            users?.forEach {
                //update UI
            }
        }
 }
 ```
#### Initialize `ViewModel` class in your `activity` class and implement above method
```
val usersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UsersViewModel::class.java)
usersViewModel.getUsersFromCache()
```

#### Thank you for using FirebaseDatabaseAssistant.
