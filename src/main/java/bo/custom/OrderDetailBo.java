package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import dto.OrderDetailDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailBo extends SuperBo {
    List<OrderDetailDto> allOrderDetails() throws SQLException, ClassNotFoundException;
}
