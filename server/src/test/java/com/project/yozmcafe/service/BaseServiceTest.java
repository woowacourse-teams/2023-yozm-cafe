package com.project.yozmcafe.service;


import com.project.yozmcafe.BaseTest;
import com.project.yozmcafe.domain.S3Client;
import com.project.yozmcafe.service.auth.GoogleOAuthClient;
import org.springframework.boot.test.mock.mockito.SpyBean;

public abstract class BaseServiceTest extends BaseTest {

    @SpyBean
    protected S3Client s3Client;

    @SpyBean
    protected GoogleOAuthClient googleOAuthClient;
}
