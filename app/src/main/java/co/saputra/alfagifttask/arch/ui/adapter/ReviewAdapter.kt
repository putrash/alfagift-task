package co.saputra.alfagifttask.arch.ui.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import co.saputra.alfagifttask.base.BaseListAdapter
import co.saputra.alfagifttask.base.BaseViewHolder
import co.saputra.alfagifttask.databinding.ItemReviewBinding
import com.bumptech.glide.RequestManager
import com.putrash.data.model.Review

class ReviewAdapter(
    layoutInflater: LayoutInflater,
    private val glide: RequestManager,
    private val onClickListener: (Review) -> Unit
) : BaseListAdapter<Review, ItemReviewBinding, ReviewAdapter.ViewHolder>(
    layoutInflater,
    ItemReviewBinding::inflate,
    ReviewDiffcallback
) {

    override fun itemViewHolder(viewBinding: ItemReviewBinding, viewType: Int): ViewHolder {
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemReviewBinding) : BaseViewHolder<Review>(binding.root) {
        override fun onBind(item: Review) {
            binding.apply {
                tvName.text = item.author
                tvContent.text = item.content
                tvReview.text = item.authorDetails.rating.toString()
            }
        }
    }

    object ReviewDiffcallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.content == newItem.content
        }
    }
}