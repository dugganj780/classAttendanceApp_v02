package org.wit.classattendanceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.classattendanceapp.activities.ModuleActivity
import org.wit.classattendanceapp.databinding.CardLectureBinding
import org.wit.classattendanceapp.models.LectureModel

interface LectureListener {
    fun onLectureClick(lecture: LectureModel)
}

class LectureAdapter(private var lectures: List<LectureModel>, private val listener: LectureListener) :
    RecyclerView.Adapter<LectureAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardLectureBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val lecture = lectures.get(holder.adapterPosition)
        if (lecture != null) {
            holder.bind(lecture, listener)
        }
    }

    override fun getItemCount(): Int = lectures.size

    class MainHolder(private val binding : CardLectureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lecture: LectureModel, listener: LectureListener) {
            var lectureTimes = lecture.startTime +" to "+lecture.endTime


            binding.day.text = lecture.day
            binding.lectureTimes.text = lectureTimes
            binding.lectureLocation.text = lecture.location
            binding.root.setOnClickListener{listener.onLectureClick(lecture)}
        }
    }

}