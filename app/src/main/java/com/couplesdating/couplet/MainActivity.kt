package com.couplesdating.couplet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.couplesdating.couplet.ui.LoginFragment
import com.couplesdating.couplet.ui.viewModel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        viewModel.userLiveData.observe(this) { user ->
            user?.let {
                // already signed in
            } ?: run {
                showLogin()
            }
        }
    }

    private fun showLogin() {
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragment_container_view,
                LoginFragment()
            ).commit()
    }
}