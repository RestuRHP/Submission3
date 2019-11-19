package net.restu.submission3.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_movies.*
import net.restu.submission3.R
import net.restu.submission3.model.MoviesItem

class MoviesDetail : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movies)

        progressBar = findViewById(R.id.progressBar)

        progressBar.visibility = View.VISIBLE
        val handler = Handler()
        Thread(Runnable {
            try {
                Thread.sleep(5000)
            } catch (e: Exception) {

            }
            handler.post {
                val movie = intent.getParcelableArrayListExtra<MoviesItem>(EXTRA_MOVIE)
                tv_title.text = movie[0].title
                tv_overview.text = movie[0].overview
                tv_rating.text = movie[0].vote_average
                tv_release.text = movie[0].release_date
                tv_language.text = movie[0].language
                val imgPoster: ImageView = findViewById(R.id.img_poster)
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/${movie[0].backdrop}")
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster)
                progressBar.visibility = View.INVISIBLE
            }
        }).start()

    }
}
