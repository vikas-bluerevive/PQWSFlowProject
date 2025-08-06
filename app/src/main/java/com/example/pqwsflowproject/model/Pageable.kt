package com.example.pqwsflowproject.model

data class Pageable(
    val paged: Boolean? = null,
    val pageNumber: Int? = null,
    val offset: Int? = null,
    val pageSize: Int? = null,
    val unpaged: Boolean? = null,
    val sort: Sort? = null
)
