package org.itmo.rnbbackend.utils

import io.github.cdimascio.dotenv.Dotenv
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.SIG
import io.jsonwebtoken.security.Keys
import org.itmo.rnbbackend.entity.UserEntity
import org.itmo.rnbbackend.services.UserService
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JwtUtil(@Lazy private val userService: UserService) {
    private val env = Dotenv.load()
    private val secret = env.get("JWT_SECRET")
    private val expiration = env.get("JWT_EXPIRATION")

    fun generateToken(user: UserEntity): String {
        val claims: MutableMap<String, Any> = mutableMapOf()
        println(user.toString())
        user.id?.let { claims["id"] = it }
        claims["email"] = user.email

        return Jwts.builder()
            .claims(claims)
            .issuedAt(Date())
            .expiration(Date.from(Instant.now().plusSeconds(expiration.toLong())))
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), SIG.HS512)
            .compact()
    }

    fun verifyToken(token: String): Boolean {
        val claims = getClaims(token)

        return claims?.expiration?.after(Date()) == true
    }

    fun getUserFromToken(token: String): UserEntity? {
        val claims = getClaims(token)
        val user = userService.getByEmail(claims?.get("email", String::class.java) ?: "")

        return user
    }

    private fun getClaims(token: String): Claims? {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .build()
            .parseSignedClaims(token)
            .payload
    }
}

