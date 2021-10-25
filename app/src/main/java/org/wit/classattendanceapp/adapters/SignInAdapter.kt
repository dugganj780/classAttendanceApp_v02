package org.wit.classattendanceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.classattendanceapp.activities.ModuleActivity
import org.wit.classattendanceapp.databinding.ActivitySigninBinding
import org.wit.classattendanceapp.models.SignInModel

/*
interface LectureListener {
    fun onLectureClick(lecture: LectureModel)
}

 */

class SignInAdapter(private var attendance: List<SignInModel>) :
    RecyclerView.Adapter<SignInAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = ActivitySigninBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val signIn = attendance.get(holder.adapterPosition)
        if (signIn != null) {
            holder.bind(signIn)
        }
    }

    override fun getItemCount(): Int = attendance.size

    class MainHolder(private val binding : ActivitySigninBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var status:String = ""

        fun bind(signIn: SignInModel) {
            if (signIn.inPerson){
                status="In Person"
            }

            if(signIn.live){
                status = "Live Online"
            }

            if (signIn.recording){
                status = "Recording"
            }


            binding.moduleCode.text = signIn.moduleCode
            binding.day.text = signIn.day
            binding.surname.text = signIn.surname
            binding.firstName.text = signIn.firstName
            binding.startTime.text = signIn.startTime
            binding.signInTime.text = signIn.signTime
            binding.status.text = status
        }
    }

}