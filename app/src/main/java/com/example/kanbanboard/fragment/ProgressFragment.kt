package com.example.kanbanboard.fragment

import android.view.LayoutInflater
import com.example.kanbanboard.helper.TaskDBHelper
import com.example.kanbanboard.adapter.TaskViewAdapter
import com.example.kanbanboard.databinding.TaskFragmentBinding
import com.example.kanbanboard.interfaces.CallBackOptionDB
import com.example.kanbanboard.model.Task
import com.example.kanbanboard.utails.DataManger

class ProgressFragment: BaseFragment<TaskFragmentBinding>(), CallBackOptionDB {
    override val LOG_TAG: String = ""
    override val bindingInflater: (LayoutInflater) -> com.example.kanbanboard.databinding.TaskFragmentBinding = com.example.kanbanboard.databinding.TaskFragmentBinding::inflate

    private lateinit var adapter: TaskViewAdapter


    override fun setup() {
        adapter = TaskViewAdapter(DataManger.listTaskProgress(requireContext()))
        binding?.taskRecyclerView?.adapter = adapter
    }

    override fun addCallBack() {

    }

    override fun addTask(task: Task) {
        DataManger.addTask(requireContext(), task)
        adapter.setData(DataManger.listProgress)
    }

    override fun updateTask(task: Task) {
        DataManger.updateTask(requireContext(), task)
        adapter.setData(DataManger.listProgress)
    }

    override fun deleteTask(taskId: Int) {
        DataManger.deleteTask(requireContext(), taskId)
        adapter.setData(DataManger.listProgress)
    }
}