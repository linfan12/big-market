package com.lin.domain.strategy.service.raffle;

import com.lin.domain.strategy.model.entity.RaffleAwardEntity;
import com.lin.domain.strategy.model.entity.RaffleFactorEntity;
import com.lin.domain.strategy.model.entity.RuleActionEntity;
import com.lin.domain.strategy.model.entity.StrategyEntity;
import com.lin.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lin.domain.strategy.repository.IStrategyRepository;
import com.lin.domain.strategy.service.IRaffleStrategy;
import com.lin.domain.strategy.service.armory.IStrategyDispatch;
import com.lin.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.lin.types.enums.ResponseCode;
import com.lin.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 抽象策略的抽象类，用来实现一些公共的方法
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    //策略仓储服务 -> Domain层像是一个大厨， 仓储层提供米面粮油
    protected IStrategyRepository repository;
    //策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        //1.参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (strategyId == null || StringUtils.isBlank(userId)){
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        //2.规则查询
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);

        //3.抽奖前规则过滤,有几种过滤规则，
        // doCheckRaffleBeforeLogic的作用是进行检查规则检查
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = this.doCheckRaffleBeforeLogic(RaffleFactorEntity.builder().userId(userId).strategyId(strategyId).build(), strategy.ruleModels());

        if(RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())){
            if (DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(ruleActionEntity.getRuleModel())){
                //黑名单返回固定奖励
                return RaffleAwardEntity.builder()
                        //黑名单返回固定的奖励
                        .awardId(ruleActionEntity.getData().getAwardId())
                        .build();
            }else if (DefaultLogicFactory.LogicModel.RULE_WEIGHT.getCode().equals(ruleActionEntity.getRuleModel())){
                //权重根据返回的信息进行抽奖
                RuleActionEntity.RaffleBeforeEntity raffleBeforeEntity = ruleActionEntity.getData();
                String ruleWeightValueKey = raffleBeforeEntity.getRuleWeightValueKey();
                //根据返回的奖品范围随机获取
                Integer awardId = strategyDispatch.getRandomAwardId(strategyId, ruleWeightValueKey);
                return RaffleAwardEntity.builder()
                        .awardId(awardId)
                        .build();
            }
        }
        //4.默认抽奖流程，如果没有规则过滤就获取随机奖品
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();
    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity build, String ...logics);
}
