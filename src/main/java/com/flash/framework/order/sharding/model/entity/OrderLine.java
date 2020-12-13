package com.flash.framework.order.sharding.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.flash.framework.mybatis.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 订单行
 * </p>
 *
 * @author zhurg
 * @since 2020-11-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order_line")
public class OrderLine extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 订单行id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 租户id
     */
    @TableField("tenant_id")
    private Integer tenantId;

    /**
     * 店铺订单id
     */
    @TableField("order_id")
    private Long orderId;

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
     * 退款完成时间
     */
    @TableField("refund_at")
    private Date refundAt;

    /**
     * 确认收获时间
     */
    @TableField("confirm_at")
    private Date confirmAt;

    /**
     * sku id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * sku code
     */
    @TableField("sku_code")
    private String skuCode;

    /**
     * sku 名称
     */
    @TableField("sku_name")
    private String skuName;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

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
     * 商品id
     */
    @TableField("item_id")
    private Long itemId;

    /**
     * 商品名称
     */
    @TableField("item_name")
    private String itemName;

    private Integer version;
}
