package net.restu.submission3.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import net.restu.submission3.R
import net.restu.submission3.adapter.ShowsAdapter
import net.restu.submission3.viewmodel.ShowsViewModel

/**
 * A simple [Fragment] subclass.
 */
class TvShowsFragment : Fragment() {

    private lateinit var adapter: ShowsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var showsViewModel: ShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tv_shows,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<View>(R.id.rv_tv_movies)as RecyclerView

        adapter = ShowsAdapter()
        adapter.notifyDataSetChanged()
        recyclerView.layoutManager=LinearLayoutManager(this.context)
        recyclerView.adapter=adapter
        progressBar = view.findViewById(R.id.progressBar)

        showsViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(ShowsViewModel::class.java)
        showsViewModel.getShows().observe(this, Observer { showsItem ->
            if(showsItem != null){
                adapter.setData(showsItem)
            }
            showLoading(false)
        })
        showsViewModel.setShows()
        showLoading(true)
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
