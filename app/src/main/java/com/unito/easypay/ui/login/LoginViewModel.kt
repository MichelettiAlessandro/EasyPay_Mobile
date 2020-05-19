package com.unito.easypay.ui.login

import android.telecom.Call
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.android.volley.Response
import com.unito.easypay.data.LoginRepository
import com.unito.easypay.data.Result

import com.unito.easypay.R
import com.unito.easypay.data.Mappable
import com.unito.easypay.data.model.LoggedInUser
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String, loginActivity: LoginActivity) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password, loginActivity)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(token = result.data.token))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
