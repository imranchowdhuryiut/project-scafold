package com.example.projectscafold.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.projectscafold.data.db.dao.RepoDao
import com.example.projectscafold.data.model.room.Repo

/**
 * Created by Sadman Sarar on 9/9/17.
 * Database Class including the Dao
 */
@Database(entities = [(Repo::class)], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}

