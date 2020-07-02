package uz.mirsaidoff.curexrate.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
class Rate(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val rate: Double
)
