package co.saputra.alfagifttask.arch.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.putrash.data.Api
import com.putrash.data.BuildConfig
import com.putrash.data.model.Review

class ReviewPagingSource(private val api: Api, private val id: Int) : PagingSource<Int, Review>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val pageNumber = params.key ?: 1
        return try {
            val response = api.getMovieReviews(id, BuildConfig.API_KEY, pageNumber)
            val pagedResponse = response.body()
            val data = pagedResponse?.results

            var nextPageNumber: Int? = null
            if ((pagedResponse?.page ?: 1) <= (pagedResponse?.totalPages ?: 1)) {
                nextPageNumber = pagedResponse?.page?.plus(1)
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}