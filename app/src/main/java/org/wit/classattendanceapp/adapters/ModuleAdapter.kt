package org.wit.classattendanceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.classattendanceapp.databinding.CardModuleBinding
import org.wit.classattendanceapp.models.ModuleModel

interface ModuleListener {
    fun onModuleClick(module: ModuleModel)
}

class ModuleAdapter constructor(private var modules: List<ModuleModel>, private val listener: ModuleListener) :
    RecyclerView.Adapter<ModuleAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardModuleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val module = modules[holder.adapterPosition]
        holder.bind(module, listener)
    }

    override fun getItemCount(): Int = modules.size

    class MainHolder(private val binding : CardModuleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(module: ModuleModel, listener: ModuleListener) {
            binding.moduleCode.text = module.moduleCode
            binding.moduleTitle.text = module.title
            binding.root.setOnClickListener{listener.onModuleClick(module)}
        }
    }

}