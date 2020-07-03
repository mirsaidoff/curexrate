package uz.mirsaidoff.curexrate.data.model

import com.google.gson.annotations.SerializedName

class WeeklyCurrency(
    @SerializedName("start_at")
    val startAt: String,
    @SerializedName("end_at")
    val endAt: String
) : BaseModel<Map<String, Map<String, Double>>, WeeklyRate>() {
    override fun getRates(): List<WeeklyRate> =
        rates?.map {
            WeeklyRate(
                it.key,
                it.value.map { rate -> Rate(title = rate.key, rate = rate.value) }[0]
            )
        }?.sortedBy { it.date } ?: listOf()
}