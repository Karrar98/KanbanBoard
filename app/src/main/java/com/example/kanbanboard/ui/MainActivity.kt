package com.example.kanbanboard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.example.kanbanboard.R
import com.example.kanbanboard.helper.TaskDBHelper
import com.example.kanbanboard.adapter.ViewPagerAdapter
import com.example.kanbanboard.databinding.ActivityMainBinding
import com.example.kanbanboard.databinding.AddTaskDialogBinding
import com.example.kanbanboard.fragment.BacklogFragment
import com.example.kanbanboard.fragment.DoneFragment
import com.example.kanbanboard.fragment.ProgressFragment
import com.example.kanbanboard.model.Task
import com.example.kanbanboard.utails.Constants
import com.example.kanbanboard.utails.DataManger
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: TaskDBHelper

    private val fragments: List<Fragment> = listOf(
        BacklogFragment(),
        ProgressFragment(),
        DoneFragment(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
    }

    private fun setUp() {
        setUpViewPager()
        setUpTabLayout()
        databaseHelper = TaskDBHelper(this)
    }

    private fun setUpViewPager() {
        val mAdapte = ViewPagerAdapter(this, fragments)
        binding.viewPagerTask.adapter = mAdapte
    }

    private fun setUpTabLayout() {
        TabLayoutMediator(binding.tabTask, binding.viewPagerTask) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_label_backlog)
                1 -> getString(R.string.tab_label_progress)
                else -> getString(R.string.tab_label_done)
            }
        }.attach()
    }
}