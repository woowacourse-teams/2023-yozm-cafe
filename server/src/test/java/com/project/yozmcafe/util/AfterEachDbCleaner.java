package com.project.yozmcafe.util;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class AfterEachDbCleaner implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        final ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        final DbCleaner dbCleaner = applicationContext.getBean(DbCleaner.class);
        dbCleaner.dbClear();
    }
}
