package com.flash.framework.order.sharding.es;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhurg
 * @date 2020/9/20 - 下午10:40
 */
@Slf4j
@Component
public class EsIndiceInitializer {

    @Async
    @EventListener
    public void initIndice(ContextRefreshedEvent event) {
        Map<String, EsDao> beans = event.getApplicationContext().getBeansOfType(EsDao.class);
        if (MapUtils.isNotEmpty(beans)) {
            beans.values().forEach(bean -> {
                try {
                    bean.init();
                } catch (Exception e) {
                    log.error("es init failed,cause:{}", Throwables.getStackTraceAsString(e));
                }
            });
        }
    }
}