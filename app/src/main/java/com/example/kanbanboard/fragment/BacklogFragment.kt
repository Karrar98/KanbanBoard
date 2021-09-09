package com.example.kanbanboard.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.kanbanboard.R
import com.example.kanbanboard.helper.TaskDBHelper
import com.example.kanbanboard.adapter.TaskViewAdapter
import com.example.kanbanboard.databinding.AddTaskDialogBinding
import com.example.kanbanboard.databinding.TaskFragmentBinding
import com.example.kanbanboard.interfaces.CallBackOptionDB
import com.example.kanbanboard.model.Task
import com.example.kanbanboard.utails.Constants
import com.example.kanbanboard.utails.DataManger
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BacklogFragment: BaseFragment<TaskFragmentBinding>(), CallBackOptionDB {
    override val LOG_TAG: String = ""
    override val bindingInflater: (LayoutInflater) -> TaskFragmentBinding = TaskFragmentBinding::inflate

    lateinit var adapter: TaskViewAdapter
    lateinit var dataManger: DataManger

    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var customAlertDialogView : View

    override fun setup() {
        dataManger = DataManger(requireContext())
        adapter = TaskViewAdapter(dataManger.listBackLog, this)
        binding?.taskRecyclerView?.adapter = adapter
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        binding?.fabAdd?.setOnClickListener {
            addTask()
        }
        adapter.setData(dataManger.listBackLog)
    }

    override fun addCallBack() {

    }

    fun addTask(){
        // Inflate Custom alert dialog view
        customAlertDialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.add_task_dialog, null, false)
        val binding: AddTaskDialogBinding = AddTaskDialogBinding.bind(customAlertDialogView)

        // Launching the custom alert dialog
        // Building the Alert dialog using materialAlertDialogBuilder instance
        materialAlertDialogBuilder.setView(customAlertDialogView)
            .setTitle("Task")
            .setMessage("Enter your Task details")
            .setPositiveButton("Add Task") { dialog, _ ->
                binding.apply {
                    val taskName = txtNameTask.editText?.text.toString()
                    val taskDescription = txtDescriptionTask.editText?.text.toString()
                    val taskColor = R.drawable.task_card_yellow
                    val task = Task(
                        taskId = null,
                        taskName = taskName,
                        taskDescription = taskDescription,
                        taskStatus = Constants.TaskStatus.BACKLOG,
                        color = taskColor
                    )
                    addTask(requireContext(),task)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun addTask(context: Context, task: Task) {
        dataManger.addTask(context, task)
        adapter.setData(dataManger.listBackLog)
    }

    override fun updateTask(context: Context, from: String, task: Task) {
        dataManger.updateTask(context, from, task)
        adapter.setData(dataManger.listBackLog)
        Toast.makeText(requireContext(),"hi update task", Toast.LENGTH_LONG).show()
    }

    override fun deleteTask(context: Context, task: Task) {
        dataManger.deleteTask(context, task)
        adapter.setData(dataManger.listBackLog)
        Toast.makeText(requireContext(),"hi delete task", Toast.LENGTH_LONG).show()
    }

    override fun updateTaskColor(context: Context, task: Task) {
        dataManger.updateColorTask(context, task)
    }
}