package com.lin.test.domain;

import com.alibaba.fastjson.JSON;
import com.lin.domain.strategy.model.entity.RaffleAwardEntity;
import com.lin.domain.strategy.model.entity.RaffleFactorEntity;
import com.lin.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.lin.domain.strategy.service.IRaffleStock;
import com.lin.domain.strategy.service.IRaffleStrategy;
import com.lin.domain.strategy.service.armory.IStrategyArmory;
import com.lin.domain.strategy.service.rule.chain.Impl.RuleWeightLogicChain;
import com.lin.domain.strategy.service.rule.tree.impl.RuleLockLogicTreeNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class RaffleStrategyTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;

    @Resource
    private RuleLockLogicTreeNode ruleLockLogicTreeNode;

    @Resource
    private IRaffleStock raffleStock;

    @Before
    public void setUp(){
        //策略装配100001，100002，100003
//        log.info("100001策略装配测试结果：{}",strategyArmory.assembleLotteryStrategy(100001L));
//        log.info("100002策略装配测试结果：{}",strategyArmory.assembleLotteryStrategy(100002L));
//        log.info("100003策略装配测试结果：{}",strategyArmory.assembleLotteryStrategy(100003L));
        log.info("100006策略装配测试结果：{}",strategyArmory.assembleLotteryStrategy(100006L));

        ReflectionTestUtils.setField(ruleWeightLogicChain,"userScore", 4500L);
        ReflectionTestUtils.setField(ruleLockLogicTreeNode, "userRaffleCount", 10L);
    }

    /**
     * 权重测试
     */
    @Test
    public void test_performRaffle() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .userId("xiaofuge")
                    .strategyId(100006L)
                    .build();

            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
        }

        // 等待 UpdateAwardStockJob 消费队列
        new CountDownLatch(1).await();
    }

    /**
     * 黑名单测试
     */
    @Test
    public void test_performRaffle_blacklist(){
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("user003")
                .strategyId(100006L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}",JSON.toJSONString(raffleAwardEntity));
    }

    /**
     * 抽奖次数校验，抽奖n次后解锁，100003策略，可以通过@Before中的setUp方法中个人抽奖次数来验证。
     */
    @Test
    public void test_raffle_center_rule_lock(){
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("lin")
                .strategyId(100006L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}",JSON.toJSONString(raffleAwardEntity));
    }

    @Test
    public void test_takeQueueValue() throws InterruptedException {
        StrategyAwardStockKeyVO strategyAwardStockKeyVO = raffleStock.takeQueueValue();
        log.info("测试结果：{}", JSON.toJSONString(strategyAwardStockKeyVO));
    }
}
