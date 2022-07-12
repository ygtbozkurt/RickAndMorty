package com.example.rickandmorty.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    //TODO: use base fragment
    private var _binding: VB? = null
    val binding get() = _binding!!

    abstract fun getViewBinding(container: ViewGroup?): VB

    //abstract val analyticsHelperDelegate: AnalyticsHelper?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(container)
        return binding.root
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateActivityIsBaseActivity {
            it.loadingLayout?.isVisible = false
        }
    }

    override fun onResume() {
        super.onResume()
        //analyticsHelperDelegate?.sendScreenView(this::class.java.simpleName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateActivityIsBaseActivity(block: (baseActivity: BaseActivity<*>) -> Unit) {
        val activity = requireActivity()
        if (activity is BaseActivity<*>) {
            block(activity)
        } else {
            throw IllegalArgumentException("Activity must be instance of BaseActivity")
        }
    }

    fun showError(
        failure: Resource.Failure,
        customErrorMessage: String? = null
    ) {
        validateActivityIsBaseActivity {
            it.showError(failure, customErrorMessage)
        }
    }

    fun showSuccess(message: String?) {
        validateActivityIsBaseActivity {
            it.showSuccess(message)
        }
    }

    fun <T> handleResource(
        resource: Resource<T>,
        handleError: Boolean = true,
        showLoading: Boolean = true,
        resourceListener: ResourceListener<T>
    ) {
        validateActivityIsBaseActivity {
            it.handleResource(resource, handleError, showLoading, resourceListener)
        }
    }

    inline fun launchAndRepeatWithViewLifecycle(
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        crossinline block: suspend CoroutineScope.() -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
                block()
            }
        }
    }*/
}