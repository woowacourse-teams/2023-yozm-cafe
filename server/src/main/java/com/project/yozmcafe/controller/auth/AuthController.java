package com.project.yozmcafe.controller.auth;

import com.project.yozmcafe.controller.dto.AuthorizationUrlDto;
import com.project.yozmcafe.controller.dto.TokenResponse;
import com.project.yozmcafe.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 14;

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/{providerName}")
    public ResponseEntity<TokenResponse> login(@RequestParam("code") final String code,
                                               @PathVariable("providerName") final OAuthProvider oAuthProvider,
                                               final HttpServletResponse response) {
        final TokenResponse accessTokenResponse = authService.createAccessToken(code, oAuthProvider);
        setRefreshTokenCookie(response);
        return ResponseEntity.ok(accessTokenResponse);
    }

    private void setRefreshTokenCookie(final HttpServletResponse response) {
        final TokenResponse refreshTokenResponse = authService.createRefreshToken();

        final Cookie cookie = new Cookie(REFRESH_TOKEN, refreshTokenResponse.token());
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @GetMapping
    public ResponseEntity<TokenResponse> refreshToken(final HttpServletRequest request,
                                                      final HttpServletResponse response,
                                                      @CookieValue(name = REFRESH_TOKEN) final String refreshToken) {
        final String accessToken = request.getHeader(AUTHORIZATION).replace(BEARER, "");
        final TokenResponse tokenResponse = authService.refreshAccessToken(accessToken, refreshToken);
        setRefreshTokenCookie(response);
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/urls")
    public ResponseEntity<List<AuthorizationUrlDto>> getRedirectAuthorizationUrls() {
        final List<AuthorizationUrlDto> authorizationUrls = authService.getAuthorizationUrls();

        return ResponseEntity.ok(authorizationUrls);
    }

    @DeleteMapping
    public ResponseEntity<Void> logout(final HttpServletResponse response) {
        final Cookie cookie = new Cookie(REFRESH_TOKEN, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
