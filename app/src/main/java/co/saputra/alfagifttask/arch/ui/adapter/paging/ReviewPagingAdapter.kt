package co.saputra.alfagifttask.arch.ui.adapter.paging

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import co.saputra.alfagifttask.base.BasePagingAdapter
import co.saputra.alfagifttask.base.BaseViewHolder
import co.saputra.alfagifttask.databinding.ItemReviewBinding
import com.bumptech.glide.Glide
import com.putrash.common.Constant
import com.putrash.common.convertDate
import com.putrash.common.handleAvatar
import com.putrash.data.model.Review

class ReviewPagingAdapter(
    layoutInflater: LayoutInflater,
    private val context: Context,
) : BasePagingAdapter<Review, ItemReviewBinding, ReviewPagingAdapter.ViewHolder>(
    layoutInflater,
    ItemReviewBinding::inflate,
    ReviewDiffcallback
) {

    override fun itemViewHolder(viewBinding: ItemReviewBinding, viewType: Int): ViewHolder {
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: Review()
        holder.onBind(item)
    }

    inner class ViewHolder(private val binding: ItemReviewBinding) : BaseViewHolder<Review>(binding.root) {
        override fun onBind(item: Review) {
            binding.apply {
                tvName.text = item.author
                tvContent.text = item.content
                tvReview.text = (item.authorDetails.rating ?: 0.0f).toString()
                tvDate.text = item.updatedAt?.convertDate(Constant.DATE_FULL_FORMAT, Constant.DATE_DD_MMMM_YYYY_FORMAT)
                Glide.with(context)
                    .load(item.authorDetails.avatarPath.handleAvatar())
                    .into(ivAvatar)
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