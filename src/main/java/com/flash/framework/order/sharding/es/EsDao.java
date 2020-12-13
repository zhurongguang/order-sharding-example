package com.flash.framework.order.sharding.es;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flash.framework.commons.utils.ReflectUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhurg
 * @date 2020/9/20 - 下午6:29
 */
@Slf4j
public abstract class EsDao<T> {

    @Value("${spring.elasticsearch.bboss.mapperFilePath:esmapper}")
    private String mapperFilePath;

    @Autowired
    private BBossESStarter esStarter;

    private Class<T> clazz;

    private String indice;

    private String indiceType;

    public void init() {
        try {
            clazz = ReflectUtil.getGenericClass(this.getClass());
            ClientInterface clientInterface = getClient();
            EsIndice esIndice = AnnotationUtils.findAnnotation(clazz, EsIndice.class);
            if (Objects.isNull(esIndice)) {
                throw new IllegalArgumentException("EsIndice is undefined");
            }
            this.indice = esIndice.indice();
            this.indiceType = esIndice.indiceType();
            if (!clientInterface.existIndice(indice)) {
                clientInterface.createIndiceMapping(indice, esIndice.indiceMapping());
            }
        } catch (Exception e) {
            log.error("ES indice {} init failed,cause:{}", clazz, Throwables.getStackTraceAsString(e));
        }
    }

    public void create(T obj) {
        ClientInterface clientInterface = getClient();
        String response = clientInterface.addDocument(indice, indiceType, obj, "refresh=true");
        log.info("create doc {} for indice {},response : {}", JSON.toJSONString(obj), indice, response);
    }

    public void creates(List<T> objs) {
        ClientInterface clientInterface = getClient();
        String response = clientInterface.addDocuments(indice, indiceType, objs, "refresh=true");
        log.info("create docs {} for indice {},response : {}", JSON.toJSONString(objs), indice, response);
    }

    /**
     * 分页查询
     *
     * @param statement
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<T> page(String statement, Map<String, Object> params, int pageNo, int pageSize) {
        ClientInterface clientInterface = getClient();
        if (MapUtils.isEmpty(params)) {
            params = Maps.newHashMapWithExpectedSize(2);
        }
        params.put("from", (pageNo - 1) * pageSize);
        params.put("size", pageSize);
        ESDatas<T> datas = clientInterface.searchList(String.format("%s/_search", indice), statement,
                params, clazz);
        Page<T> paging = new Page<>();
        paging.setCurrent((long) pageNo);
        paging.setSize((long) pageSize);
        paging.setRecords(datas.getDatas());
        paging.setTotal(datas.getTotalSize());
        return paging;
    }

    /**
     * 获取client
     *
     * @return
     */
    protected ClientInterface getClient() {
        return esStarter.getConfigRestClient(String.format("%s/%s.xml", mapperFilePath, clazz.getSimpleName()));
    }
}