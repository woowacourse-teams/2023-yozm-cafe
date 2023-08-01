package com.project.yozmcafe.domain.auth.token;

import static com.project.yozmcafe.exception.ErrorCode.INVALID_OAUTH_USER_INFO;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.exception.OAuthException;

public abstract class OAuthToken {

    private static final String JWT_TOKEN_DELIMITER = "\\.";
    private static final String PAYLOADS_DELIMITER = ",";
    private static final String ENTRY_DELIMITER = "\"";

    private static final int PAYLOAD_INDEX = 1;
    private static final int VALUE_INDEX = 3;

    protected final String idToken;
    protected final String subject;
    protected final String name;
    protected final String image;

    protected OAuthToken(@JsonProperty("id_token") String idToken, String subject, String name, String image) {
        this.idToken = idToken;
        this.subject = subject;
        this.name = name;
        this.image = image;
    }

    public MemberInfo toUserInfo() {
        final List<String> payloads = getPayloads();

        String openId = parse(payloads, subject);
        String name = parse(payloads, this.name);
        String image = parse(payloads, this.image);

        return new MemberInfo(openId, name, image);
    }

    private List<String> getPayloads() {
        final String payload = idToken.split(JWT_TOKEN_DELIMITER)[PAYLOAD_INDEX];
        byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
        final String decoded = new String(decodedBytes);

        return Arrays.asList(decoded.split(PAYLOADS_DELIMITER));
    }

    private String parse(final List<String> payLoads, final String key) {
        final String entry = payLoads.stream()
                .filter(payload -> payload.contains(key))
                .findAny()
                .orElseThrow((() -> new OAuthException(INVALID_OAUTH_USER_INFO)));

        return entry.split(ENTRY_DELIMITER)[VALUE_INDEX];
    }
}
