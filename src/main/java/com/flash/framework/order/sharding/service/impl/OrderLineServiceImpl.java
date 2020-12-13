package com.flash.framework.order.sharding.service.impl;

import com.flash.framework.order.sharding.model.entity.OrderLine;
import com.flash.framework.order.sharding.mapper.OrderLineMapper;
import com.flash.framework.order.sharding.service.OrderLineService;
import com.flash.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单行 服务实现类
 * </p>
 *
 * @author zhurg
 * @since 2020-11-24
 */
@Service
public class OrderLineServiceImpl extends BaseServiceImpl<OrderLineMapper, OrderLine> implements OrderLineService {

}
