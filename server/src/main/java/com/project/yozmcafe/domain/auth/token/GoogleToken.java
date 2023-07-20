package com.project.yozmcafe.domain.auth.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleToken extends OAuthToken {

    private static final String SUBJECT = "sub";
    private static final String NAME = "name";
    private static final String PICTURE = "picture";

    public GoogleToken(@JsonProperty("id_token") String idToken) {
        super(idToken, SUBJECT, NAME, PICTURE);
    }
}
