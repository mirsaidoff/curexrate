package uz.mirsaidoff.curexrate.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.mirsaidoff.curexrate.data.model.WeeklyRate
import uz.mirsaidoff.curexrate.data.remote.ApiService
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DetailsViewModel(private val apiService: ApiService) : ViewModel() {

    val resultLive: MutableLiveData<List<WeeklyRate>> = MutableLiveData()
    val progressLive: MutableLiveData<Boolean> = MutableLiveData()
    val errorLive: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("SimpleDateFormat")
    fun getLastSevenDayUpdate(symbols: String?) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()


        val endDate = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val startDate = dateFormat.format(calendar.time)

        symbols?.apply {
            viewModelScope.launch {
                progressLive.value = true
                try {
                    val lastSevenDaysResult = apiService.getLastSevenDaysResult(
                        startAt = startDate,
                        endAt = endDate,
                        symbols = this@apply
                    )
                    if (lastSevenDaysResult.isSuccessful) {
                        val list = lastSevenDaysResult.body()?.getRates() ?: listOf()
                        resultLive.value = list
                    } else throw Exception(lastSevenDaysResult.message())
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    var message = ex.message
                    if (message.isNullOrEmpty()) message =
                        "No exchange rate data is available for the selected currency."
                    errorLive.value = message
                } finally {
                    progressLive.value = false
                }
            }
        }
    }
}