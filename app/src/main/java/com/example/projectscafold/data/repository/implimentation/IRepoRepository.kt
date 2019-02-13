package com.example.projectscafold.data.repository.implimentation

import android.arch.lifecycle.LiveData
import android.support.annotation.Nullable
import com.example.projectscafold.data.api.RateLimiter
import com.example.projectscafold.data.api.network.ApiResponse
import com.example.projectscafold.data.api.network.NetworkBoundResource
import com.example.projectscafold.data.api.network.Resource
import com.example.projectscafold.data.api.retrofit.WebService
import com.example.projectscafold.data.db.dao.RepoDao
import com.example.projectscafold.data.model.room.Repo
import com.example.projectscafold.data.repository.interfaces.RepoRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sadman Sarar on 9/10/17.
 * Repository class - uses NetworkBoundResource to load data from API
 */
@Singleton
class IRepoRepository
@Inject constructor(val repoDao: RepoDao, val webService: WebService) : RepoRepository {
    val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    override fun loadRepos(owner: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, List<Repo>>() {
            override fun saveCallResult(item: List<Repo>) {
                repoDao.insertRepos(item)
            }

            override fun shouldFetch(@Nullable data: List<Repo>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(owner)
            }

            override fun loadFromDb(): LiveData<List<Repo>> {
                return repoDao.loadRepositories(owner)
            }

            override fun createCall(): LiveData<ApiResponse<List<Repo>>> {
                return webService.getRepos(owner)
            }

            override fun onFetchFailed() {
                repoListRateLimit.reset(owner)
            }
        }.asLiveData()
    }

}