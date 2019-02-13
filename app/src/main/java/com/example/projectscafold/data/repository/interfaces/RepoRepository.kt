package com.example.projectscafold.data.repository.interfaces

import android.arch.lifecycle.LiveData
import com.example.projectscafold.data.api.network.Resource
import com.example.projectscafold.data.model.room.Repo

/**
 * Created by Sadman Sarar on 10/15/18.
 */
interface RepoRepository {
    fun loadRepos(owner: String): LiveData<Resource<List<Repo>>>
}