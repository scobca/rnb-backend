package org.itmo.rnbbackend.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.itmo.rnbbackend.utils.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtRequestFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        var jwtToken: String? = null

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7)

            val user = jwtUtil.getUserFromToken(jwtToken)
            val authorities = emptyList<SimpleGrantedAuthority>()

            if (SecurityContextHolder.getContext().authentication == null && jwtUtil.verifyToken(jwtToken)) {
                val authReq = UsernamePasswordAuthenticationToken(user?.email, null, authorities)
                SecurityContextHolder.getContext().authentication = authReq
            }
        }

        filterChain.doFilter(request, response);
    }

}