package uz.mirsaidoff.curexrate.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.ext.android.inject
import uz.mirsaidoff.curexrate.R
import uz.mirsaidoff.curexrate.common.showAlertDialog

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by inject()
    private lateinit var viewAdapter: MainViewAdapter
    private var listener: NavigationListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavigationListener) listener = context
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.update) {
            refresh()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refresh() {
        viewModel.getLatestExchangeRates(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewAdapter = MainViewAdapter(listener)
        rv_main.adapter = viewAdapter
        rv_main.layoutManager = LinearLayoutManager(context)

        initSubscriptions()
        viewModel.getLatestExchangeRates(false)
    }

    private fun initSubscriptions() {
        viewModel.resultLiveData.observe(viewLifecycleOwner) { viewAdapter.setItems(it) }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { requireContext().showAlertDialog(it) }
    }

}