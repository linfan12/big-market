package com.lin.domain.strategy.service.armory;

/**
 * 策略装配库，负责初始化策略
 */
public interface IStrategyArmory {

    void assembleLotteryStrategy(Long strategyId);

    Integer getRandomAwardId(Long strategyId);

}
