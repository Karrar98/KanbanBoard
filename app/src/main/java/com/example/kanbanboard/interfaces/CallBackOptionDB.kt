package com.example.kanbanboard.interfaces

import android.content.Context
import com.example.kanbanboard.model.Task

interface CallBackOptionDB {
    fun addTask(context: Context, task: Task)
    fun updateTask(context: Context, from: String, task: Task)
    fun deleteTask(context: Context, task: Task)
    fun updateTaskColor(context: Context, task: Task)
}