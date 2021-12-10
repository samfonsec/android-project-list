package de.mycraftnote.code_challenge.ui.task.projects

data class ProjectResult(
    val success: List<Project>? = null,
    val error: String? = null
)