package com.lin.trigger.job;


import com.lin.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.lin.domain.activity.service.IRaffleActivitySkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 更新活动sku库存任务
 * @create 2024-03-30 09:52
 */
@Slf4j
@Component()
public class UpdateActivitySkuStockJob {

    @Resource
    private IRaffleActivitySkuStockService skuStock;
    @Resource
    private ThreadPoolExecutor executor;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            // log.info("定时任务，更新活动sku库存【延迟队列获取，降低对数据库的更新频次，不要产生竞争】");
            List<Long> skuList = skuStock.querySkuList();
            for (Long sku : skuList) {
                executor.execute(() -> {
                    ActivitySkuStockKeyVO activitySkuStockKeyVO = null;
                    try {
                        activitySkuStockKeyVO = skuStock.takeQueueValue(sku);
                    } catch (InterruptedException e) {
                        log.error("定时任务，更新活动sku库存失败 userId: {} topic: {}", activitySkuStockKeyVO.getSku(), activitySkuStockKeyVO.getActivityId());
                    }
                    if (null == activitySkuStockKeyVO) return;
                    log.info("定时任务，更新活动sku库存 sku:{} activityId:{}", activitySkuStockKeyVO.getSku(), activitySkuStockKeyVO.getActivityId());
                    skuStock.updateActivitySkuStock(activitySkuStockKeyVO.getSku());
                });
            }

        } catch (Exception e) {
            log.error("定时任务，更新活动sku库存失败", e);
        }
    }

}
