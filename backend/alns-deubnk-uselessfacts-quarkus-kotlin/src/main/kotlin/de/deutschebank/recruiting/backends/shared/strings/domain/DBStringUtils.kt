package de.deutschebank.recruiting.backends.shared.strings.domain

import jakarta.enterprise.context.ApplicationScoped
import java.security.MessageDigest

@ApplicationScoped
class DBStringUtils() {

    fun generateShortUrl(originalUrl: String): String  {
        return MessageDigest.getInstance("SHA-256")
            .digest(originalUrl.toByteArray())
            .joinToString("") { byte -> "%02x".format(byte) }
            .substring(0, 8)
    }
}
