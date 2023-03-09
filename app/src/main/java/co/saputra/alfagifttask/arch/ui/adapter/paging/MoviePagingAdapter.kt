package co.saputra.alfagifttask.arch.ui.adapter.paging

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import co.saputra.alfagifttask.R
import co.saputra.alfagifttask.base.BasePagingAdapter
import co.saputra.alfagifttask.base.BaseViewHolder
import co.saputra.alfagifttask.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.putrash.common.Constant
import com.putrash.common.convertDate
import com.putrash.data.BuildConfig
import com.putrash.data.model.Movie

class MoviePagingAdapter(
    layoutInflater: LayoutInflater,
    private val context: Context,
    private val onClickListener: (Movie) -> Unit
) : BasePagingAdapter<Movie, ItemMovieBinding, MoviePagingAdapter.ViewHolder>(
    layoutInflater,
    ItemMovieBinding::inflate,
    MovieDiffcallback
) {

    override fun itemViewHolder(viewBinding: ItemMovieBinding, viewType: Int): ViewHolder {
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: Movie()
        holder.onBind(item)
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) : BaseViewHolder<Movie>(binding.root) {
        override fun onBind(item: Movie) {
            binding.apply {
                root.setOnClickListener {
                    onClickListener(item)
                }
                tvTitle.text = item.title
                tvGenre.text = item.genres?.map { it.name }?.joinToString(", ")
                tvContent.text = item.overview
                Glide.with(context)
                    .load(BuildConfig.IMAGE_URL + item.posterPath)
                    .into(ivPoster)
                tvReview.text = (item.voteAverage ?: 0.0f).toString()
                tvYear.text = item.releaseDate?.convertDate(Constant.DATE_YYYY_MM_DD_FORMAT, Constant.DATE_YYYY_FORMAT)
                tvAge.text = if (item.adult == true)
                    context.getString(R.string.label_adult)
                else context.getString(R.string.label_not_adult)
            }
        }
    }

    object MovieDiffcallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }
    }
}