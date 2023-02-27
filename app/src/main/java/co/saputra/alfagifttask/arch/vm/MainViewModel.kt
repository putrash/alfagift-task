package co.saputra.alfagifttask.arch.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.saputra.alfagifttask.arch.paging.PlayingNowMoviePagingSource
import co.saputra.alfagifttask.arch.paging.ReviewPagingSource
import co.saputra.alfagifttask.arch.paging.TopRatedMoviePagingSource
import co.saputra.alfagifttask.base.BaseViewModel
import com.putrash.data.Api
import com.putrash.data.BuildConfig
import com.putrash.data.model.Movie
import com.putrash.data.model.Review
import com.putrash.data.model.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val api: Api) : BaseViewModel() {

    private val _moviesPlayingNow = MutableLiveData<ArrayList<Movie>>()
    val moviesPlayingNow : LiveData<ArrayList<Movie>> get() = _moviesPlayingNow

    private val _moviesTopRated = MutableLiveData<ArrayList<Movie>>()
    val moviesTopRated : LiveData<ArrayList<Movie>> get() = _moviesTopRated

    private val _reviewsMovie = MutableLiveData<ArrayList<Review>>()
    val reviewsMovie : LiveData<ArrayList<Review>> get() = _reviewsMovie

    private val _videosMovie = MutableLiveData<ArrayList<Video>>()
    val videosMovie : LiveData<ArrayList<Video>> get() = _videosMovie

    private val reviewId = MutableLiveData<Int>()

    private lateinit var _reviewsFlow: Flow<PagingData<Review>>
    val reviewsFlow: Flow<PagingData<Review>> get() = _reviewsFlow

    private lateinit var _moviesPlayingNowFlow: Flow<PagingData<Movie>>
    val moviesPlayingNowFlow: Flow<PagingData<Movie>> get() = _moviesPlayingNowFlow

    private lateinit var _moviesTopRatedFlow: Flow<PagingData<Movie>>
    val moviesTopRatedFlow: Flow<PagingData<Movie>> get() = _moviesTopRatedFlow

    init {
        getAllReviews()
        getAllPlayingNowMovies()
        getAllTopRatedMovies()
    }

    fun setReviewId(id: Int) {
        reviewId.value = id
    }

    private fun getAllReviews() = launchPagingAsync({
        Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { ReviewPagingSource(api, reviewId.value!!) }
        ).flow.cachedIn(viewModelScope)
    }, {
        _reviewsFlow = it
    })

    private fun getAllPlayingNowMovies() = launchPagingAsync({
        Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PlayingNowMoviePagingSource(api) }
        ).flow.cachedIn(viewModelScope)
    }, {
        _moviesPlayingNowFlow = it
    })

    private fun getAllTopRatedMovies() = launchPagingAsync({
        Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { TopRatedMoviePagingSource(api) }
        ).flow.cachedIn(viewModelScope)
    }, {
        _moviesTopRatedFlow = it
    })

    fun getPlayingNowMovies(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                showLoading()
                val data = arrayListOf<Movie>()
                val response = api.getNowPlayingMovies(BuildConfig.API_KEY, page)
                if (response.isSuccessful) {
                    data.addAll(response.body()!!.results)
                }
                _moviesPlayingNow.postValue(data)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                showError(throwable.message.toString())
            } finally {
                hideLoading()
            }
        }
    }

    fun getTopRatedMovies(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                showLoading()
                val data = arrayListOf<Movie>()
                val response = api.getTopRatedMovies(BuildConfig.API_KEY, page)
                if (response.isSuccessful) {
                    data.addAll(response.body()!!.results)
                }
                _moviesTopRated.postValue(data)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                showError(throwable.message.toString())
            } finally {
                hideLoading()
            }
        }
    }

    fun getMovieReviews(id: Int, page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                showLoading()
                val data = arrayListOf<Review>()
                val response = api.getMovieReviews(id, BuildConfig.API_KEY, page)
                if (response.isSuccessful) {
                    data.addAll(response.body()!!.results)
                }
                _reviewsMovie.postValue(data)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                showError(throwable.message.toString())
            } finally {
                hideLoading()
            }
        }
    }

    fun getMovieVideo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                showLoading()
                val data = arrayListOf<Video>()
                val response = api.getMovieVideos(id, BuildConfig.API_KEY)
                if (response.isSuccessful) {
                    data.addAll(response.body()!!.results)
                }
                _videosMovie.postValue(data)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                showError(throwable.message.toString())
            } finally {
                hideLoading()
            }
        }
    }
}