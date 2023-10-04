package com.project.yozmcafe.service;


import org.springframework.boot.test.mock.mockito.SpyBean;

import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.domain.S3Client;
import com.project.yozmcafe.service.auth.GoogleOAuthClient;

public abstract class BaseServiceTest extends BaseTest {
    @SpyBean
    protected GoogleOAuthClient googleOAuthClient;
    @SpyBean
    protected S3Client s3Client;
}
