package de.deutschebank.recruiting.backends.shared.strings.domain

import java.security.MessageDigest

class StringUtils() {

    companion object {

        fun generateShortUrl(originalUrl: String): String  {
            return MessageDigest.getInstance("SHA-256")
                .digest(originalUrl.toByteArray())
                .joinToString("") { byte -> "%02x".format(byte) }
                .substring(0, 8)
        }

    }

}
