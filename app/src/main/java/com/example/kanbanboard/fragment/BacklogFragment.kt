package com.example.kanbanboard.fragment

import android.view.LayoutInflater
import com.example.kanbanboard.helper.TaskDBHelper
import com.example.kanbanboard.adapter.TaskViewAdapter
import com.example.kanbanboard.databinding.TaskFragmentBinding
import com.example.kanbanboard.utails.DataManger

class BacklogFragment: BaseFragment<TaskFragmentBinding>() {
    override val LOG_TAG: String = ""
    override val bindingInflater: (LayoutInflater) -> TaskFragmentBinding = TaskFragmentBinding::inflate

    private lateinit var db: TaskDBHelper

    override fun setup() {
        db = context?.let { TaskDBHelper(it) }!!
        val adapter = TaskViewAdapter(DataManger.listTaskBacklog(requireContext()))
        binding?.taskRecyclerView?.adapter = adapter
    }

    override fun addCallBack() {

    }
}