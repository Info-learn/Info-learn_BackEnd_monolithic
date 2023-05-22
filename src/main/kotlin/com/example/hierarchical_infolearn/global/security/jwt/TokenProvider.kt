package com.example.hierarchical_infolearn.global.security.jwt

import com.example.hierarchical_infolearn.global.security.jwt.data.TokenResponse
import com.example.hierarchical_infolearn.global.security.jwt.env.JwtProperty
import com.example.hierarchical_infolearn.global.security.jwt.exception.ExpiredTokenException
import com.example.hierarchical_infolearn.global.security.jwt.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenProvider(
    private val jwtProperty: JwtProperty,
) {
    fun encode(accountId: String, role: String, tokenUUID: String): TokenResponse {
        val accessToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
            .setSubject(role)
            .setId(accountId)
            .claim("type", "access")
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + (jwtProperty.accessExpiredAt * 1000)))
            .compact() /*+ ".${tokenUUID}"*/
        val refreshToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtProperty.secretKey)
            .claim("type", "refresh")
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + (jwtProperty.refreshExpiredAt * 1000)))
            .compact()

        return TokenResponse(
            accessToken,
            refreshToken
        )
    }

    fun decodeBody(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(jwtProperty.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException) {
            throw InvalidTokenException(e.message.toString())
        }
    }

    fun getSubjectWithExpiredCheck(token: String): Pair<String, String>? {
        val body = decodeBody(token)
        body.subject?: throw InvalidTokenException(body["type"].toString())
        val now = Date()
        if (now.after(Date(now.time + body.expiration.time))) throw ExpiredTokenException(token)
        return Pair(body.subject , body.id)
    }

}