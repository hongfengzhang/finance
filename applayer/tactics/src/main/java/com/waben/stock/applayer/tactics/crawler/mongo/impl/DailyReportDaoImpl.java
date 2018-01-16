package com.waben.stock.applayer.tactics.crawler.mongo.impl;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.waben.stock.applayer.tactics.crawler.model.news.DailyReportModel;
import com.waben.stock.applayer.tactics.crawler.mongo.DailyReportDao;

/**
 * Created by qmw on 2017/10/26.
 */
@Repository
public class DailyReportDaoImpl implements DailyReportDao {
    public static final String DAILY_REPORT = "lm_news_dailyReport";
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public String saveReport(DailyReportModel dailyReport) {
        dailyReport.setId(new ObjectId().toString());
        mongoTemplate.save(dailyReport, DAILY_REPORT);
        redisTemplate.opsForHash().put(DailyReportModel.DAILY_REPORT_CLICKS_REDISKEY,dailyReport.getId(),0);
        return dailyReport.getId();
    }



    @PostConstruct
    public void init() {

        // 添加索引
        mongoTemplate.indexOps(DAILY_REPORT).ensureIndex(new Index("type", Sort.Direction.ASC));
        mongoTemplate.indexOps(DAILY_REPORT).ensureIndex(new Index("createTime", Sort.Direction.ASC));



    }
}
