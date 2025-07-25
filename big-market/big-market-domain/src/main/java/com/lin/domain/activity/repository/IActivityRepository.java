package com.lin.domain.activity.repository;

import com.lin.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.lin.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.lin.domain.activity.model.entity.*;
import com.lin.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;
import java.util.List;

/**
 * 活动仓储接口
 */
public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    void doSaveOrder(CreateQuotaOrderAggregate createOrderAggregate);

    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

    ActivitySkuStockKeyVO takeQueueValue();

    ActivitySkuStockKeyVO takeQueueValue(Long sku);

    void clearQueueValue();

    void clearQueueValue(Long sku);

    /**
     * 查询所有的sku
     * @return sku编号列表
     */
    List<Long> querySkuList();


    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);

    void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate);

    ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId);

    ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month);

    ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day);

    UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

    List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId);

    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);
}
