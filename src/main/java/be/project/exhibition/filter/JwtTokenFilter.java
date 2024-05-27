package be.project.exhibition.filter;

import be.project.exhibition.dto.UserDto;
import be.project.exhibition.service.UserService;
import be.project.exhibition.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String key;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get token from cookie
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("AUTHORIZATION".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        // get header
        if (token == null) {
            log.error("Error occurs while getting token. Token is not available in cookie", request.getRequestURL());
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if(JwtTokenUtils.isExpired(token, key)) {
                log.error("Token is expired");
                filterChain.doFilter(request, response);
                return;
            }

            // get username from token
            String userId = JwtTokenUtils.getName(token, key);
            // check the userName is valid
            UserDto user = userService.loadUserByUserName(userId);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user,null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException e) {
            log.error("Error occurs while validating. {}", e.toString());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
