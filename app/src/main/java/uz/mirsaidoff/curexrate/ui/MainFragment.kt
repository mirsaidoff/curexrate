package uz.mirsaidoff.curexrate.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
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
        setHasOptionsMenu(true)
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
        viewAdapter = MainViewAdapter(requireContext(), listener)
        rv_main.adapter = viewAdapter
        rv_main.layoutManager = GridLayoutManager(context, 2)

        initSubscriptions()
        viewModel.getLatestExchangeRates(false)
    }

    private fun initSubscriptions() {
        viewModel.apply {
            resultLive.observe(viewLifecycleOwner) { viewAdapter.setItems(it) }
            errorLive.observe(viewLifecycleOwner) { requireContext().showAlertDialog(it) }
            progressLive.observe(viewLifecycleOwner) {
                progress.isVisible = it
                rv_main.isVisible = !it
            }
        }
    }

}