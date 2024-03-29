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
import net.restu.submission3.adapter.MoviesAdapter
import net.restu.submission3.model.MoviesItem
import net.restu.submission3.viewmodel.MoviesViewModel

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {

    private lateinit var adapter: MoviesAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<View>(R.id.rv_movies) as RecyclerView
        adapter = MoviesAdapter()
        adapter.notifyDataSetChanged()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        progressBar = view.findViewById(R.id.progressBar)

        moviesViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MoviesViewModel::class.java)
        moviesViewModel.getMovies().observe(this, Observer { moviesItem ->
            if (moviesItem != null) {
                adapter.setData(moviesItem)
            }
            showLoading(false)
        })
        moviesViewModel.setMovies()
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
