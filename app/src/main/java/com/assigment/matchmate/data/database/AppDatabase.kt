package com.assigment.matchmate.data.database

import android.content.Context
import androidx.room.*

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "matchmate_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// Used to convert custom enum types
class Converters {
    @TypeConverter
    fun fromUserStatus(status: UserStatus): String = status.name

    @TypeConverter
    fun toUserStatus(status: String): UserStatus = UserStatus.valueOf(status)
}
