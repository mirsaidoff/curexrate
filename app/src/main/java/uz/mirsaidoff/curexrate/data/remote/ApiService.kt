package uz.mirsaidoff.curexrate.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.mirsaidoff.curexrate.data.model.Currency
import uz.mirsaidoff.curexrate.data.model.WeeklyCurrency

interface ApiService {

    @GET("latest")
    suspend fun getLatestRates(@Query("base") base: String = "USD"): Response<Currency>

    @GET("history")
    suspend fun getLastSevenDaysResult(
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String,
        @Query("base") base: String = "USD",
        @Query("symbols") symbols: String
    ): Response<WeeklyCurrency>
}