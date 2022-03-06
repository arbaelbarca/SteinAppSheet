package com.arbaelbarca.steinsheetapp.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arbaelbarca.steinsheetapp.data.base.BaseFragmentBinding
import com.arbaelbarca.steinsheetapp.databinding.FragmentSheetHomeBinding
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSheetList
import com.arbaelbarca.steinsheetapp.presentation.viewmodel.ViewModelSheet
import com.arbaelbarca.steinsheetapp.ui.adapter.AdapterSheet
import com.arbaelbarca.steinsheetapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SheetHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeSheetFragment : BaseFragmentBinding<FragmentSheetHomeBinding>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var fragmentBinding: FragmentSheetHomeBinding
    val viewModelSheet: ViewModelSheet by viewModels()
    var mutableListSheet: MutableList<ResponseSheetList.ResponseSheetListItem> = arrayListOf()
    val adapterSheet: AdapterSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SheetHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSheetHomeBinding
        get() = FragmentSheetHomeBinding::inflate

    override fun setupView(binding: FragmentSheetHomeBinding) {
        fragmentBinding = binding
        initial()
    }

    private fun initial() {
        initCallApi()
        initObserving()
        initTextWatcher()
    }

    private fun initTextWatcher() {
        fragmentBinding.edSearch.addTextChangedListener {
            if (it?.isNotEmpty() == true) {
                searchFilterList(it.toString())
            }
        }
    }

    private fun initObserving() {
        viewModelSheet.observerListSheet()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {
                        showView(fragmentBinding.pbList)
                    }
                    is UiState.Success -> {
                        hideView(fragmentBinding.pbList)
                        val dataItem = it.data
                        if (dataItem.isNotEmpty()) {
                            mutableListSheet = dataItem.toMutableList()
                            println("respon List $mutableListSheet")
                            initAdapter(mutableListSheet)
                        }

                    }
                    is UiState.Failure -> {
                        hideView(fragmentBinding.pbList)
                        it.throwable.printStackTrace()
                        showToast("error throw", requireContext())
                    }
                }
            }
    }

    fun searchFilterList(text: String) {
        val mutableList: MutableList<ResponseSheetList.ResponseSheetListItem> = arrayListOf()

        lifecycleScope.launch {
            kotlin.runCatching {
                for (responseSheetListItem in mutableListSheet) {
                    println("respon Komditas ${responseSheetListItem.komoditas?.lowercase()}")
                    if (responseSheetListItem.komoditas!!
                            .contains(text)
                    ) {
                        mutableList.add(responseSheetListItem)
                    }
                }

                adapterSheet!!.searchFilter(mutableList)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    private fun initAdapter(dataItem: List<ResponseSheetList.ResponseSheetListItem>) {
        val adapterSheet = AdapterSheet(dataItem.toMutableList())
        setRvAdapterVertikalDefault(fragmentBinding.rvListSheet, adapterDefault = adapterSheet)
    }

    private fun initCallApi() {
        viewModelSheet.getSheetList()
    }
}