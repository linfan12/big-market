package com.lin.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 抽奖请求参数
 * @create 2024-02-14 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaffleRequestDTO {

    // 抽奖策略ID
    private Long strategyId;

}
