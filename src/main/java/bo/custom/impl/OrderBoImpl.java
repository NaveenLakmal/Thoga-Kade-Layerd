package bo.custom.impl;

import bo.custom.OrderBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.OrderDao;
import dao.custom.impl.OrderDaoImpl;
import dao.util.DaoType;
import dto.OrderDetailDto;
import dto.OrderDto;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBoImpl implements OrderBo {
    private OrderDao orderDao = new OrderDaoImpl();

    private OrderDao orderDao2 = DaoFactory.getInstance().getDao(DaoType.ORDER);

    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return orderDao.save(dto);
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            OrderDto dto = orderDao.getLastOrder();
            if (dto!=null){
                String id = dto.getOrderId();
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                return String.format("D%03d",num);
            }else{
                return "D001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<OrderDto> allOrder() throws SQLException, ClassNotFoundException {

       /* List<OrderDto> entityList = orderDao2.getAll();
       // System.out.println("test OD BO lline ALL ORDER"+entityList);
        List<OrderDto> list = new ArrayList<>();

        for (OrderDto orderDto:entityList) {
            list.add(new OrderDto(
                    orderDto.getOrderId(),
                    orderDto.getDate(),
                    orderDto.getCustId()
            ));
        }
        return list;*/
        List<OrderDto> entityList = orderDao2.getAll();
        return entityList;
    }

}



