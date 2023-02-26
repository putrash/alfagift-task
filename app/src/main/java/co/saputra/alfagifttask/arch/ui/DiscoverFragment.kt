package co.saputra.alfagifttask.arch.ui

import android.os.Bundle
import android.view.View
import co.saputra.alfagifttask.arch.vm.MainViewModel
import co.saputra.alfagifttask.base.BaseFragment
import co.saputra.alfagifttask.databinding.FragmentDiscoverBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding, MainViewModel>(
    FragmentDiscoverBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()

    override fun initView(view: View, savedInstaceState: Bundle?) {

    }

}