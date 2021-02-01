package com.unito.easypay.data

import android.util.Log
import com.unito.easypay.data.model.LoggedInUser
import com.unito.easypay.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.sql.ResultSet

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

/**
 * hansolo@test.it
 * Prova123
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: Result<LoggedInUser>? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    /*
    fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }
        return result
    }
     */

    suspend fun login(username: String, password: String) : Result<LoggedInUser> =
            withContext(Dispatchers.IO) {
            // Blocking network request code
                val data = async { dataSource.login(username, password) }
                try{
                    val result = data.await()
                    setLoggedInUser(result)
                    return@withContext result
                } catch(e: Exception) {
                    return@withContext Result.Error(Exception("Error data request"))
                }
        }

    private fun setLoggedInUser(loggedInUser: Result<LoggedInUser>) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

}
