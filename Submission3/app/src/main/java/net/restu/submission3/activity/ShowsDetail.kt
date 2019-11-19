package net.restu.submission3.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_movies.*
import net.restu.submission3.R
import net.restu.submission3.model.ShowsItem

class ShowsDetail : AppCompatActivity() {

    companion object{
        const val EXTRA_SHOW = "w"
    }

    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shows_detail)
        progressBar = findViewById(R.id.progressBar)

        progressBar.visibility = View.VISIBLE
        val handler = Handler()
        Thread(Runnable {
            try {
                Thread.sleep(5000)
            } catch (e: Exception) {

            }
            handler.post {
                val show = intent.getParcelableArrayListExtra<ShowsItem>(ShowsDetail.EXTRA_SHOW)
                tv_title.text = show[0].title
                tv_overview.text = show[0].overview
                tv_rating.text = show[0].vote_average
                tv_release.text = show[0].release_date
                tv_language.text = show[0].language
                val imgPoster: ImageView = findViewById(R.id.img_poster)
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/${show[0].backdrop}")
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster)
                progressBar.visibility = View.INVISIBLE
            }
        }).start()
    }
}
