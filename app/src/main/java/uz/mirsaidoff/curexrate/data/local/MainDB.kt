package uz.mirsaidoff.curexrate.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.mirsaidoff.curexrate.data.model.Rate
import uz.mirsaidoff.curexrate.data.local.MainDB.Companion.DB_VERSION

@Database(entities = [Rate::class], version = DB_VERSION)
abstract class MainDB : RoomDatabase() {
    companion object {
        const val DB_VERSION = 1

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, MainDB::class.java, "main_db")
                .build()
    }

    abstract fun getDao(): Dao
}