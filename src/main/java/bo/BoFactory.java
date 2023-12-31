package bo;

import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemsBoImpl;
import bo.custom.impl.OrderBoImpl;
import bo.custom.impl.OrderDetailBoImpl;
import dao.util.BoType;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){

    }
    public static BoFactory getInstance(){
        return boFactory!=null? boFactory:(boFactory=new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case CUSTOMER: return (T) new CustomerBoImpl();
            case ITEM: return (T) new ItemsBoImpl();
            case ORDER_DETAIL: return (T) new OrderDetailBoImpl();
            case ORDER: return (T) new OrderBoImpl();
        }
        return null;
    }
}
