package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.controller.auth.MemberInfo;
import com.project.yozmcafe.domain.auth.token.OAuthToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

public abstract class OAuthClient {

    private static final String CODE = "code";
    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String RESPONSE_TYPE = "response_type";
    private static final String SCOPE = "scope";

    private final static RestTemplate restTemplate = new RestTemplate();

    public MemberInfo getUserInfo(final String code) {
        final MultiValueMap<String, String> parameter = setParameters(code);

        OAuthToken oAuthToken = restTemplate.postForObject(URI.create(tokenUri()), parameter, getType());
        return Objects.requireNonNull(oAuthToken).toUserInfo();
    }

    private MultiValueMap<String, String> setParameters(final String code) {
        final MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add(CODE, code);
        parameter.add(GRANT_TYPE, "authorization_code");
        parameter.add(CLIENT_ID, clientId());
        parameter.add(CLIENT_SECRET, clientSecret());
        parameter.add(REDIRECT_URI, redirectUri());

        return parameter;
    }

    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromUriString(authUri())
                .queryParam(RESPONSE_TYPE, CODE)
                .queryParam(REDIRECT_URI, redirectUri())
                .queryParam(CLIENT_ID, clientId())
                .queryParam(SCOPE, scope())
                .build()
                .toUriString();
    }

    protected abstract String clientId();

    protected abstract String clientSecret();

    protected abstract String redirectUri();

    protected abstract String tokenUri();

    protected abstract String authUri();

    protected abstract String scope();

    protected abstract <T extends OAuthToken> Class<T> getType();
}
