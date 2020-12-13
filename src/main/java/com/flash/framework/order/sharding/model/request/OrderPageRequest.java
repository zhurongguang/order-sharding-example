package com.flash.framework.order.sharding.model.request;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhurg
 * @date 2020/12/3 - 上午12:49
 */
@Data
public class OrderPageRequest implements Serializable {

    private static final long serialVersionUID = 4751173934450589951L;

    private List<Long> orderIds;

    private List<Long> shopIds;

    private String shopName;

    private String itemName;

    private Long buyerId;

    private List<String> payStatus;

    private List<String> deliveryStatus;

    private List<String> receiveStatus;

    private List<String> reverseStatus;

    private Date startAt;

    private Date endAt;

    private Integer pageNo;

    private Integer pageSize;

    public Map<String, Object> toMap() {
        return JSON.parseObject(JSON.toJSONString(this), Map.class);
    }
}