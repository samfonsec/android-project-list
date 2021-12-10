package de.mycraftnote.code_challenge.ui.task.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.mycraftnote.code_challenge.data.project.ProjectRepository

class ProjectViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectsViewModel::class.java)) {
            return ProjectsViewModel(ProjectRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}