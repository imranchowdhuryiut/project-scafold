package com.example.projectscafold.view.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projectscafold.R
import com.example.projectscafold.data.model.room.Repo
import com.example.projectscafold.data.api.network.Status
import com.example.projectscafold.viewmodel.implimentation.IRepoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


/***
 * Activity that displays a list of Repos
 */
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repoViewModel: IRepoViewModel

    private val USER_STATE_KEY = "UserName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repoViewModel = ViewModelProviders.of(this, viewModelFactory).get(IRepoViewModel::class.java)

        val reposAdapter = ReposAdapter(this, ArrayList())
        recyclerViewRepos.adapter = reposAdapter
        recyclerViewRepos.layoutManager = LinearLayoutManager(this)

        //search click listener
        setupSearchListener(reposAdapter, savedInstanceState)
    }


    private fun setupSearchListener(reposAdapter: ReposAdapter, savedInstanceState: Bundle?) {
        buttonSearch.setOnClickListener {
            repoViewModel.setQuery(editTextUser.text.toString(), reposAdapter.itemCount == 0)
        }
        val currentUserName = savedInstanceState?.get(USER_STATE_KEY) as String?
        repoViewModel.setQuery(currentUserName, reposAdapter.itemCount == 0)
        repoViewModel.results.observe(this, Observer {
            it?.let {
                textViewError.visibility = View.GONE
                progressBar.visibility = View.GONE
                reposAdapter.updateDataSet(ArrayList())
                when (it.status) {
                    Status.SUCCESS -> {
                        recyclerViewRepos.visibility = View.VISIBLE
                        reposAdapter.updateDataSet(it.data as ArrayList<Repo>)
                    }
                    Status.ERROR   -> {
                        textViewError.visibility = View.VISIBLE
                        textViewError.text = it.message
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    //save state
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //get the state from viewModel
        outState?.putString(USER_STATE_KEY, repoViewModel.currentRepoUser)
    }

    class ReposAdapter(val context: Context, var repos: ArrayList<Repo>) : RecyclerView.Adapter<ReposAdapter.RepoItemViewHolder>() {

        override fun getItemCount(): Int {
            return repos.size
        }

        fun updateDataSet(data: ArrayList<Repo>) {
            repos = data
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RepoItemViewHolder {
            val textView = TextView(context)

            return RepoItemViewHolder(textView)
        }

        override fun onBindViewHolder(p0: RepoItemViewHolder, p1: Int) {
            p0.bind(repos[p1])
        }

        class RepoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(repo: Repo) = with(itemView) {
                (itemView as TextView).text = repo.name
            }
        }
    }
}
