package one.gypsy.neatorganizer.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_home.bottom_navigation_view_activity_home
import kotlinx.android.synthetic.main.activity_home.organizerToolbar
import one.gypsy.neatorganizer.R
import one.gypsy.neatorganizer.core.utils.extensions.show
import one.gypsy.neatorganizer.core.utils.extensions.shrink
import one.gypsy.neatorganizer.note.view.widget.management.NoteWidgetSynchronizationService
import one.gypsy.neatorganizer.task.view.widget.management.TaskWidgetSynchronizationService

internal class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpActionBar()
        startWidgetSynchronizationServices()
        setUpLocationListener()
    }

    private fun setUpActionBar() {
        setSupportActionBar(organizerToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun startWidgetSynchronizationServices() {
        Intent(this, TaskWidgetSynchronizationService::class.java).also { intent ->
            startService(intent)
        }
        Intent(this, NoteWidgetSynchronizationService::class.java).also { intent ->
            startService(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopServices()
    }

    private fun stopServices() {
        stopService(Intent(this, TaskWidgetSynchronizationService::class.java))
        stopService(Intent(this, NoteWidgetSynchronizationService::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_activity_home_nav_container).navigateUp()
    }

    private fun setUpBottomNavigation() {
        bottom_navigation_view_activity_home.setupWithNavController(findNavController(R.id.fragment_activity_home_nav_container))
        bottom_navigation_view_activity_home.setOnNavigationItemReselectedListener {
            // Do nothing to prevent fragment reloading
        }
    }
    private fun setUpLocationListener() {
        findNavController(R.id.fragment_activity_home_nav_container).addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.noteDetails) {
                bottom_navigation_view_activity_home.shrink()
            } else {
                bottom_navigation_view_activity_home.show()
            }
        }
    }
}
