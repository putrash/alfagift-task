package co.saputra.alfagifttask.arch.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.saputra.alfagifttask.R
import co.saputra.alfagifttask.arch.ui.adapter.paging.MoviePagingAdapter
import co.saputra.alfagifttask.arch.vm.MainViewModel
import co.saputra.alfagifttask.base.BaseFragment
import co.saputra.alfagifttask.databinding.FragmentMovieListBinding
import com.putrash.common.Constant
import com.putrash.data.model.Movie
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieListFragment : BaseFragment<FragmentMovieListBinding, MainViewModel>(
    FragmentMovieListBinding::inflate
) {
    override val viewModel: MainViewModel by sharedViewModel()
    private val adapter by lazy {
        MoviePagingAdapter(layoutInflater, requireContext(), ::onItemClick)
    }

    override fun initView(view: View, savedInstaceState: Bundle?) {
        binding.apply {
            if (getType() == Constant.TYPE_PLAYING_NOW)  {
                toolbar.title = getString(R.string.title_playing_now)
                viewModel.getAllPlayingNowMovies()
            } else  {
                toolbar.title = getString(R.string.title_top_rated)
                viewModel.getAllTopRatedMovies()
            }
            toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            rvMovie.adapter = adapter
        }
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            when (getType()) {
                Constant.TYPE_PLAYING_NOW -> {
                    viewModel.moviesPlayingNowFlow.collectLatest {
                        adapter.submitData(it)
                    }
                }
                Constant.TYPE_TOP_RATED -> {
                    viewModel.moviesTopRatedFlow.collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
        }
    }

    private fun getType(): String {
        return arguments?.getString(Constant.KEY_TYPE) ?: Constant.TYPE_PLAYING_NOW
    }

    private fun onItemClick(movie: Movie) {
        val bundle = bundleOf(Constant.KEY_MOVIE to movie.id)
        findNavController()
            .navigate(R.id.action_movieListFragment_to_detailFragment, bundle)
    }

}