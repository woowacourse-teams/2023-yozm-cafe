package com.project.yozmcafe.controller.auth;

import com.project.yozmcafe.controller.dto.TokenResponse;
import com.project.yozmcafe.service.auth.AuthService;
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
                                               @PathVariable("providerName") final String providerName) {
        final TokenResponse tokenResponse = authService.login(code, providerName);
        return ResponseEntity.ok(tokenResponse);
    }
}
