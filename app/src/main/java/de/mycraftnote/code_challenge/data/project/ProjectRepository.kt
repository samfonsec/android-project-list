package de.mycraftnote.code_challenge.data.project

import de.mycraftnote.code_challenge.data.model.ProjectList
import de.mycraftnote.code_challenge.data.project.ApiClient.projectService
import retrofit2.Response


class ProjectRepository {

    suspend fun getProjects(): Response<ProjectList> = projectService.getProjects()

}