package com.flash.framework.order.sharding.es.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flash.framework.order.sharding.es.EsIndice;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhurg
 * @date 2020/12/3 - 上午2:02
 */
@Data
@EsIndice(indice = "order", indiceType = "order")
public class OrderSO {

    private Long orderId;

    private Integer tenantId;

    private Long buyerId;

    private Long shopId;

    private String shopName;

    private List<String> items;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shippingAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date confirmAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date accomplishAt;

    private String payStatus;

    private String deliveryStatus;

    private String receiveStatus;

    private String reverseStatus;

    private Long version;

}