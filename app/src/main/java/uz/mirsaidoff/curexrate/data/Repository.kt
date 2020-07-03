package uz.mirsaidoff.curexrate.data

import android.content.SharedPreferences
import uz.mirsaidoff.curexrate.data.local.MainDB
import uz.mirsaidoff.curexrate.data.model.Rate
import uz.mirsaidoff.curexrate.data.remote.ApiService

class Repository(
    private val apiService: ApiService,
    private val pref: SharedPreferences,
    db: MainDB
) {

    private val dao = db.getDao()

    suspend fun updateRates(refresh: Boolean) {
        val lastUpdateTime = pref.getLong("last_update_millis", 0)
        val diffSec = (System.currentTimeMillis() - lastUpdateTime) / 1000     //600 sec
        if (!refresh && lastUpdateTime != 0L && diffSec < 600) {
            return
        }

        val updatedRates = getUpdatedRates()
        updateDBValues(updatedRates)
    }

    fun getAllSavedRates() = dao.getAll()

    private suspend fun getUpdatedRates(): List<Rate> {
        val latestRates = apiService.getLatestRates()
        if (latestRates.isSuccessful) {
            pref.edit()
                .putLong("last_update_millis", System.currentTimeMillis())
                .apply()
            return latestRates.body()?.getRates() ?: listOf()
        } else throw Exception("Cannot get latest updates from server. Please try again!")
    }

    private suspend fun updateDBValues(updatedRates: List<Rate>) {
        dao.removeAll()
        dao.add(updatedRates)
    }

}