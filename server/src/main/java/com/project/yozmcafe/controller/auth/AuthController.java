package com.project.yozmcafe.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.yozmcafe.controller.dto.TokenResponse;
import com.project.yozmcafe.service.auth.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String AUTHORIZATION = "Authorization";

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
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @GetMapping
    public ResponseEntity<TokenResponse> refreshToken(final HttpServletRequest request,
                                                      final HttpServletResponse response,
                                                      @CookieValue(name = REFRESH_TOKEN) final String refreshToken) {
        final String accessToken = request.getHeader(AUTHORIZATION);
        final TokenResponse tokenResponse = authService.refreshAccessToken(accessToken, refreshToken);
        setRefreshTokenCookie(response);
        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/{providerName}")
    public String redirectAuthorizationUri(@PathVariable("providerName") final OAuthProvider oAuthProvider) {
        final String authUri = authService.getAuthorizationUri(oAuthProvider);

        return "redirect:" + authUri;
    }
}
