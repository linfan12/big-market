package com.lin.domain.strategy.model.valobj;
import com.lin.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * 抽奖策略规则的规则值对象；值对象，没有唯一id，仅限从数据库查询对象
 */
public class StrategyAwardRuleModelVO {

    private String ruleModels;

}
