package com.example.kanbanboard.interfaces

import com.example.kanbanboard.model.Task

interface CallBackOptionDB {
    fun addTask(task: Task)
    fun updateTask(task: Task)
    fun deleteTask(taskId: Int)

}