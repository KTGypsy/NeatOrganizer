package one.gypsy.neatorganizer.core.binding

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import one.gypsy.neatorganizer.core.R

@BindingAdapter("expanded")
fun setExpandedState(view: ImageButton, expanded: Boolean) {
    view.setImageResource(
        if (expanded) {
            R.drawable.avd_down_to_up_arrow
        } else {
            R.drawable.avd_up_to_down_arrow
        }
    )
}
