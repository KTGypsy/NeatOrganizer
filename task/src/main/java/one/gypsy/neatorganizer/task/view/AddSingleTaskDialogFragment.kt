package one.gypsy.neatorganizer.task.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import one.gypsy.neatorganizer.task.R
import one.gypsy.neatorganizer.task.databinding.DialogFragmentAddTaskBinding
import one.gypsy.neatorganizer.task.vm.AddTaskViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class AddSingleTaskDialogFragment : BottomSheetDialogFragment() {

    private val args: AddSingleTaskDialogFragmentArgs by navArgs()
    private val viewModel: AddTaskViewModel by viewModel {
        parametersOf(args.groupId)
    }
    lateinit var fragmentBinding: DialogFragmentAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme_Transparent)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_add_task, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.viewModel = viewModel
        fragmentBinding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.finishedAdding.observe(
            viewLifecycleOwner
        ) { finished ->
            if (finished)
                findNavController().popBackStack()
        }
    }
}
