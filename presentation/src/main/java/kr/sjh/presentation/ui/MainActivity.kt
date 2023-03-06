package kr.sjh.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.sjh.presentation.R
import kr.sjh.presentation.databinding.ActivityMainBinding
import kr.sjh.presentation.ui.fragment.HomeFragment
import kr.sjh.presentation.ui.fragment.MovieListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        //navController를 bottomNavigationView의 navctrl에 설정
        mainBinding.nvBottom.setupWithNavController(navController)


    }
}