package de.mycraftnote.code_challenge.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import de.mycraftnote.code_challenge.R
import de.mycraftnote.code_challenge.ui.task.TaskActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory()).get(LoginViewModel::class.java)
        loginViewModel.getLoginFormState().observe(this, { loginState: LoginFormState? ->
            val loginFormState = loginState ?: return@observe
            login.isEnabled = loginFormState.isDataValid
            if (loginFormState.usernameError != null) {
                username.error = getString(loginFormState.usernameError)
            }
            if (loginFormState.passwordError != null) {
                password.error = getString(loginFormState.passwordError)
            }
        })

        loginViewModel.getLoginResult().observe(this, { result ->
            val loginResult = result ?: return@observe

            loading.visibility = View.GONE
            if (loginResult.error != null || loginResult.success == null) {
                showLoginFailed(loginResult.error)
            }
            loginResult.success?.let {
                updateUiWithUser(it)
                finish()
            }
        })

        val afterTextChangedListener: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(username.text.toString(), password.text.toString())
            }
        }
        username.addTextChangedListener(afterTextChangedListener)
        password.addTextChangedListener(afterTextChangedListener)

        password.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
            false
        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            loginViewModel.login(username.text.toString(), password.text.toString())
        }

        username.setText(USER_CREDENTIALS)
        password.setText(USER_CREDENTIALS)
    }

    private fun updateUiWithUser(model: LoggedInUserView?) {
        val welcome = getString(R.string.welcome) + model!!.displayName
        Toast.makeText(applicationContext, welcome, Toast.LENGTH_LONG).show()
        startActivity(Intent(this, TaskActivity::class.java))
    }

    private fun showLoginFailed(@StringRes errorString: Int?) {
        Toast.makeText(applicationContext, errorString!!, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val USER_CREDENTIALS = "testuser"
    }
}