package com.lin.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 抽奖请求参数
 * @create 2024-02-14 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaffleStrategyRequestDTO {

    // 抽奖策略ID
    private Long strategyId;

}
