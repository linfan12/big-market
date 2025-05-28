package com.lin.test;

import com.lin.infrastructure.persistent.dao.IAwardDao;
import com.lin.infrastructure.persistent.dao.IStrategyAwardDao;
import com.lin.infrastructure.persistent.dao.IStrategyDao;
import com.lin.infrastructure.persistent.dao.IStrategyRuleDao;
import com.lin.infrastructure.persistent.po.Award;
import com.lin.infrastructure.persistent.po.Strategy;
import com.lin.infrastructure.persistent.po.StrategyAward;
import com.lin.infrastructure.persistent.po.StrategyRule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    IAwardDao iAwardDao;

    @Resource
    IStrategyDao iStrategyDao;

    @Resource
    IStrategyAwardDao iStrategyAwardDao;

    @Resource
    IStrategyRuleDao iStrategyRuleDao;
    @Test
    public void test() {
        log.info("测试完成");
    }

    @Test
    public void testAwardDao() {
        log.info("AwardDao测试开始");
        for (Award award : iAwardDao.queryAwardList()) {
            System.out.println(award);
        }
    }

    @Test
    public void testStrategyDao() {
        log.info("StrategyDao测试开始");
        for (Strategy strategy : iStrategyDao.queryStrategyList()) {
            System.out.println(strategy);
        }
    }

    @Test
    public void testStrategyAwardDao() {
        log.info("StrategyAwardDao测试开始");
        for (StrategyAward strategyAward : iStrategyAwardDao.queryStrategyAwardList()) {
            System.out.println(strategyAward);
        }
    }

    @Test
    public void testStrategyRuleDao() {
        log.info("StrategyRuleDao测试开始");
        for (StrategyRule strategyRule : iStrategyRuleDao.queryStrategyRuleList()) {
            System.out.println(strategyRule);
        }

    }


}
