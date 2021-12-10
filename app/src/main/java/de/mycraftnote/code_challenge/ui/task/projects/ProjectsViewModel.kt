package de.mycraftnote.code_challenge.ui.task.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.mycraftnote.code_challenge.data.project.ProjectRepository
import kotlinx.coroutines.launch

class ProjectsViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    private val projectResult = MutableLiveData<ProjectResult>()
    private val showLoading = MutableLiveData<Boolean>()

    fun onProjectResult(): LiveData<ProjectResult> = projectResult
    fun onShowLoading(): LiveData<Boolean> = showLoading

    fun getProjects() {
        showLoading.value = true
        viewModelScope.launch {
            projectRepository.getProjects().run {
                val result = if (isSuccessful) {
                    ProjectResult(success = body()?.projects?.filterFolders()?.sortedBy { it.creationDate })
                } else {
                    ProjectResult(error = message())
                }
                projectResult.value = result
            }
            showLoading.value = false
        }
    }

    private fun List<Project>.filterFolders() = filter { it.projectType == FOLDER_TYPE }

    companion object {
        private const val FOLDER_TYPE = "FOLDER"
    }
}