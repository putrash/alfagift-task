package co.saputra.alfagifttask.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.putrash.common.observeEvent
import com.putrash.common.component.ProgressDialog

abstract class BaseFragment<VB: ViewBinding, VM: BaseViewModel>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding : VB? = null
    protected val binding get() = _binding!!

    protected lateinit var progressDialog: ProgressDialog

    protected abstract val viewModel: VM
    protected abstract fun initView(view: View, savedInstaceState: Bundle?)

    open fun observeLiveData() {
        viewModel.isLoading.observeEvent(viewLifecycleOwner) { loading ->
            if (loading) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }

        viewModel.errorMessage.observeEvent(viewLifecycleOwner) { message ->
            showSnackBar(message)
        }
    }

    fun showSnackBar(message: String) = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).apply { show() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(requireContext())
        initView(view, savedInstanceState)
        observeLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}