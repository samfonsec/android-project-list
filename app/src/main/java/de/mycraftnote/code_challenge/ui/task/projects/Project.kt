package de.mycraftnote.code_challenge.ui.task.projects

data class Project(
    val id: String,
    val name: String,
    val creationDate: Long,
    val endDate: Long,
    val street: String?,
    val zipcode: String,
    val projectType: String,
    var lastOpenedDate: Long
)