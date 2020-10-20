package com.sduduzog.slimlauncher.ui.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.HideAppsAdapter
import com.sduduzog.slimlauncher.models.HiddenApp
import com.sduduzog.slimlauncher.models.HiddenAppsViewModel
import com.sduduzog.slimlauncher.ui.dialogs.RemoveAllHiddenAppsDialog
import com.sduduzog.slimlauncher.utils.BaseFragment
import com.sduduzog.slimlauncher.utils.OnShitDoneToHiddenAppsListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.customise_apps_fragment.customise_apps_fragment_add
import kotlinx.android.synthetic.main.customise_apps_fragment.customise_apps_fragment_list
import kotlinx.android.synthetic.main.customise_apps_fragment.customise_apps_fragment_remove_all
import kotlinx.android.synthetic.main.hide_apps_fragment.*


@AndroidEntryPoint
class HideAppsFragment : BaseFragment(), OnShitDoneToHiddenAppsListener {

    override fun getFragmentView(): ViewGroup = hide_apps_fragment

    private val viewModel: HiddenAppsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setFragmentResult("appsMode", bundleOf("hide" to true))
        return inflater.inflate(R.layout.hide_apps_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = HideAppsAdapter(this)

        viewModel.apps.observe(viewLifecycleOwner, Observer {
            it?.let { apps ->
                adapter.setItems(apps)
                customise_apps_fragment_add.visibility = View.VISIBLE
            } ?: adapter.setItems(listOf())
        })


        customise_apps_fragment_remove_all.setOnClickListener {
            RemoveAllHiddenAppsDialog.getInstance(viewModel.apps.value!!, viewModel).show(childFragmentManager, "REMOVE_APPS")
        }

        customise_apps_fragment_list.adapter = adapter
        customise_apps_fragment_add.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_hideAppsFragment_to_addAppFragment))
    }

    private fun showPopupMenu(view: View): PopupMenu {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.hide_apps_popup_menu, popup.menu)
        popup.show()
        return popup
    }

    override fun onAppsUpdated(list: List<HiddenApp>) {
        viewModel.update(*list.toTypedArray())
    }

    override fun onAppMenuClicked(view: View, app: HiddenApp) {
        showPopupMenu(view).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.ca_menu_remove -> {
                    viewModel.remove(app)
                }
            }
            true
        }
    }
}
