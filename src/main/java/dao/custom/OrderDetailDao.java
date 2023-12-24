package dao.custom;

import dao.CrudDao;
import dao.SuperDao;
import dto.OrderDetailDto;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDao extends CrudDao<OrderDetailDto> {
    boolean saveOrderDetails(List<OrderDetailDto> list) throws SQLException, ClassNotFoundException;


}
