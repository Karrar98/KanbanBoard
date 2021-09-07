package com.example.kanbanboard.model

import com.example.kanbanboard.utails.TaskColor
import java.util.UUID

data class Task(
    val taskId: Int?,
    var taskName: String,
    var taskDescription: String,
    var taskStatus: String,
    var color: Int?
)
