package com.flash.framework.order.sharding.binlog;

import com.alibaba.fastjson.JSON;
import com.flash.framework.binlog.common.event.DataEvent;
import com.flash.framework.binlog.plugin.core.BinlogEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhurg
 * @date 2020/12/7 - 下午2:06
 */
@Slf4j
@Component
public class BinlogKafkaConsumer {

    @Autowired
    private BinlogEventService binlogEventService;

    @KafkaListener(topics = {"trade"}, containerGroup = "tradeConsumerGroup", groupId = "tradeConsumerGroup")
    public void onEvent(String event) {
        log.info("[Binlog] accept message {}", event);
        DataEvent dataEvent = JSON.parseObject(event, DataEvent.class);
        binlogEventService.onBinlogEvent(dataEvent);
    }
}