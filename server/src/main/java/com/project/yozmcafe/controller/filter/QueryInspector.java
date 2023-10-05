package com.project.yozmcafe.controller.filter;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import static java.util.Objects.nonNull;

@Component
public class QueryInspector implements StatementInspector {

    private final QueryCounter queryCounter;

    public QueryInspector(final QueryCounter queryCounter) {
        this.queryCounter = queryCounter;
    }

    @Override
    public String inspect(final String sql) {
        if (inRequestScope()) {
            queryCounter.increase();
        }

        return sql;
    }

    private boolean inRequestScope() {
        return nonNull(RequestContextHolder.getRequestAttributes());
    }

    public int getQueryCount() {
        return queryCounter.getCount();
    }
}
