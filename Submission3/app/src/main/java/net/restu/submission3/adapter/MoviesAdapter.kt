package net.restu.submission3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import net.restu.submission3.model.MoviesItem
import net.restu.submission3.R
import net.restu.submission3.activity.MoviesDetail

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private val mData = ArrayList<MoviesItem>()
    fun setData(item: ArrayList<MoviesItem>){
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MoviesViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie, viewGroup,false)
        return MoviesViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(moviesViewHolder: MoviesViewHolder, position: Int) {
        moviesViewHolder.bind(mData[position])
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(moviesItem: MoviesItem){
            with(itemView){
                tv_title.text = moviesItem.title
                tv_storyline.text=moviesItem.overview
                tv_rating.text=moviesItem.vote_average
                tv_time.text=moviesItem.release_date
                tv_language.text=moviesItem.language

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/${moviesItem.poster}")
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(img_poster)

                itemView.setOnClickListener{
                    val intent = Intent(itemView.context,MoviesDetail::class.java)
                    val movies = ArrayList<MoviesItem>()
                    val arMovie = MoviesItem(
                        title = moviesItem.title,
                        overview = moviesItem.overview,
                        vote_average = moviesItem.vote_average,
                        release_date = moviesItem.release_date,
                        language = moviesItem.language,
                        poster = moviesItem.poster,
                        backdrop = moviesItem.backdrop
                    )
                    movies.add(arMovie)
                    intent.putParcelableArrayListExtra(MoviesDetail.EXTRA_MOVIE,movies)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}