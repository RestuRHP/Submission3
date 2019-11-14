package net.restu.submission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import net.restu.submission3.item.MoviesItem
import org.json.JSONObject

class MoviesViewModel:ViewModel() {
    companion object {
        private const val API_KEY = "0bc4d12e12cd1880a3493cafe4cdb8e0"
    }
    val listMovies = MutableLiveData<ArrayList<MoviesItem>>()

    internal fun setMovies(movie: String){
        val client = AsyncHttpClient()
        val listItem = ArrayList<MoviesItem>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("list")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val movieItems = MoviesItem()
                        movieItems.title = movie.getString("title")
                        movieItems.overview=movie.getString("overview")
                        movieItems.vote_average=movie.getString("vote_average")
                        movieItems.release_date=movie.getString("release_date")
                        movieItems.poster=movie.getString("poster_path")
                        listItem.add(movieItems)
                    }
                    listMovies.postValue(listItem)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    internal fun getMovies():LiveData<ArrayList<MoviesItem>>{
        return listMovies
    }
}