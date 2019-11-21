package controllers

import models.Post
import plugins.pug.render
import routing.Router

class PostDetailsController : Router() {
    override val routes = routing {
        get("/") {
            render(
                "post_det", hashMapOf(
                    "post" to
                            Post(1, "Название поста", "Длинный текст поста", "7 июня")

                )
            )
        }
    }
}