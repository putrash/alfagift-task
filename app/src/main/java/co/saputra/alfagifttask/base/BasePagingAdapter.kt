package co.saputra.alfagifttask.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BasePagingAdapter<T : Any, VB: ViewBinding, VH: RecyclerView.ViewHolder>(
    private val layoutInflater: LayoutInflater,
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    diffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, VH>(diffCallback) {

    protected abstract fun itemViewHolder(
        viewBinding: VB,
        viewType: Int,
    ) : VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewBinding = bindingInflater.invoke(layoutInflater, parent, false)
        return itemViewHolder(viewBinding, viewType)
    }
}