package com.example.boost.config.mybatis;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransaction;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class TestInterceptor implements InnerInterceptor {

    private static final ExecutorService countPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        System.out.println("beforeQueryInterceptor");
        Map<String, Object> map = (Map<String, Object>) parameter;
        Object countRunning = map.get("countRunning");
        if (countRunning != null) {
            System.out.println("skippingQueryInterceptor");
            return;
        }
        map.put("countRunning", true);
        String id = ms.getId();
        String countId = id + "_Count";
        Configuration configuration = ms.getConfiguration();
        MappedStatement.Builder builder = new MappedStatement.Builder(configuration, countId, ms.getSqlSource(), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(Collections.singletonList((new ResultMap.Builder(configuration, "mybatis-plus", Long.class, Collections.emptyList())).build()));
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        MappedStatement countMs = builder.build();
        String countSql = "select count(1) from user_login";
        PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
        BoundSql countBoundSql = new BoundSql(configuration, countSql, mpBoundSql.parameterMappings(), parameter);

        Environment environment = configuration.getEnvironment();
        TransactionFactory transactionFactory = environment.getTransactionFactory();
        if (transactionFactory == null) {
            transactionFactory = new ManagedTransactionFactory();
        }
        Transaction transaction = transactionFactory.newTransaction(environment.getDataSource(), null, false);
        ExecutorType executorType = configuration.getDefaultExecutorType();
        Executor countExecutor = configuration.newExecutor(transaction, executorType);
        // 异步执行
        Future<Long> future = countPool.submit(() -> {
            try {
                CacheKey cacheKey = countExecutor.createCacheKey(countMs, parameter, rowBounds, countBoundSql);
                List<Object> result = countExecutor.query(countMs, parameter, rowBounds, resultHandler, cacheKey, countBoundSql);
                long total = 0;
                if (CollectionUtils.isNotEmpty(result)) {
                    Object o = result.get(0);
                    if (o != null) {
                        total = Long.parseLong(o.toString());
                    }
                }
                return total;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                countExecutor.close(false);
            }
        });
        map.put("future", future);
        System.out.println("endQueryInterceptor");
    }
}
