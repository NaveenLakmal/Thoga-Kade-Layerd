package dao.custom.impl;

import dao.util.HibernateUtil;
import db.DBConnection;
import dto.OrderDetailDto;
import dao.custom.OrderDetailDao;
import entity.Customer;
import entity.OrderDetail;
import entity.OrderDetailsKey;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean saveOrderDetails(List<OrderDetailDto> list) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (OrderDetailDto dto:list) {
            pstm.setString(1,dto.getOrderId());
            pstm.setString(2,dto.getItemCode());
            pstm.setInt(3,dto.getQty());
            pstm.setDouble(4,dto.getUnitPrice());
            if (!(pstm.executeUpdate()>0)){
                return false;
            }
        }
        return true;
    }


//    @Override
//    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
//        return false;
//    }

//    @Override
//    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
//        return false;
//    }


    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM OrderDetail ");
        List<OrderDetail> list = query.list();

        session.close();
//        System.out.println(""+list);

        return list;
    }
}
