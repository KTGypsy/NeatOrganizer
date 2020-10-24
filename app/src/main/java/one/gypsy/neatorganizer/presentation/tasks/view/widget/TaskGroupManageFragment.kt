package one.gypsy.neatorganizer.presentation.tasks.view.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import one.gypsy.neatorganizer.R
import one.gypsy.neatorganizer.databinding.FragmentTaskGroupManageBinding
import one.gypsy.neatorganizer.presentation.tasks.view.GroupedTasksAdapter
import one.gypsy.neatorganizer.presentation.tasks.vm.TaskWidgetContentManageViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TaskGroupManageFragment : Fragment() {
    private val tasksViewModel: TaskWidgetContentManageViewModel by viewModel {
        parametersOf(arguments?.getLong(MANAGED_GROUP_ID_KEY) ?: MANAGED_GROUP_INVALID_ID)
    }
    private lateinit var viewBinding: FragmentTaskGroupManageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_group_manage, container, false)
        setHasOptionsMenu(true)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            viewModel = tasksViewModel
            lifecycleOwner = this@TaskGroupManageFragment
        }
        setUpRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.add_entry -> {
            navigateToAddTaskDialog()
            true
        }
        else -> false
    }

    private fun navigateToAddTaskDialog() {
        val groupId = arguments?.getLong(MANAGED_GROUP_ID_KEY) ?: MANAGED_GROUP_INVALID_ID
        if (groupId != MANAGED_GROUP_INVALID_ID) {
            val direction = TaskGroupManageFragmentDirections
                .actionTaskGroupManageFragmentToAddSingleTaskDialog(groupId)
            findNavController(this).navigate(direction)
        }
    }

    private fun setUpRecyclerView() = viewBinding.apply {
        linearLayoutManager = LinearLayoutManager(context)
        tasksAdapter = GroupedTasksAdapter()
        tasks.itemAnimator = null
        executePendingBindings()
    }

}
