package bo.custom.impl;

import bo.custom.OrderDetailBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.OrderDetailDao;
import dao.util.DaoType;
import dto.CustomerDto;
import dto.OrderDetailDto;
import entity.Customer;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailBoImpl implements OrderDetailBo {

    private OrderDetailDao orderDetailDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);


    @Override
    public List<OrderDetailDto> allOrderDetails() throws SQLException, ClassNotFoundException {
        System.out.println("test OD BO lline 1");
        List<OrderDetailDto> all = orderDetailDao.getAll();
        List<OrderDetailDto> list = new ArrayList<>();
        for (OrderDetailDto orderDetailDto:all) {
            list.add(new OrderDetailDto(
                    orderDetailDto.getOrderId(),
                    orderDetailDto.getItemCode(),
                    orderDetailDto.getQty(),
                    orderDetailDto.getUnitPrice()
            ));
        }
        return list;
    }
}
