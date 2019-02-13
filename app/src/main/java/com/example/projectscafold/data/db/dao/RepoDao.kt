package com.example.projectscafold.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.projectscafold.data.model.room.Repo

/**
 * Created by Sadman Sarar on 10/15/18.
 */
@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>)

    @Query("SELECT * FROM Repo "
            + "WHERE lower(owner_login) = lower(:owner)"
            + "ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): LiveData<List<Repo>>

}