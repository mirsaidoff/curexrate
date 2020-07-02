package uz.mirsaidoff.curexrate.data.model

import com.google.gson.annotations.SerializedName

class Currency(
    @SerializedName("date")
    val date: String
) : BaseModel<Map<String, Double>, Rate>() {
    override fun getRates(): List<Rate> = rates?.map { Rate(title = it.key, rate = it.value) } ?: listOf()
}