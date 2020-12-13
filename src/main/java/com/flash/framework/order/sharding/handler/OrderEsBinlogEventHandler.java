package com.flash.framework.order.sharding.handler;

import com.alibaba.fastjson.JSON;
import com.flash.framework.binlog.common.event.DataEvent;
import com.flash.framework.binlog.common.event.EventType;
import com.flash.framework.binlog.plugin.core.annotation.BinlogEvent;
import com.flash.framework.binlog.plugin.core.handler.BinlogEventHandler;
import com.flash.framework.binlog.plugin.es.elasticsearch.ElasticsearchBulkClient;
import com.flash.framework.binlog.plugin.es.elasticsearch.request.BulkRequest;
import com.flash.framework.binlog.plugin.es.elasticsearch.request.DeleteRequest;
import com.flash.framework.binlog.plugin.es.elasticsearch.request.IndexRequest;
import com.flash.framework.binlog.plugin.es.elasticsearch.request.UpsertRequest;
import com.flash.framework.binlog.plugin.es.elasticsearch.response.BulkResponse;
import com.flash.framework.order.sharding.es.order.OrderSO;
import com.flash.framework.order.sharding.model.entity.Order;
import com.flash.framework.order.sharding.model.entity.OrderLine;
import com.flash.framework.order.sharding.service.OrderService;
import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhurg
 * @date 2020/12/8 - 下午9:56
 */
@Slf4j
@BinlogEvent(
        database = "trade",
        tables = {"order_0", "order_1", "order_2", "order_3", "order_4", "order_5", "order_6", "order_7", "order_8", "order_9"},
        eventTypes = {EventType.CREATE, EventType.UPDATE, EventType.DELETE}
)
public class OrderEsBinlogEventHandler implements BinlogEventHandler {

    @Autowired
    private ElasticsearchBulkClient elasticsearchBulkClient;

    @Autowired
    private OrderService orderService;

    @Override
    public void onEvent(DataEvent dataEvent) {

        BulkRequest bulkRequest = new BulkRequest();
        Set<IndexRequest> requests = Sets.newHashSet();

        switch (dataEvent.getEventType()) {
            case CREATE:
            case UPDATE:
                requests.addAll(dataEvent.getRows().stream().map(rowEvent -> {
                    UpsertRequest upsertRequest = new UpsertRequest();
                    upsertRequest.setIndex("order");
                    upsertRequest.setType("order");
                    Order order = orderService.findByIdWithLine(Long.valueOf(rowEvent.getAfter().get("id").toString()));
                    OrderSO so = new OrderSO();
                    so.setOrderId(order.getId());
                    so.setTenantId(order.getTenantId());
                    so.setBuyerId(order.getBuyerId());
                    so.setShopId(order.getShopId());
                    so.setShopName(order.getShopName());
                    so.setItems(order.getOrderLines().stream().map(OrderLine::getItemName).collect(Collectors.toList()));
                    so.setCreatedAt(order.getCreatedAt());
                    so.setUpdatedAt(order.getUpdatedAt());
                    so.setPayAt(order.getPayAt());
                    so.setShippingAt(order.getShippingAt());
                    so.setConfirmAt(order.getConfirmAt());
                    so.setAccomplishAt(order.getAccomplishAt());
                    so.setPayStatus(order.getPayStatus());
                    so.setDeliveryStatus(order.getDeliveryStatus());
                    so.setReceiveStatus(order.getReceiveStatus());
                    so.setReverseStatus(order.getReverseStatus());
                    so.setVersion(order.getVersion().longValue());
                    upsertRequest.setId(so.getOrderId().toString());
                    upsertRequest.setSource(so);
                    upsertRequest.setVersion(so.getVersion());
                    return upsertRequest;
                }).collect(Collectors.toSet()));
                break;
            case DELETE:
                requests.addAll(dataEvent.getRows().stream().map(rowEvent -> {
                    DeleteRequest deleteRequest = new DeleteRequest();
                    deleteRequest.setIndex("order");
                    deleteRequest.setType("order");
                    deleteRequest.setId(rowEvent.getBefore().get("id").toString());
                    return deleteRequest;
                }).collect(Collectors.toSet()));
                break;
        }


        if (CollectionUtils.isNotEmpty(requests)) {
            bulkRequest.setRequests(requests);
            try {
                BulkResponse bulkResponse = elasticsearchBulkClient.bulk(bulkRequest);
                log.info("order changes {}", JSON.toJSONString(bulkResponse));
            } catch (Exception e) {
                log.error("binlog sync to elasticsearch failed,cause:{}", Throwables.getStackTraceAsString(e));
            }
        }
    }
}
