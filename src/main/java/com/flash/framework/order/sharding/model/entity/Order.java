package com.flash.framework.order.sharding.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.flash.framework.mybatis.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author zhurg
 * @since 2020-11-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("`order`")
public class Order extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 租户id
     */
    @TableField("tenant_id")
    private Integer tenantId;

    /**
     * 买家id
     */
    @TableField("buyer_id")
    private Long buyerId;

    /**
     * 买家姓名
     */
    @TableField("buyer_name")
    private String buyerName;

    /**
     * 卖家姓名
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 卖家id
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 支付状态
     */
    @TableField("pay_status")
    private String payStatus;

    /**
     * 配送状态
     */
    @TableField("delivery_status")
    private String deliveryStatus;

    /**
     * 签收状态
     */
    @TableField("receive_status")
    private String receiveStatus;

    /**
     * 售后状态
     */
    @TableField("reverse_status")
    private String reverseStatus;

    /**
     * 支付完成时间
     */
    @TableField("pay_at")
    private Date payAt;

    /**
     * 发货时间
     */
    @TableField("shipping_at")
    private Date shippingAt;

    /**
     * 确认收货时间
     */
    @TableField("confirm_at")
    private Date confirmAt;

    /**
     * 实际支付金额
     */
    @TableField("paid_amount")
    private Long paidAmount;

    /**
     * 商品总额
     */
    @TableField("sku_origin_total_amount")
    private Long skuOriginTotalAmount;

    /**
     * 商品优惠总额
     */
    @TableField("sku_discount_total_amount")
    private Long skuDiscountTotalAmount;

    /**
     * 运费总额
     */
    @TableField("ship_fee_origin_amount")
    private Long shipFeeOriginAmount;

    /**
     * 运费优惠总额
     */
    @TableField("ship_fee_discount_total_amount")
    private Long shipFeeDiscountTotalAmount;

    /**
     * 收货地址
     */
    @TableField("delivery_address")
    private String deliveryAddress;

    /**
     * 订单完成时间
     */
    @TableField("accomplish_at")
    private Date accomplishAt;

    @TableField(exist = false)
    private List<OrderLine> orderLines;

    private Integer version;
}
