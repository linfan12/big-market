package com.lin.domain.strategy.service.annotation;

import com.lin.domain.strategy.service.rule.factory.DefaultLogicFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解标注规则过滤齐rule_weight和rule_blacklist
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicStrategy {

    //logicMode()表示这个注解的一个属性是DefaultLogicFactory.LogicModel
    DefaultLogicFactory.LogicModel logicMode();
}
