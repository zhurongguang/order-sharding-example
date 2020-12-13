package com.flash.framework.order.sharding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flash.framework.mybatis.service.BaseServiceImpl;
import com.flash.framework.order.sharding.es.order.OrderEsDao;
import com.flash.framework.order.sharding.es.order.OrderSO;
import com.flash.framework.order.sharding.mapper.OrderMapper;
import com.flash.framework.order.sharding.model.entity.Order;
import com.flash.framework.order.sharding.model.entity.OrderLine;
import com.flash.framework.order.sharding.model.request.OrderPageRequest;
import com.flash.framework.order.sharding.service.OrderLineService;
import com.flash.framework.order.sharding.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zhurg
 * @since 2020-11-24
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements OrderService {

    private final OrderEsDao orderEsDao;

    private final OrderLineService orderLineService;

    @Override
    public Page<Order> page(OrderPageRequest request) {
        Page<Order> page = new Page<>();
        //基于ES分页查询
        Page<OrderSO> esPage = orderEsDao.page("page", request.toMap(), request.getPageNo(), request.getPageSize());
        if (esPage.getTotal() > 0L) {
            page.setTotal(esPage.getTotal());
            //基于id查询订单完整信息,走数据库查询
            List<Order> orders = listByIds(esPage.getRecords().stream().map(OrderSO::getOrderId).collect(Collectors.toList()));
            page.setRecords(orders);
            page.setCurrent(request.getPageNo());
            page.setSize(request.getPageSize());
        } else {
            page.setTotal(0);
        }
        return page;
    }

    @Override
    public Order findByIdWithLine(Long orderId) {
        Order order = getById(orderId);
        if (Objects.isNull(order)) {
            return null;
        }

        List<OrderLine> orderLines = orderLineService.list(new QueryWrapper<OrderLine>().eq("order_id", orderId));
        order.setOrderLines(orderLines);
        return order;
    }
}
