package pers.ryan.database.persistence.master;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * order_main
 *
 * @author
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Long orderSn) {
        this.orderSn = orderSn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public Short getProvince() {
        return province;
    }

    public void setProvince(Short province) {
        this.province = province;
    }

    public Short getCity() {
        return city;
    }

    public void setCity(Short city) {
        this.city = city;
    }

    public Short getDistrict() {
        return district;
    }

    public void setDistrict(Short district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Byte getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Byte paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BigDecimal getShippingMoney() {
        return shippingMoney;
    }

    public void setShippingMoney(BigDecimal shippingMoney) {
        this.shippingMoney = shippingMoney;
    }

    public BigDecimal getPaymentMoney() {
        return paymentMoney;
    }

    public void setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
    }

    public String getShippingCompName() {
        return shippingCompName;
    }

    public void setShippingCompName(String shippingCompName) {
        this.shippingCompName = shippingCompName;
    }

    public String getShippingSn() {
        return shippingSn;
    }

    public void setShippingSn(String shippingSn) {
        this.shippingSn = shippingSn;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}