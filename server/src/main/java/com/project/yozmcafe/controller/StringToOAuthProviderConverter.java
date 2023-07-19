package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.auth.OAuthProvider;
import org.springframework.core.convert.converter.Converter;

public class StringToOAuthProviderConverter implements Converter<String, OAuthProvider> {

    @Override
    public OAuthProvider convert(final String source) {
        return OAuthProvider.from(source);
    }
}
