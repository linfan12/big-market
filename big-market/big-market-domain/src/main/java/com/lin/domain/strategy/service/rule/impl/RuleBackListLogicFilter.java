package com.lin.domain.strategy.service.rule.impl;

import com.lin.domain.strategy.model.entity.RuleActionEntity;
import com.lin.domain.strategy.model.entity.RuleMatterEntity;
import com.lin.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.lin.domain.strategy.repository.IStrategyRepository;
import com.lin.domain.strategy.service.annotation.LogicStrategy;
import com.lin.domain.strategy.service.rule.ILogicFilter;
import com.lin.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.lin.types.common.Constants;
import com.lin.types.enums.ResponseCode;
import javafx.util.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_BLACKLIST)
public class RuleBackListLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {

    @Resource
    IStrategyRepository repository;

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {

        log.info("规则过滤-黑名单 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(),ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        String userId = ruleMatterEntity.getUserId();

        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(),ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());
        //100:user001,user002,user003
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        //["100", "user001,user002,user003"]
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        //100:user001,user002,user003
        //过滤其他规则
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                        .ruleModel(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode())
                        .data(RuleActionEntity.RaffleBeforeEntity.builder()
                                .strategyId(ruleMatterEntity.getStrategyId())
                                .awardId(awardId)
                                .build())
                        .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                        .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                        .build();
            }
        }


        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }
}
