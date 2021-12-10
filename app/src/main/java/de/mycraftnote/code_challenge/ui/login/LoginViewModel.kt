package de.mycraftnote.code_challenge.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.mycraftnote.code_challenge.R
import de.mycraftnote.code_challenge.data.LoginRepository
import de.mycraftnote.code_challenge.data.Result
import de.mycraftnote.code_challenge.data.model.LoggedInUser

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val loginFormState = MutableLiveData<LoginFormState>()
    private val loginResult = MutableLiveData<LoginResult>()
    fun getLoginFormState(): LiveData<LoginFormState> {
        return loginFormState
    }

    fun getLoginResult(): LiveData<LoginResult> {
        return loginResult
    }

    fun login(username: String?, password: String?) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)
        if (result is Result.Success) {
            loginResult.setValue(LoginResult(success = LoggedInUserView(result.data.displayName)))
        } else {
            loginResult.setValue(LoginResult(error = R.string.login_failed))
        }
    }

    fun loginDataChanged(username: String?, password: String?) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(LoginFormState(usernameError = R.string.invalid_username))
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(LoginFormState(passwordError = R.string.invalid_password))
        } else {
            loginFormState.setValue(LoginFormState(isDataValid = true))
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(name: String?): Boolean {
        val username = name ?: return false
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.trim().isNotEmpty()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return password != null && password.trim().length > 5
    }
}