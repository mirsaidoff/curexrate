package uz.mirsaidoff.curexrate.data.model

import com.google.gson.annotations.SerializedName

abstract class BaseModel<T, V>(
    @SerializedName("rates")
    val rates: T? = null,
    @SerializedName("base")
    val base: String? = null
) {
    abstract fun getRates(): List<V>
}