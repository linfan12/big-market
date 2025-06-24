package com.lin.domain.strategy.service.rule.chain;

import org.springframework.aop.target.PrototypeTargetSource;

import javax.rmi.PortableRemoteObject;

public abstract class AbstractLogicChain implements ILogicChain{

    private  ILogicChain next;

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    @Override
    public ILogicChain next() {
        return next;
    }

    protected abstract String ruleModel();
}
