package co.saputra.alfagifttask.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.putrash.common.Event

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> get() = _isLoading

    protected fun showLoading() {
        _isLoading.postValue(Event(true))
    }

    protected fun hideLoading() {
        _isLoading.postValue(Event(false))
    }

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> get() = _errorMessage

    protected fun showError(message: String) {
        _errorMessage.postValue(Event(message))
    }
}