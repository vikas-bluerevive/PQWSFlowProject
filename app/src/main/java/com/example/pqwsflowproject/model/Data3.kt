package com.example.pqwsflowproject.model

data class Data3(
                 val number: Int? = null,
                 val last: Boolean? = null,
                 val size: Int? = null,
                 val numberOfElements: Int? = null,
                 val totalPages: Int? = null,
                 val pageable: Pageable? = null,
                 val sort: Sort? = null,
                 val content: List<ContentItem3?>? = null,
                 val first: Boolean? = null,
                 val totalElements: Int? = null,
                 val empty: Boolean? = null
)
