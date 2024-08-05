package com.example.emarketcaseapp.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.emarketcaseapp.R
import com.example.emarketcaseapp.databinding.ActivityMainBinding
import com.example.emarketcaseapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setSupportActionBar(activityMainBinding.appTopBar)
        supportActionBar?.title = getString(R.string.emarket)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(activityMainBinding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        activityMainBinding.bottomBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.updateMenuVisibility(destination.id)
        }

        lifecycleScope.launch {
            viewModel.menuVisibility.collect { visibility ->
                invalidateOptionsMenu()
            }
        }

        lifecycleScope.launch {
            viewModel.isBackButtonVisible.collect { isVisible ->
                supportActionBar?.setDisplayHomeAsUpEnabled(isVisible)
                if (isVisible) {
                    onBackPressedDispatcher.addCallback(this@MainActivity) {
                        supportActionBar?.title = getString(R.string.emarket)
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val filterItem = menu?.findItem(R.id.action_filter)
        val searchView = searchItem?.actionView as SearchView

        lifecycleScope.launch {
            viewModel.menuVisibility.collect { visibility ->
                searchItem.isVisible = visibility.isSearchVisible
                filterItem?.isVisible = visibility.isFilterVisible
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val fragment = navHostFragment.childFragmentManager.fragments[0] as? HomeScreenFragment
                if (query != null) {
                    fragment?.viewModel?.searchProducts(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val fragment = navHostFragment.childFragmentManager.fragments[0] as? HomeScreenFragment
                if (newText != null) {
                    fragment?.viewModel?.searchProducts(newText)
                }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_filter -> {
                showFilterDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterDialog() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val fragment = navHostFragment.childFragmentManager.fragments[0] as? HomeScreenFragment
        if (fragment != null) {
            val dialog = FilterDialog()
            dialog.show(supportFragmentManager, "FilterDialog")
        }
    }
    fun updateTitle(title: String) {
        supportActionBar?.title = title
    }

}
