package co.saputra.alfagifttask.arch.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.saputra.alfagifttask.R
import co.saputra.alfagifttask.arch.ui.adapter.MoviePagingAdapter
import co.saputra.alfagifttask.arch.vm.MainViewModel
import co.saputra.alfagifttask.base.BaseFragment
import co.saputra.alfagifttask.databinding.FragmentMovieListBinding
import com.bumptech.glide.Glide
import com.putrash.common.parcelable
import com.putrash.data.model.Movie
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : BaseFragment<FragmentMovieListBinding, MainViewModel>(
    FragmentMovieListBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()
    private val adapter by lazy {
        MoviePagingAdapter(layoutInflater, Glide.with(requireContext()), ::onItemClick)
    }

    override fun initView(view: View, savedInstaceState: Bundle?) {
        binding.rvMovie.adapter = adapter
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            when (initBundle()) {
                "playing_now" -> {
                    viewModel.moviesPlayingNowFlow.collectLatest {
                        adapter.submitData(it)
                    }
                }
                "top_rated" -> {
                    viewModel.moviesTopRatedFlow.collectLatest {
                        adapter.submitData(it)
                    }
                }
            }

        }
    }

    private fun initBundle(): String {
        return arguments?.getString("type") ?: "playing_now"
    }

    private fun onItemClick(movie: Movie) {
        val bundle = bundleOf("movie" to movie)
        findNavController()
            .navigate(R.id.action_discoverFragment_to_detailFragment, bundle)
    }

}