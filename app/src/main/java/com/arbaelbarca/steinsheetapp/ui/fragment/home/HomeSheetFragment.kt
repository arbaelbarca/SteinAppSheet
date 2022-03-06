package com.arbaelbarca.steinsheetapp.ui.fragment.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.steinsheetapp.R
import com.arbaelbarca.steinsheetapp.data.base.BaseFragmentBinding
import com.arbaelbarca.steinsheetapp.databinding.FragmentSheetHomeBinding
import com.arbaelbarca.steinsheetapp.presentation.listener.OnClickItem
import com.arbaelbarca.steinsheetapp.presentation.model.request.RequestTextFilter
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseCityList
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSheetList
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSizeList
import com.arbaelbarca.steinsheetapp.presentation.viewmodel.ViewModelSheet
import com.arbaelbarca.steinsheetapp.ui.adapter.AdapterSheet
import com.arbaelbarca.steinsheetapp.ui.adapter.AdapterTextFilter
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
    var mutableListCity: MutableList<ResponseCityList.ResponseCityListItem> = arrayListOf()
    var mutableListSize: MutableList<ResponseSizeList.ResponseSizeListItem> = arrayListOf()

    var adapterSheet: AdapterSheet? = null

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
        initDialog()
    }

    private fun initDialog() {
        fragmentBinding.btnFilterDialog.setOnClickListener {
            showDialogFilter()
        }
    }

    private fun showDialogFilter() {
        val dialogFilter = Dialog(requireContext())
        dialogFilter.setContentView(R.layout.layout_dialog_filter)
        dialogFilter.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialogFilter.show()

        val edCityProvins = dialogFilter.findViewById<EditText>(R.id.edFilterDialogCityProvins)
        val edSize = dialogFilter.findViewById<EditText>(R.id.edFilterDialogSize)

        edCityProvins.setOnClickListener {
            showPopupFilterText(edCityProvins, "city")
        }

        edSize.setOnClickListener {
            showPopupFilterText(edSize, "size")
        }

        val btnFilter = dialogFilter.findViewById<Button>(R.id.btnSubmitFilter)

        btnFilter.setOnClickListener {
            val getTextCity = edCityProvins.text.toString()
            val getSize = edSize.text.toString()
            searchFilterDialog(getTextCity, getSize)
            dialogFilter.dismiss()
        }

    }

    private fun searchFilterDialog(getTextCity: String, getSize: String) {
        val mutableList: MutableList<ResponseSheetList.ResponseSheetListItem> = arrayListOf()

        lifecycleScope.launch {
            kotlin.runCatching {
                for (responseSheetListItem in mutableListSheet) {
                    if (getTextCity.isNotEmpty() && getSize.isNotEmpty()) {
                        val spliCity = getTextCity.split(",")
                        if (responseSheetListItem.area_kota?.lowercase()
                                ?.contains(spliCity[0].lowercase()) == true
                            && responseSheetListItem.size?.contains(getSize) == true
                        ) {
                            mutableList.add(responseSheetListItem)
                        }
                    } else if (getTextCity.isNotEmpty()) {
                        val spliCity = getTextCity.split(",")
                        if (responseSheetListItem.area_kota?.lowercase()
                                ?.contains(spliCity[0].lowercase()) == true
                        ) {
                            mutableList.add(responseSheetListItem)
                        }
                    } else if (getSize.isNotEmpty()) {
                        if (responseSheetListItem.size?.contains(getSize) == true) {
                            mutableList.add(responseSheetListItem)
                        }
                    }
                }

                adapterSheet!!.searchFilter(mutableList)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    private fun showPopupFilterText(textFilter: EditText?, isFrom: String) {
        val dialogList = Dialog(requireContext())
        dialogList.setContentView(R.layout.layout_rv_text_filter)
        dialogList.show()

        dialogList.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        val rvList = dialogList.findViewById<RecyclerView>(R.id.rvListFilter)

        val mutableListText: MutableList<RequestTextFilter> = arrayListOf()
        if (isFrom.equals("city")) {
            mutableListCity.forEach {
                mutableListText.addAll(
                    listOf(
                        RequestTextFilter(it.city + ", " + it.province)
                    )
                )
            }
        } else {
            mutableListSize.forEach {
                mutableListText.addAll(
                    listOf(
                        RequestTextFilter(it.size.toString())
                    )
                )
            }
        }

        val adapterTextFilter = AdapterTextFilter(mutableListText,
            object : OnClickItem {
                override fun clicKItem(pos: Int, any: Any) {
                    val dataItem = any as RequestTextFilter
                    textFilter?.setText(dataItem.text)
                    dialogList.dismiss()
                }
            })

        setRvAdapterVertikalDefault(rvList, adapterTextFilter)
    }

    private fun initTextWatcher() {
        fragmentBinding.edSearch.addTextChangedListener {
            if (it?.isNotEmpty() == true) {
                searchFilterList(it.toString())
            }
        }
    }

    private fun initObserving() {
        viewModelSheet.observerCityList()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {
                    }
                    is UiState.Success -> {
                        hideView(fragmentBinding.pbList)
                        val dataItem = it.data
                        if (dataItem.isNotEmpty()) {
                            mutableListCity = dataItem.toMutableList()
                        }

                    }
                    is UiState.Failure -> {
                        it.throwable.printStackTrace()
                        showToast("error throw", requireContext())
                    }
                }
            }

        viewModelSheet.observerSizeList()
            .observe(viewLifecycleOwner) {
                when (it) {
                    is UiState.Loading -> {
                    }
                    is UiState.Success -> {
                        hideView(fragmentBinding.pbList)
                        val dataItem = it.data
                        if (dataItem.isNotEmpty()) {
                            mutableListSize = dataItem.toMutableList()
                        }

                    }
                    is UiState.Failure -> {
                        it.throwable.printStackTrace()
                        showToast("error throw", requireContext())
                    }
                }
            }

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
                    if (responseSheetListItem.komoditas?.lowercase()
                            ?.contains(text.lowercase()) == true
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
        adapterSheet = AdapterSheet(dataItem.toMutableList())
        setRvAdapterVertikalDefault(fragmentBinding.rvListSheet, adapterDefault = adapterSheet!!)
    }

    private fun initCallApi() {
        viewModelSheet.getSheetList()
        viewModelSheet.getCityList()
        viewModelSheet.getSizeList()
    }
}