package uz.mirsaidoff.curexrate.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.mirsaidoff.curexrate.data.model.Rate
import uz.mirsaidoff.curexrate.data.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    val resultLive by lazy { repository.getAllSavedRates() }
    val progressLive by lazy { MutableLiveData<Boolean>() }
    val errorLive by lazy { MutableLiveData<String>() }

    fun getLatestExchangeRates(refresh: Boolean) {
        viewModelScope.launch {
            progressLive.value = true
            try {
                repository.updateRates(refresh)
            } catch (ex: Exception) {
                ex.printStackTrace()
                var message = ex.message
                if (message.isNullOrEmpty()) message =
                    "Oops, something went wrong. Please, try again!"
                errorLive.value = message
            } finally {
                progressLive.value = false
            }
        }
    }
}