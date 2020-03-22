package one.gypsy.neatorganizer.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_home.*
import one.gypsy.neatorganizer.R
import one.gypsy.neatorganizer.utils.hide
import one.gypsy.neatorganizer.utils.show
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    val navController by lazy {
        findNavController(R.id.fragment_activity_home_nav_container)
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpActionBar()
        setUpNavigationListener()
        AndroidInjection.inject(this)
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_activity_home)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setUpNavigationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                 R.id.person_profile_fragment -> bottom_navigation_view_activity_home.hide()
                else -> bottom_navigation_view_activity_home.show()
            }
        }
    }



//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navigation = findNavController(R.id.fragment_activity_home_nav_container)
//        return item.onNavDestinationSelected(navigation) || super.onOptionsItemSelected(item)
//    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_activity_home_nav_container).navigateUp()
    }


    private fun setUpBottomNavigation() {
        bottom_navigation_view_activity_home.setupWithNavController(findNavController(R.id.fragment_activity_home_nav_container))
    }

}
