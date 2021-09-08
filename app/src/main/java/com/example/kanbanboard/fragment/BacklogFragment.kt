package com.example.kanbanboard.fragment

import android.view.LayoutInflater
import com.example.kanbanboard.helper.TaskDBHelper
import com.example.kanbanboard.adapter.TaskViewAdapter
import com.example.kanbanboard.databinding.TaskFragmentBinding
import com.example.kanbanboard.interfaces.CallBackOptionDB
import com.example.kanbanboard.model.Task
import com.example.kanbanboard.utails.DataManger

class BacklogFragment: BaseFragment<TaskFragmentBinding>(), CallBackOptionDB {
    override val LOG_TAG: String = ""
    override val bindingInflater: (LayoutInflater) -> TaskFragmentBinding = TaskFragmentBinding::inflate

    lateinit var adapter: TaskViewAdapter

    override fun setup() {
        adapter = TaskViewAdapter(DataManger.listTaskBacklog(requireContext()))
        binding?.taskRecyclerView?.adapter = adapter
    }

    override fun addCallBack() {

    }

    override fun addTask(task: Task) {
        DataManger.addTask(requireContext(), task)
        adapter.setData(DataManger.listBackLog)
    }

    override fun updateTask(task: Task) {
        DataManger.updateTask(requireContext(), task)
        adapter.setData(DataManger.listBackLog)
    }

    override fun deleteTask(taskId: Int) {
        DataManger.deleteTask(requireContext(), taskId)
        adapter.setData(DataManger.listBackLog)
    }
}