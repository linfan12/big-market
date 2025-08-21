package com.lin.test.infrastructure;


import com.alibaba.fastjson2.JSON;
import com.lin.infrastructure.dao.IRaffleActivitySkuDao;
import com.lin.infrastructure.dao.po.RaffleActivitySku;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 抽奖活动配置Dao测试
 * @create 2024-03-09 11:30
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivitySkuDaoTest {

    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    @Test
    public void test_queryRaffleActivityByActivityId() {
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(9011L);
        log.info("测试结果：{}", JSON.toJSONString(raffleActivitySku));
    }

}
