package com.example.kanbanboard.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kanbanboard.R
import com.example.kanbanboard.databinding.TaskViewBinding
import com.example.kanbanboard.interfaces.CallBackOptionDB
import com.example.kanbanboard.model.Task
import com.example.kanbanboard.utails.Constants
import com.example.kanbanboard.utails.DataManger
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TaskViewAdapter(private var taskList: List<Task>, private val listener: CallBackOptionDB): RecyclerView.Adapter<TaskViewAdapter.TaskViewHolder>() {

    private lateinit var context: Context
    private lateinit var visibleColorPaletteViewList: List<View>
    private var colorPaletteIsVisible: Boolean = false
    private lateinit var colorPaletteViews: List<View>
    private lateinit var deleteDialog: MaterialAlertDialogBuilder
    lateinit var dataManger: DataManger

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // set context
        context = parent.context
        //Inflate the desired task view
        val view = LayoutInflater.from(context).inflate(R.layout.task_view, parent, false)

        dataManger = DataManger(context)

        deleteDialog = MaterialAlertDialogBuilder(context)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_text)
                .setNegativeButton(R.string.delete_dialog_no_button_label)
                { _, _ -> }

        //Create a viewHolder that holds the created view
        return TaskViewHolder(view)
    }

    //Bind task data to view
    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        //Assign corresponding drawable as background for task card
        val taskCardDrawableResId = task.color

        //Set text to edittext and set card background
        holder.binding.apply {
            colorPaletteViews = listOf(
                colorPaletteBackground,
                greenColorPicker,
                blueColorPicker,
                yellowColorPicker,
                pinkColorPicker
            )
            taskName.text = task.taskName
            taskDescription.text = task.taskDescription
            btnDelete.setOnClickListener {
                deleteDialog.setPositiveButton(R.string.delete_dialog_yes_button_label)
                { _, _ ->
                    //Delete task from db and notify adapter
                    listener.deleteTask(context, task)
                    notifyDataSetChanged()
                }.create().show()
            }
            btnMove.setOnClickListener {
                val from = task.taskStatus
                task.taskStatus = when(task.taskStatus){
                    Constants.TaskStatus.BACKLOG, Constants.TaskStatus.DONE -> Constants.TaskStatus.PROGRESS
                    Constants.TaskStatus.PROGRESS -> Constants.TaskStatus.DONE
                    else -> throw Exception("Unrecognized taskList type")
                }
                listener.updateTask(context, from, task)
            }
//            btnDrag.setOnTouchListener { v, event ->
//                if (event.actionMasked == MotionEvent.ACTION_DOWN)
//                    itemTouchHelper.startDrag(this)
//                v.performClick()
//                return@setOnTouchListener true
//            }
            //Set all color controls to invisible at creation
            setColorPaletteVisibility(View.INVISIBLE)

            //Set listeners for color pickers and color palette button, set palette UI up
            btnColor.setOnClickListener {
                if (colorPaletteIsVisible) {
                    //If there is an open color palette, close the last open palette
                    setColorPaletteVisibility(View.INVISIBLE, visibleColorPaletteViewList)

                    //If the pressed color button is the last task view with colorpalette opened,
                    if (visibleColorPaletteViewList[0] != colorPaletteBackground) {
                        setColorPaletteVisibility(View.VISIBLE)

                        visibleColorPaletteViewList = colorPaletteViews
                    } else {
                        colorPaletteIsVisible = false
                    }
                } else {
                    colorPaletteIsVisible = true
                    setColorPaletteVisibility(View.VISIBLE)
                    visibleColorPaletteViewList = colorPaletteViews
                }
            }
            taskLayout.setOnClickListener {
                if (colorPaletteIsVisible) {
                    setColorPaletteVisibility(View.INVISIBLE, visibleColorPaletteViewList)
                    colorPaletteIsVisible = false
                }
            }

            //Set listeners for color picker buttons
            for (i in 1..4) {
                val colorResId: Int = when (i) {
                    1 -> {
                        R.drawable.task_card_green
                    }
                    2 -> {
                        R.drawable.task_card_blue
                    }
                    3 -> {
                        R.drawable.task_card_yellow
                    }
                    4 -> {
                        R.drawable.task_card_pink
                    }
                    else -> throw Exception()
                }

                //Change background to corresponing color on click.
                colorPaletteViews[i].setOnClickListener {
                    taskLayout.background =
                        ContextCompat.getDrawable(context, colorResId)

                    if (colorPaletteIsVisible) {
                        setColorPaletteVisibility(View.INVISIBLE, visibleColorPaletteViewList)
                        colorPaletteIsVisible = false
                    }

                    task.color = colorResId
                    listener.updateTaskColor(context, task)
                }
            }

            //Set card background
            taskLayout.background = ContextCompat.getDrawable(context, taskCardDrawableResId!!)
        }

        //Set button images and listeners of created viewholder's view (MOVE BUTTON)
        setIconMove(holder.binding, task.taskStatus)
    }

    private fun setColorPaletteVisibility(
        visibilityStatus: Int,
        colorPaletteViewList: List<View> = colorPaletteViews
    ) {
        for (view in colorPaletteViewList)
            view.visibility = visibilityStatus
    }

    private fun setIconMove(binding: TaskViewBinding, taskStatus: String) {
        when (taskStatus) {
            Constants.TaskStatus.BACKLOG -> binding.btnMove.setImageResource(R.drawable.ic_arrow_forward)
            Constants.TaskStatus.PROGRESS -> binding.btnMove.setImageResource(R.drawable.ic_done)
            Constants.TaskStatus.DONE -> {
                binding.btnMove.setImageResource(R.drawable.ic_arrow_forward)
                binding.btnMove.rotation = 180.0f }
            else -> throw Exception("Unrecognized taskList type")
        }
    }

    override fun getItemCount(): Int = taskList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Task>) {
        taskList = newList
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = TaskViewBinding.bind(itemView)
    }

    //ItemTouchHelper instance with custom callback, to move task card view positions on hold
//    val itemTouchHelper by lazy {
//        val taskItemTouchCallback =
//            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    val adapter = recyclerView.adapter as TaskViewAdapter
//                    val from = viewHolder.adapterPosition
//                    val to = target.adapterPosition
//                    adapter.moveTaskView(from, to)
//                    adapter.notifyItemMoved(from, to)
//                    return true
//                }
//
//                //Make taskView transparent while being moved
//                override fun onSelectedChanged(
//                    viewHolder: RecyclerView.ViewHolder?,
//                    actionState: Int
//                ) {
//                    super.onSelectedChanged(viewHolder, actionState)
//
//                    if (actionState == ItemTouchHelper.ACTION_STATE_DRAG)
//                        viewHolder?.itemView?.alpha = 0.7f
//                }
//
//                //Make taskView opaque while being
//                override fun clearView(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder
//                ) {
//                    super.clearView(recyclerView, viewHolder)
//
//                    viewHolder.itemView.alpha = 1.0f
//                }
//
//                override fun onSwiped(
//                    viewHolder: RecyclerView.ViewHolder,
//                    direction: Int
//                ) { /* Not implemented on purpose. */ }
//            }
//        ItemTouchHelper(taskItemTouchCallback)
//    }

    //Change a tasks position
//    fun moveTaskView(from: Int, to: Int) {
//        val temp = taskList[from]
//        taskList.removeAt(from)
//        taskList.add(to, temp)
//    }
}