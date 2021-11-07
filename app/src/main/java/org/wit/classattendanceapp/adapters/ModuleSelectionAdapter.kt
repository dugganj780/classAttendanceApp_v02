package org.wit.classattendanceapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.classattendanceapp.databinding.CardModuleSelectionBinding
import org.wit.classattendanceapp.models.ModuleModel

interface ModuleSelectionListener {
    fun onModuleCheck(module: ModuleModel)
}

class ModuleSelectionAdapter constructor(private var modules: List<ModuleModel>, private val listener: ModuleSelectionListener) :
    RecyclerView.Adapter<ModuleAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardModuleSelectionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val module = modules[holder.adapterPosition]
        holder.bind(module, listener)
    }

    override fun getItemCount(): Int = modules.size

    class MainHolder(private val binding : CardModuleSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(module: ModuleModel, listener: ModuleSelectionListener) {
            binding.moduleCode.text = module.moduleCode
            binding.root.setOnClickListener{listener.onModuleCheck(module)}
        }
    }
}