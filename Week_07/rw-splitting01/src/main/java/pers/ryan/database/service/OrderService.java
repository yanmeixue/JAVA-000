package pers.ryan.database.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.ryan.database.annotation.Master;
import pers.ryan.database.persistence.mall.OrderMain;
import pers.ryan.database.persistence.mall.OrderMainMapper;

import javax.annotation.Resource;

@Service
public class OrderService {
    @Resource
    private OrderMainMapper orderMainMapper;

    @Transactional
    public int insert(OrderMain orderMain) {
        return orderMainMapper.insertSelective(orderMain);
    }


    public OrderMain selectById(long id) {
        return orderMainMapper.selectByPrimaryKey(id);
    }

    @Master
    public OrderMain selectFromMaster(long id) {
        return orderMainMapper.selectByPrimaryKey(id);
    }
}
