package bo.custom;

import bo.SuperBo;
import dto.OrderDetailDto;
import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo extends SuperBo {
    boolean saveOrder(OrderDto dto)throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;

    List<OrderDto> allOrder() throws SQLException, ClassNotFoundException;
}
