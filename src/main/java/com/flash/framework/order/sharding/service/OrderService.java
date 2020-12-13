package com.flash.framework.order.sharding.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flash.framework.mybatis.service.BaseService;
import com.flash.framework.order.sharding.model.entity.Order;
import com.flash.framework.order.sharding.model.request.OrderPageRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zhurg
 * @since 2020-11-24
 */
public interface OrderService extends BaseService<Order> {

    Page<Order> page(OrderPageRequest request);

    Order findByIdWithLine(Long orderId);
}
