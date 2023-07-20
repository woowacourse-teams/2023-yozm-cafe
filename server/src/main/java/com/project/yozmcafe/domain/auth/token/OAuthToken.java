package com.project.yozmcafe.domain.auth.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.yozmcafe.controller.auth.MemberInfo;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public abstract class OAuthToken {

    private static final String JWT_TOKEN_DELIMITER = "\\.";
    private static final String PAYLOADS_DELIMITER = ",";
    private static final String ENTRY_DELIMITER = "\"";

    private static final int PAYLOAD_INDEX = 1;
    private static final int VALUE_INDEX = 3;

    protected final String idToken;
    protected final String SUBJECT;
    protected final String NAME;
    protected final String IMAGE;

    protected OAuthToken(@JsonProperty("id_token") String idToken, String subject, String name, String image) {
        this.idToken = idToken;
        SUBJECT = subject;
        NAME = name;
        IMAGE = image;
    }

    public MemberInfo toUserInfo() {
        final List<String> payloads = getPayloads();

        String openId = parse(payloads, SUBJECT);
        String name = parse(payloads, NAME);
        String image = parse(payloads, IMAGE);

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
                .orElseThrow();

        return entry.split(ENTRY_DELIMITER)[VALUE_INDEX];
    }
}
