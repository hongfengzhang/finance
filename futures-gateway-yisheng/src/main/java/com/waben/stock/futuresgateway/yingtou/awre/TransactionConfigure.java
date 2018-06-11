package com.waben.stock.futuresgateway.yingtou.awre;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 配置事务
 * 
 * @author luomengan
 *
 */
@EnableTransactionManagement
@EnableJpaRepositories(queryLookupStrategy = QueryLookupStrategy.Key.CREATE, basePackages = "com.waben.stock.futuresgateway.dao.impl.jpa")
@Configuration
public class TransactionConfigure {

}
