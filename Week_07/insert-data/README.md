### 按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率
1.逐个插入，耗时很多，跑一会停掉了
 ```java
    public void testInsert() {
        for (int i = 0; i < TOTAL_MOUNT; i++) {
            log.info("插入数据{}", i);
            orderMainMapper.insertSelective(targetOrder);
        }
    }
 ```

2.批量插入。耗时124秒
 ```java
    public void testInsertBatch() {
        List<OrderMain> orderMainList = new ArrayList<>(EACH_MOUNT);
        for (int i = 0; i < TOTAL_MOUNT; i++) {
            orderMainList.add(targetOrder);
            if (orderMainList.size() >= EACH_MOUNT) {
                log.info("插入数据{}", i);
                orderMainMapper.insertBatch(orderMainList);
                orderMainList = new ArrayList<>(EACH_MOUNT);
            }
        }
    }

<insert id="insertBatch" parameterType="pers.ryan.database.persistence.master.OrderMain">
    insert into order_main (order_sn, user_id,
      shipping_name, province, city,
      district, address, payment_method,
      order_money, shipping_money, payment_money,
      create_time, order_status, modified_time)
    values
    <foreach collection="list" item="record" separator=",">
      (#{record.orderSn,jdbcType=BIGINT}, #{record.userId,jdbcType=BIGINT},
      #{record.shippingName,jdbcType=VARCHAR}, #{record.province,jdbcType=SMALLINT}, #{record.city,jdbcType=SMALLINT},
      #{record.district,jdbcType=SMALLINT}, #{record.address,jdbcType=VARCHAR}, #{record.paymentMethod,jdbcType=TINYINT},
      #{record.orderMoney,jdbcType=DECIMAL}, #{record.shippingMoney,jdbcType=DECIMAL}, #{record.paymentMoney,jdbcType=DECIMAL},
      #{record.createTime,jdbcType=BIGINT},#{record.orderStatus,jdbcType=TINYINT}, #{record.modifiedTime,jdbcType=BIGINT})
    </foreach>
  </insert>
 ```

3.生产消费模式，多线程批量插入。耗时26秒
 ```java
    public void testInsertBatchThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            executorService.submit(new handleData());
        }
        executorService.shutdown();
        for (int i = 0; i < TOTAL_MOUNT; i++) {
            orderQueue.add(targetOrder);
        }
        end = true;
        start = System.currentTimeMillis();
        while (!orderQueue.isEmpty()) {
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    private class handleData implements Runnable {
        @Override
        public void run() {
            while (true) {
                List<OrderMain> temp = new ArrayList<>();
                for (int i = 0; i < 5000; i++) {
                    OrderMain orderMain = orderQueue.poll();
                    if (orderMain != null) {
                        temp.add(orderMain);
                    } else {
                        break;
                    }
                }
                if (!temp.isEmpty()) {
                    log.info("插入数据: {}", temp.size());
                    orderMainMapper.insertBatch(temp);
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (end && orderQueue.isEmpty()) {
                    break;
                }
            }
        }
    }
 ```