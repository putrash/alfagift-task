package co.saputra.alfagifttask.arch.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.saputra.alfagifttask.R
import co.saputra.alfagifttask.arch.ui.adapter.paging.ReviewPagingAdapter
import co.saputra.alfagifttask.arch.vm.MainViewModel
import co.saputra.alfagifttask.base.BaseFragment
import co.saputra.alfagifttask.databinding.FragmentReviewListBinding
import com.putrash.common.Constant
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ReviewListFragment : BaseFragment<FragmentReviewListBinding, MainViewModel>(
    FragmentReviewListBinding::inflate
) {
    override val viewModel: MainViewModel by sharedViewModel()
    private val adapter by lazy {
        ReviewPagingAdapter(layoutInflater, requireContext())
    }
    private var movieId : Int = Constant.DEFAULT_MOVIE_ID

    override fun initView(view: View, savedInstaceState: Bundle?) {
        movieId = getMovieId()
        binding.apply {
            toolbar.title = getString(R.string.title_reviews)
            toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            rvReview.adapter = adapter
        }
        viewModel.setReviewId(movieId)
        viewModel.getAllReviews()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.reviewsFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun getMovieId(): Int {
        return arguments?.getInt(Constant.KEY_MOVIE) ?: Constant.DEFAULT_MOVIE_ID
    }
}