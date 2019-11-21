package misc

import java.util.regex.Pattern

object Validate {
    private val PHONE_NUMBER_PATTERN = Pattern.compile("^[+]?[0-9]{10,13}\$")
    private val USERNAME_PATTERN = Pattern.compile("^(?=.{8,20}\$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])\$")

    fun phoneNumber(phone: String) = PHONE_NUMBER_PATTERN.asPredicate().test(phone)

    fun username(username: String) = USERNAME_PATTERN.asPredicate().test(username)

    fun password(password: String) = password.length in 8..16

}