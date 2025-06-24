package com.lin.test.domain;

import com.alibaba.fastjson.JSON;
import com.lin.domain.strategy.model.entity.RaffleAwardEntity;
import com.lin.domain.strategy.model.entity.RaffleFactorEntity;
import com.lin.domain.strategy.service.armory.IStrategyArmory;
import com.lin.domain.strategy.service.rule.chain.ILogicChain;
import com.lin.domain.strategy.service.rule.chain.Impl.RuleWeightLogicChain;
import com.lin.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * 用于责任链抽奖，验证不同的规则走不同的责任链
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicChainTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;

    @Resource
    private DefaultChainFactory defaultChainFactory;

    @Before
    public void setUp(){
        //策略装配100001，100002，100003
        log.info("100001策略装配测试结果：{}",strategyArmory.assembleLotteryStrategy(100001L));
        log.info("100002策略装配测试结果：{}",strategyArmory.assembleLotteryStrategy(100002L));
        log.info("100003策略装配测试结果：{}",strategyArmory.assembleLotteryStrategy(100003L));

    }

    /**
     * 黑名单测试
     */
    @Test
    public void test_LogicChain_rule_blacklist(){
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        DefaultChainFactory.StrategyAwardVO user001 = logicChain.logic("user001", 100001L);
        log.info("测试结果是：{}",user001);
    }

    /**
     * 权重测试
     */
    @Test
    public void test_LogicChain_rule_weight(){
        ReflectionTestUtils.setField(ruleWeightLogicChain,"userScore", 4900L);

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        DefaultChainFactory.StrategyAwardVO lin = logicChain.logic("lin", 100001L);
        log.info("测试结果是：{}",lin);
    }

    @Test
    public void test_LogicChain_rule_default(){
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        DefaultChainFactory.StrategyAwardVO lin = logicChain.logic("lin", 100001L);
        log.info("测试结果是：{}",lin);
    }
}
