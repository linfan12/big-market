package com.lin.domain.strategy.service;

import com.lin.domain.strategy.model.entity.RaffleAwardEntity;
import com.lin.domain.strategy.model.entity.RaffleFactorEntity;

public interface IRaffleStrategy {
    /*执行抽奖动作*/
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity );
}
