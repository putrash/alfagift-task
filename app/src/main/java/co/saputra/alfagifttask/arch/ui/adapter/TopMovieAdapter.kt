package co.saputra.alfagifttask.arch.ui.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import co.saputra.alfagifttask.base.BaseListAdapter
import co.saputra.alfagifttask.base.BaseViewHolder
import co.saputra.alfagifttask.databinding.ItemMovieTopBinding
import com.bumptech.glide.RequestManager
import com.putrash.data.BuildConfig
import com.putrash.data.model.Movie

class TopMovieAdapter(
    layoutInflater: LayoutInflater,
    private val glide: RequestManager,
    private val onClickListener: (Movie) -> Unit
) : BaseListAdapter<Movie, ItemMovieTopBinding, TopMovieAdapter.ViewHolder>(
    layoutInflater,
    ItemMovieTopBinding::inflate,
    MovieDiffcallback
) {

    override fun itemViewHolder(viewBinding: ItemMovieTopBinding, viewType: Int): ViewHolder {
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMovieTopBinding) : BaseViewHolder<Movie>(binding.root) {
        override fun onBind(item: Movie) {
            binding.apply {
                root.setOnClickListener {
                    onClickListener(item)
                }
                tvTitle.text = item.title
                // tvDuration.text = item.
                glide
                    .load(BuildConfig.IMAGE_URL + item.backdropPath)
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