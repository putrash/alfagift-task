package co.saputra.alfagifttask.arch.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import co.saputra.alfagifttask.R
import co.saputra.alfagifttask.arch.ui.adapter.LatestMovieAdapter
import co.saputra.alfagifttask.arch.ui.adapter.TopMovieAdapter
import co.saputra.alfagifttask.arch.vm.MainViewModel
import co.saputra.alfagifttask.base.BaseFragment
import co.saputra.alfagifttask.databinding.FragmentDiscoverBinding
import com.bumptech.glide.Glide
import com.putrash.data.model.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, MainViewModel>(
    FragmentDiscoverBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()
    private val adapterTopMovie by lazy {
        TopMovieAdapter(layoutInflater, Glide.with(requireContext()), ::onItemClick)
    }
    private val adapterLatestMovie by lazy {
        LatestMovieAdapter(layoutInflater, Glide.with(requireContext()), ::onItemClick)
    }

    override fun initView(view: View, savedInstaceState: Bundle?) {
        binding.apply {
            rvMovieTop.adapter = adapterTopMovie
            rvMovieLatest.adapter = adapterLatestMovie
            tvLatestMore.setOnClickListener {
                val bundle = bundleOf("type" to "playing_now")
                findNavController()
                    .navigate(R.id.action_discoverFragment_to_movieListFragment, bundle)
            }
            tvTopRatedMore.setOnClickListener {
                val bundle = bundleOf("type" to "top_rated")
                findNavController()
                    .navigate(R.id.action_discoverFragment_to_movieListFragment, bundle)
            }
        }
        viewModel.getPlayingNowMovies()
        viewModel.getTopRatedMovies()
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.moviesPlayingNow.observe(viewLifecycleOwner) {
            adapterLatestMovie.submitList(it)
        }
        viewModel.moviesTopRated.observe(viewLifecycleOwner) {
            adapterTopMovie.submitList(it)
        }
    }

    private fun onItemClick(movie: Movie) {
        val bundle = bundleOf("movie" to movie)
        findNavController()
            .navigate(R.id.action_discoverFragment_to_detailFragment, bundle)
    }
}