package co.saputra.alfagifttask.arch.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import co.saputra.alfagifttask.base.BaseListAdapter
import co.saputra.alfagifttask.base.BaseViewHolder
import co.saputra.alfagifttask.databinding.ItemMoviePlayingBinding
import com.bumptech.glide.Glide
import com.putrash.data.BuildConfig
import com.putrash.data.model.Movie

class PlayingNowMovieAdapter(
    layoutInflater: LayoutInflater,
    private val context: Context,
    private val onClickListener: (Movie) -> Unit
) : BaseListAdapter<Movie, ItemMoviePlayingBinding, PlayingNowMovieAdapter.ViewHolder>(
    layoutInflater,
    ItemMoviePlayingBinding::inflate,
    MovieDiffcallback
) {

    override fun itemViewHolder(viewBinding: ItemMoviePlayingBinding, viewType: Int): ViewHolder {
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMoviePlayingBinding) : BaseViewHolder<Movie>(binding.root) {
        override fun onBind(item: Movie) {
            binding.apply {
                root.setOnClickListener {
                    onClickListener(item)
                }
                tvTitle.text = item.title
                tvGenre.text = item.genres?.map { it.name }?.joinToString(", ")
                Glide.with(context)
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