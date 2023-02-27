package co.saputra.alfagifttask.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putrash.common.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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

    inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}