package com.lin.domain.strategy.model.entity;

import com.lin.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategyEntity {
    /*抽奖策略ID*/
    private Long strategyId;
    /*抽奖策略描述*/
    private String strategyDesc;
    /*抽奖模型规则*/
    private String ruleModels;

    public String[] ruleModels(){
        if (StringUtils.isBlank(ruleModels)) return null;
        return ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight(){
        String[] ruleModels = this.ruleModels();
        for (String ruleModel : ruleModels) {
            if ("rule_weight".equals(ruleModel)) return ruleModel;
        }
        return null;
    }
}
