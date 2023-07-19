package com.project.yozmcafe.controller.auth;

import com.project.yozmcafe.controller.dto.TokenResponse;
import com.project.yozmcafe.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/{providerName}")
    public ResponseEntity<TokenResponse> login(@RequestParam("code") final String code,
                                               @PathVariable("providerName") final String providerName,
                                               final HttpServletResponse response) {
        final TokenResponse accessTokenResponse = authService.createAccessToken(code, providerName);
        final TokenResponse refreshTokenResponse = authService.createRefreshToken();
        final Cookie cookie = new Cookie("refreshToken", refreshTokenResponse.token());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(accessTokenResponse);
    }
}
