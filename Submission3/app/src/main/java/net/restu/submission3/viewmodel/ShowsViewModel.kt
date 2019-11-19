package net.restu.submission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import net.restu.submission3.model.ShowsItem
import org.json.JSONObject

class ShowsViewModel : ViewModel() {
    companion object {
        private const val ApiKey = "---"
    }

    val listShows = MutableLiveData<ArrayList<ShowsItem>>()

    internal fun setShows() {
        val client = AsyncHttpClient()
        val listItem = ArrayList<ShowsItem>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$ApiKey&language=en-US"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val shows = list.getJSONObject(i)
                        val showsItem = ShowsItem(
                            title = shows.getString("original_name"),
                            overview = shows.getString("overview"),
                            vote_average = shows.getString("vote_average"),
                            release_date = shows.getString("first_air_date"),
                            poster = shows.getString("poster_path"),
                            language = shows.getString("original_language"),
                            backdrop = shows.getString("backdrop_path")
                        )

                        listItem.add(showsItem)
                    }
                    listShows.postValue(listItem)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    internal fun getShows(): LiveData<ArrayList<ShowsItem>> {
        return listShows
    }
}