package net.restu.submission3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_shows.view.*
import net.restu.submission3.R
import net.restu.submission3.activity.ShowsDetail
import net.restu.submission3.model.ShowsItem

class ShowsAdapter : RecyclerView.Adapter<ShowsAdapter.ShowsViewHolder>(){
    private val mData = ArrayList<ShowsItem>()
    fun setData(item: ArrayList<ShowsItem>){
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_shows,parent,false)
        return ShowsViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(showsViewHolder: ShowsViewHolder, position: Int) {
        showsViewHolder.bind(mData[position])
    }

    inner class ShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(showsItem: ShowsItem){
            with(itemView){
                tv_title.text = showsItem.title
                tv_storyline.text=showsItem.overview
                tv_rating.text=showsItem.vote_average
                tv_time.text=showsItem.release_date
                tv_language.text=showsItem.language

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w185/${showsItem.poster}")
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(img_poster)
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context,ShowsDetail::class.java)
                    val shows = ArrayList<ShowsItem>()
                    val arShows = ShowsItem(
                        title = showsItem.title,
                        overview = showsItem.overview,
                        vote_average = showsItem.vote_average,
                        release_date = showsItem.release_date,
                        language = showsItem.language,
                        poster = showsItem.poster,
                        backdrop = showsItem.backdrop
                    )
                    shows.add(arShows)
                    intent.putParcelableArrayListExtra(ShowsDetail.EXTRA_SHOW,shows)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}