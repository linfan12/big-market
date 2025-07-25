package com.lin.domain.activity.service.quota.rule.factory;

import com.lin.domain.activity.service.quota.rule.IActionChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 责任链工厂
 */
@Service
public class DefaultActivityChainFactory {

    private IActionChain actionChain;

    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainGroup) {
        actionChain = actionChainGroup.get(ActionModel.activity_base_action.code);
        actionChain.appendNext(actionChainGroup.get(ActionModel.activity_sku_stack_action.getCode()));
    }

    public IActionChain openActionChain(){
        return this.actionChain;
    }

    @Getter
    @AllArgsConstructor
    public enum ActionModel{

        activity_base_action("activity_base_action", "活动的库存、时间状态校验"),
        activity_sku_stack_action("activity_sku_stack_action","活动sku库存"),
        ;

        private final String code;
        private final String info;
    }
}
