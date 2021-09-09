package com.example.kanbanboard.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.kanbanboard.helper.TaskDBHelper
import com.example.kanbanboard.adapter.TaskViewAdapter
import com.example.kanbanboard.databinding.TaskFragmentBinding
import com.example.kanbanboard.interfaces.CallBackOptionDB
import com.example.kanbanboard.model.Task
import com.example.kanbanboard.utails.DataManger

class ProgressFragment: BaseFragment<TaskFragmentBinding>(), CallBackOptionDB {
    override val LOG_TAG: String = ""
    override val bindingInflater: (LayoutInflater) -> TaskFragmentBinding = TaskFragmentBinding::inflate

    private lateinit var adapter: TaskViewAdapter
    lateinit var dataManger: DataManger

    override fun setup() {
        dataManger = DataManger(requireContext())
        adapter = TaskViewAdapter(dataManger.listProgress, this)
        binding?.taskRecyclerView?.adapter = adapter
        binding?.fabAdd?.visibility = View.GONE
    }

    override fun addCallBack() {

    }

    override fun addTask(context: Context, task: Task) {
        dataManger.addTask(context, task)
        adapter.setData(dataManger.listProgress)
    }

    override fun updateTask(context: Context, from: String, task: Task) {
        dataManger.updateTask(context, from, task)
        adapter.setData(dataManger.listProgress)
    }

    override fun deleteTask(context: Context, task: Task) {
        dataManger.deleteTask(context, task)
        adapter.setData(dataManger.listProgress)
    }

    override fun updateTaskColor(context: Context, task: Task) {
        dataManger.updateColorTask(context, task)
    }
}