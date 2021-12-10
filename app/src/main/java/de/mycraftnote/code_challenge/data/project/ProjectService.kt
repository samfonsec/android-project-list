package de.mycraftnote.code_challenge.data.project

import de.mycraftnote.code_challenge.data.model.ProjectList
import retrofit2.Response
import retrofit2.http.GET


interface ProjectService {

    @GET(GET_PROJECTS_URL)
    suspend fun getProjects(): Response<ProjectList>

    companion object {
        private const val GET_PROJECTS_URL = "projects"
    }
}