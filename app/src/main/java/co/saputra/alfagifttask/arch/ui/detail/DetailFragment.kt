package co.saputra.alfagifttask.arch.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.saputra.alfagifttask.R
import co.saputra.alfagifttask.arch.ui.adapter.ReviewAdapter
import co.saputra.alfagifttask.arch.ui.adapter.ReviewPagingAdapter
import co.saputra.alfagifttask.arch.vm.MainViewModel
import co.saputra.alfagifttask.base.BaseFragment
import co.saputra.alfagifttask.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.putrash.common.parcelable
import com.putrash.data.BuildConfig
import com.putrash.data.model.Movie
import com.putrash.data.model.Review
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, MainViewModel>(
    FragmentDetailBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()
    private val adapter by lazy {
        ReviewAdapter(layoutInflater, Glide.with(requireContext()), ::onItemClick)
    }
    private val pagingAdapter by lazy {
        ReviewPagingAdapter(layoutInflater, Glide.with(requireContext()), ::onItemClick)
    }
    private lateinit var data : Movie

    override fun initView(view: View, savedInstaceState: Bundle?) {
        data = initBundle()
        binding.apply {
            toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            Glide.with(requireContext())
                .load(BuildConfig.IMAGE_URL + data.posterPath)
                .into(ivPoster)

            tvTitle.text = data.title
            tvContent.text = data.overview
            rvReview.adapter = pagingAdapter
        }
        viewModel.getMovieReviews(data.id)
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.setReviewId(data.id)
            viewModel.reviewsFlow.collectLatest {
                pagingAdapter.submitData(it)
            }
        }
        viewModel.reviewsMovie.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }

    private fun initBundle(): Movie {
        return arguments?.parcelable<Movie>("movie") ?: Movie()
    }

    private fun onItemClick(review: Review) {
        val bundle = bundleOf("review" to review)
        findNavController()
            .navigate(R.id.action_discoverFragment_to_detailFragment, bundle)
    }
}