package com.example.kanbanboard.utails

import android.content.Context
import com.example.kanbanboard.helper.TaskDBHelper
import com.example.kanbanboard.model.Task

object DataManger {

    var listTaskBacklog = mutableListOf<Task>()
    var listTaskProgress = mutableListOf<Task>()
    var listTaskDone = mutableListOf<Task>()

    fun listTaskBacklog(context: Context)  = TaskDBHelper(context).listTask(Constants.TaskStatus.BACKLOG)
    fun listTaskProgress(context: Context) = TaskDBHelper(context).listTask(Constants.TaskStatus.PROGRESS)
    fun listTaskDone(context: Context) = TaskDBHelper(context).listTask(Constants.TaskStatus.DONE)


    fun addTask(context: Context, task: Task) = TaskDBHelper(context).addTask(task)
    fun updateTask(context: Context, task: Task) = TaskDBHelper(context).updateTask(task)
    fun updateColorTask(context: Context, task: Task) = TaskDBHelper(context).updateColorTask(task)
    fun deleteTask(context: Context, idTask: Int) = TaskDBHelper(context).deleteTask(idTask)

}