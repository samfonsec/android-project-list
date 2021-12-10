package de.mycraftnote.code_challenge.ui.task.projects

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import de.mycraftnote.code_challenge.databinding.ProjectListItemBinding
import de.mycraftnote.code_challenge.ui.firstLetter
import de.mycraftnote.code_challenge.ui.hide
import de.mycraftnote.code_challenge.ui.onlyName
import de.mycraftnote.code_challenge.ui.toMilliseconds

class ProjectAdapter(private val projects: List<Project>) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    private val onItemClicked = MutableLiveData<Long?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProjectListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = projects.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        with(holder) {
            bind(project)
            itemView.setOnClickListener { onItemClicked.postValue(project.lastOpenedDate.toMilliseconds()) }
        }
    }

    fun onItemClicked(): LiveData<Long?> = onItemClicked

    class ViewHolder(private val binding: ProjectListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project) {
            with(binding) {
                with(project) {
                    projectName.text = name.onlyName()
                    initial.text = name.firstLetter()
                    if (street.isNullOrEmpty())
                        projectAddress.hide()
                    else
                        projectAddress.text = street
                }
            }
        }
    }
}