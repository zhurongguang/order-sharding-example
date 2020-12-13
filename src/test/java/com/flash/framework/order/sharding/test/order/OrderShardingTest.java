package com.flash.framework.order.sharding.test.order;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flash.framework.order.sharding.model.entity.Order;
import com.flash.framework.order.sharding.model.entity.OrderLine;
import com.flash.framework.order.sharding.model.request.OrderPageRequest;
import com.flash.framework.order.sharding.service.OrderLineService;
import com.flash.framework.order.sharding.service.OrderService;
import com.flash.framework.order.sharding.test.BaseTest;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author zhurg
 * @date 2020/11/24 - 下午6:39
 */
public class OrderShardingTest extends BaseTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLineService orderLineService;

    @Test
    public void testShardingInsert() {
        for (int i = 1; i <= 100; i++) {
            Order order = new Order();
            order.setTenantId(1);
            order.setBuyerId(1L);
            order.setBuyerName("test");
            order.setCreateBy("test");
            order.setShopName("test");
            order.setShopId(1L);
            order.setPayStatus("PAY_SUCCESS");
            order.setDeliveryStatus("DELIVERY_INIT");
            order.setReceiveStatus("RECEIVE_INIT");
            order.setReverseStatus("REVERSE_INIT");
            order.setPayAt(new Date());
            order.setPaidAmount(1000L);
            order.setDeliveryAddress("测试");
            order.setUpdateBy("test");
            order.setSkuOriginTotalAmount(1000L);
            order.setSkuDiscountTotalAmount(0L);
            order.setShipFeeOriginAmount(0L);
            order.setShipFeeDiscountTotalAmount(0L);
            order.setDeleted(Boolean.FALSE);
            order.setVersion(0);

            boolean rs = orderService.save(order);
            if (rs) {
                List<OrderLine> orderLines = Lists.newArrayListWithCapacity(2);
                for (int j = 1; j <= 2; j++) {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setCreateBy("test");
                    orderLine.setUpdateBy("test");
                    orderLine.setTenantId(1);
                    orderLine.setOrderId(order.getId());
                    orderLine.setPayStatus(order.getPayStatus());
                    orderLine.setDeliveryStatus(order.getDeliveryStatus());
                    orderLine.setReceiveStatus(order.getReceiveStatus());
                    orderLine.setReverseStatus(order.getReverseStatus());
                    orderLine.setSkuId(j + 100000L);
                    orderLine.setSkuCode(orderLine.getSkuId().toString());
                    orderLine.setSkuName("shu" + j);
                    orderLine.setQuantity(1);
                    orderLine.setPaidAmount(500L);
                    orderLine.setItemId(orderLine.getSkuId());
                    orderLine.setItemName(orderLine.getSkuName());
                    orderLine.setSkuOriginTotalAmount(1000L);
                    orderLine.setSkuDiscountTotalAmount(0L);
                    orderLine.setShipFeeOriginAmount(0L);
                    orderLine.setShipFeeDiscountTotalAmount(0L);
                    orderLine.setDeleted(Boolean.FALSE);
                    orderLine.setVersion(0);
                    orderLines.add(orderLine);
                }

                orderLineService.saveBatch(orderLines);
            }
        }
    }

    @Test
    public void testPageRequest() {
        OrderPageRequest orderPageRequest = new OrderPageRequest();
        orderPageRequest.setPageNo(1);
        orderPageRequest.setPageSize(2);
        orderPageRequest.setBuyerId(1L);
        Page<Order> page = orderService.page(orderPageRequest);
        System.out.println(JSON.toJSONString(page));
    }

    @Test
    public void testFindById() {
        //order_7
        Order order = orderService.getById(1331229209856352257L);
        System.out.println(JSON.toJSONString(order));
    }
}