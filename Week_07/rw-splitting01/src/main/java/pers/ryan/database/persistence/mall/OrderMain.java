package pers.ryan.database.persistence.mall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * order_main
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMain implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    private Long id;
    /**
     * 订单编号
     */
    private Long orderSn;
    /**
     * 下单人ID
     */
    private Long userId;
    /**
     * 收货人姓名
     */
    private String shippingName;
    /**
     * 省
     */
    private Short province;
    /**
     * 市
     */
    private Short city;
    /**
     * 区
     */
    private Short district;
    /**
     * 地址
     */
    private String address;
    /**
     * 支付方式：1现金，2余额，3网银，4支付宝，5微信
     */
    private Byte paymentMethod;
    /**
     * 订单金额
     */
    private BigDecimal orderMoney;
    /**
     * 运费金额
     */
    private BigDecimal shippingMoney;
    /**
     * 支付金额
     */
    private BigDecimal paymentMoney;
    /**
     * 快递公司名称
     */
    private String shippingCompName;
    /**
     * 快递单号
     */
    private String shippingSn;
    /**
     * 下单时间
     */
    private Long createTime;
    /**
     * 发货时间
     */
    private Date shippingTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 收货时间
     */
    private Date receiveTime;
    /**
     * 订单状态
     */
    private Byte orderStatus;
    /**
     * 最后修改时间
     */
    private Long modifiedTime;

}