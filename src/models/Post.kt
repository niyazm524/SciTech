package models

import annotations.Alias
import annotations.Table

@Table("post")
data class Post(
    @Alias("author_id") val authorId: Int,
    val title: String,
    val body: String,
    @Alias("created_at") val createdAt: String,
    val id: Int = 0
) {
    companion object {
        fun from(params: Map<String, String>) = object {
            val map = params.withDefault { null }
            val authorId by map
            val title by map
            val body by map
            val data = Post(authorId!!.toInt(), title!!, body!!, "")
        }.data
    }
}