package my.test.movieexpert.loginscreen.model.form

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import my.test.movieexpert.utilities.InputValidators
import javax.inject.Inject

data class ForgotPasswordForm @Inject constructor(
    private val inputValidators: InputValidators,
    val email: MutableLiveData<String>
) {
    val emailError = MediatorLiveData<String?>().apply {
        value = ""
        addSource(email) {
            value = inputValidators.validateEmail(it)
        }
    }
}