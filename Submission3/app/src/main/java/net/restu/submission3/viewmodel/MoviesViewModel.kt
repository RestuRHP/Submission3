package net.restu.submission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import net.restu.submission3.model.MoviesItem
import org.json.JSONObject

class MoviesViewModel : ViewModel() {
    companion object {
        private const val ApiKey = "---"
    }

    val listMovies = MutableLiveData<ArrayList<MoviesItem>>()

    internal fun setMovies() {
        val client = AsyncHttpClient()
        val listItem = ArrayList<MoviesItem>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$ApiKey&language=en-US"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int,headers: Array<Header>,responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val movieItems = MoviesItem(
                            title = movie.getString("title"),
                            overview = movie.getString("overview"),
                            vote_average = movie.getString("vote_average"),
                            release_date = movie.getString("release_date"),
                            poster = movie.getString("poster_path"),
                            language = movie.getString("original_language"),
                            backdrop = movie.getString("backdrop_path")
                        )
                        listItem.add(movieItems)                    }
                    listMovies.postValue(listItem)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int,headers: Array<Header>,responseBody: ByteArray,error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    internal fun getMovies(): LiveData<ArrayList<MoviesItem>> {
        return listMovies
    }
}