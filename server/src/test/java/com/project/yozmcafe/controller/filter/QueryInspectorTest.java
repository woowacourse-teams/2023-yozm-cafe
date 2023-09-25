package com.project.yozmcafe.controller.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.context.request.RequestContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
class QueryInspectorTest {

    private final QueryCounter queryCounter = mock(QueryCounter.class);
    private final QueryInspector queryInspector = new QueryInspector(queryCounter);
    @Autowired
    MockHttpServletRequest request;

    @Test
    @DisplayName("리퀘스트 범위가 아니면 쿼리 카운팅이 동작하지 않는다")
    void inspect_notWorking() {
        //given
        RequestContextHolder.resetRequestAttributes();

        //when
        queryInspector.inspect("sql");

        //then
        verify(queryCounter, times(0)).increase();
    }

    @Test
    @DisplayName("리퀘스트 범위에서는 쿼리 카운팅이 동작한다")
    void inspect_working() {
        new MockHttpServletRequest();
        //given
        when(queryCounter.getCount()).thenReturn(1);

        //when
        queryInspector.inspect("sql");

        //then
        verify(queryCounter, times(1)).increase();
    }
}
