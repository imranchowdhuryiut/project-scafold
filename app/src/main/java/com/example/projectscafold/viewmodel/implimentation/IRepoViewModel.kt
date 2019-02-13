package com.example.projectscafold.viewmodel.implimentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.projectscafold.data.api.network.AbsentLiveData
import com.example.projectscafold.data.api.network.Resource
import com.example.projectscafold.data.model.room.Repo
import com.example.projectscafold.data.repository.interfaces.RepoRepository
import com.example.projectscafold.viewmodel.interfaces.RepoViewModel
import java.util.*
import javax.inject.Inject


/**
 * Created by Sadman Sarar on 9/10/17.
 * ViewModel for the Repos
 */
class IRepoViewModel
@Inject constructor(repository: RepoRepository) : ViewModel(), RepoViewModel {
    var currentRepoUser: String? = null

    //live data of list of Repos, called results
    val results: LiveData<Resource<List<Repo>>>
    private val query: MutableLiveData<String> = MutableLiveData()

    init {
        results = Transformations.switchMap(query) {
            when {
                it == null || it.length == 1 -> AbsentLiveData.create()
                else                         -> repository.loadRepos(it)
            }
        }
    }

    override fun setQuery(originalInput: String?, force: Boolean) {
        if (originalInput == null) return
        val input = originalInput.toLowerCase(Locale.getDefault()).trim { it <= ' ' }
        if (input == query.value && ! force) {
            return
        }
        query.value = input
    }

}