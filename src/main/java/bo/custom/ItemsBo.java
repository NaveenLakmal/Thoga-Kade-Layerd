package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemsBo extends SuperBo {

    boolean saveItems(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean updateItems(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteItems(String id) throws SQLException, ClassNotFoundException;
    List<ItemDto> allItems() throws SQLException, ClassNotFoundException;

}
