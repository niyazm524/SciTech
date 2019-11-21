package controllers

import models.Post
import plugins.pug.render
import routing.Router

class HomeController : Router() {
    override val routes = routing {
        get("/") {
            render(
                "index", hashMapOf(
                    "posts" to listOf<Post>(
                        Post(1, "Название поста", "Длинный текст поста", "7 июня")
                    )
                )
            )
        }
    }
}