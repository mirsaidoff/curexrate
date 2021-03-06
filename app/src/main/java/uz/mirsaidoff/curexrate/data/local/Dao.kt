package uz.mirsaidoff.curexrate.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uz.mirsaidoff.curexrate.data.model.Rate

@Dao
interface Dao {

    @Query(" select * from rates")
    fun getAll(): LiveData<List<Rate>>

    @Insert
    suspend fun add(rates: List<Rate>)

    @Query("delete from rates")
    suspend fun removeAll()
}