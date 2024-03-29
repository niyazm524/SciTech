package models

import annotations.Alias
import annotations.Table
import misc.Validate

@Table("user")
data class User(
    val name: String,
    val username: String,
    val password: String,
    @Alias("phone_number") val phoneNumber: String? = null,
    val avatar: String? = null,
    val id: Int = 0
) {
    companion object {
        fun from(params: Map<String, String>) = object {
            val map = params.withDefault { null }
            val name by map
            val username by map
            val password by map
            val phoneNumber: String? by map
            val avatar: String? by map
            val data = User(name!!, username!!, password!!, phoneNumber!!, avatar)

            init {
                if (!Validate.phoneNumber(phoneNumber!!)) error("Проверьте номер телефона")
                if (!Validate.password(password!!)) error("В пароле должно содержаться минимум 8 символов")
                if (!Validate.username(username!!)) error("Введите валидный username")
            }
        }.data
    }
}