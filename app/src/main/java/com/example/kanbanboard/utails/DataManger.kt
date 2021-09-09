package com.example.kanbanboard.utails

import android.content.Context
import com.example.kanbanboard.fragment.DoneFragment
import com.example.kanbanboard.fragment.ProgressFragment
import com.example.kanbanboard.helper.TaskDBHelper
import com.example.kanbanboard.model.Task
import java.lang.reflect.Array.get

class DataManger(private var context: Context) {

    var listTaskBacklog = mutableListOf<Task>()
    var listTaskProgress = mutableListOf<Task>()
    var listTaskDone = mutableListOf<Task>()

    val listBackLog: List<Task>
        get() = listTaskBacklog

    val listProgress: List<Task>
        get() = listTaskProgress

    val listDone: List<Task>
        get() = listTaskDone

    init {
        listTaskBacklog = TaskDBHelper(context).listTask(Constants.TaskStatus.BACKLOG)
        listTaskProgress = TaskDBHelper(context).listTask(Constants.TaskStatus.PROGRESS)
        listTaskDone = TaskDBHelper(context).listTask(Constants.TaskStatus.DONE)
    }

    fun addTaskBacklog(task: Task) = listTaskBacklog.add(task)
    fun addTaskProgress(task: Task) = listTaskProgress.add(task)
    fun addTaskDone(task: Task) = listTaskDone.add(task)

    fun addTask(context: Context, task: Task) {
        TaskDBHelper(context).addTask(task)
        addTaskBacklog(task)
    }
    fun updateTask(context: Context, from: String, task: Task) {
        TaskDBHelper(context).updateTask(task)
        when(from){
            Constants.TaskStatus.BACKLOG -> {
                listTaskBacklog.remove(task)
                addTaskProgress(task)
            }
            Constants.TaskStatus.PROGRESS -> {
                listTaskProgress.remove(task)
                addTaskDone(task)
            }
            Constants.TaskStatus.DONE -> {
                listTaskDone.remove(task)
                addTaskProgress(task)
            }
        }
    }
    fun updateColorTask(context: Context, task: Task) = TaskDBHelper(context).updateColorTask(task)
    fun deleteTask(context: Context, task: Task) {
        TaskDBHelper(context).deleteTask(task.taskId)
        when(task.taskStatus){
            Constants.TaskStatus.BACKLOG -> listTaskBacklog.remove(task)
            Constants.TaskStatus.PROGRESS -> listTaskProgress.remove(task)
            Constants.TaskStatus.DONE -> listTaskDone.remove(task)
        }
    }
}