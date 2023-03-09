package co.saputra.alfagifttask.arch.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.putrash.data.Api
import com.putrash.data.BuildConfig
import com.putrash.data.model.Genre
import com.putrash.data.model.Movie

class PlayingNowMoviePagingSource(private val api: Api, private val genresMovie: ArrayList<Genre>?) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageNumber = params.key ?: 1
        return try {
            val response = api.getNowPlayingMovies(BuildConfig.API_KEY, pageNumber)
            val pagedResponse = response.body()
            val data = arrayListOf<Movie>()

            response.body()!!.results.map { item ->
                data.add(Movie(
                    id = item.id,
                    posterPath = item.posterPath,
                    adult = item.adult,
                    overview = item.overview,
                    releaseDate = item.releaseDate,
                    genreIds = item.genreIds,
                    originalTitle = item.originalTitle,
                    title = item.title,
                    backdropPath = item.backdropPath,
                    video = item.video,
                    voteAverage = item.voteAverage,
                    genres = genresMovie?.filter { item.genreIds.contains(it.id) }
                ))
            }

            var nextPageNumber: Int? = null
            if ((pagedResponse?.page ?: 1) <= (pagedResponse?.totalPages ?: 1)) {
                nextPageNumber = pagedResponse?.page?.plus(1)
            }

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}