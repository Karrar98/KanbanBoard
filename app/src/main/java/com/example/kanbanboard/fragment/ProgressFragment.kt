package com.example.kanbanboard.fragment

import android.view.LayoutInflater
import com.example.kanbanboard.helper.TaskDBHelper
import com.example.kanbanboard.adapter.TaskViewAdapter
import com.example.kanbanboard.databinding.TaskFragmentBinding
import com.example.kanbanboard.utails.DataManger

class ProgressFragment: BaseFragment<TaskFragmentBinding>() {
    override val LOG_TAG: String = ""
    override val bindingInflater: (LayoutInflater) -> com.example.kanbanboard.databinding.TaskFragmentBinding = com.example.kanbanboard.databinding.TaskFragmentBinding::inflate

    private lateinit var db: TaskDBHelper

    override fun setup() {
        db = context?.let { TaskDBHelper(it) }!!
        val adapter = TaskViewAdapter(DataManger.listTaskProgress(requireContext()))
        binding?.taskRecyclerView?.adapter = adapter
    }

    override fun addCallBack() {

    }
}