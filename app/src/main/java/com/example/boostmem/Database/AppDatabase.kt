package com.example.boostmem.Database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.boostmem.Database.Dao.CardDao
import com.example.boostmem.Database.Dao.CategoryDao
import com.example.boostmem.Database.Dao.DeckDao
import com.example.boostmem.Database.Dao.NotiDao
import com.example.boostmem.Database.Models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//Card::class, NotificationModel::class, Statistic::class
@Database(
    entities = [Deck::class,Category::class,Card::class,NotificationModel::class],
    version = 12
)
@TypeConverters(Converter::class)

abstract class AppDatabase: RoomDatabase() {
    abstract fun deckDao(): DeckDao
    abstract fun cateDao(): CategoryDao
    abstract fun cardDao(): CardDao
    abstract fun notiDao(): NotiDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bm_db"
                )

                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let {database->
                    scope.launch(Dispatchers.IO) {

                        var cateDao = database.cateDao()
                        if (cateDao.getDataCount() == 0){
                            cateDao.insert(Category(0,"Education"))
                        }
                    }
                }
            }
        }

    }
}