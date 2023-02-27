package co.saputra.alfagifttask.arch.ui.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import co.saputra.alfagifttask.base.BaseListAdapter
import co.saputra.alfagifttask.base.BasePagingAdapter
import co.saputra.alfagifttask.base.BaseViewHolder
import co.saputra.alfagifttask.databinding.ItemMovieBinding
import com.bumptech.glide.RequestManager
import com.putrash.data.BuildConfig
import com.putrash.data.model.Movie
import com.putrash.data.model.Review

class MoviePagingAdapter(
    layoutInflater: LayoutInflater,
    private val glide: RequestManager,
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
                tvContent.text = item.overview
                // tvDuration.text = item.
                glide
                    .load(BuildConfig.IMAGE_URL + item.posterPath)
                    .into(ivPoster)
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