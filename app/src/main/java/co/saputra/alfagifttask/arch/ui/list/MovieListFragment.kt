package co.saputra.alfagifttask.arch.ui.list

import android.os.Bundle
import android.view.View
import co.saputra.alfagifttask.arch.vm.MainViewModel
import co.saputra.alfagifttask.base.BaseFragment
import co.saputra.alfagifttask.databinding.FragmentMovieListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : BaseFragment<FragmentMovieListBinding, MainViewModel>(
    FragmentMovieListBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()

    override fun initView(view: View, savedInstaceState: Bundle?) {

    }

}