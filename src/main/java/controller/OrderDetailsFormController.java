package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.OrderDetailBo;
import dao.util.BoType;
import dto.CustomerDto;
import dto.OrderDetailDto;
import dto.tm.CustomerTm;
import dto.tm.OrderDetailTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsFormController {
    public TableView tblOrderDetail;
    public TableColumn colCode;
    public TableColumn colOrderId;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TextField txtCode;

    private OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    public void initialize(){
        //System.out.println("Load");
        colCode.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        loadOrderDetailTable();

        //tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
           // setData(newValue);
        //});
    }

    private void loadOrderDetailTable() {

        ObservableList<OrderDetailTm> tmList = FXCollections.observableArrayList();

        try {

            List<OrderDetailDto> dtoList  = orderDetailBo.allOrderDetails();
            System.out.println("Load list"+dtoList);
            for (OrderDetailDto dto:dtoList) {

                OrderDetailTm c = new OrderDetailTm(
                        dto.getOrderId(),
                        dto.getItemCode(),
                        dto.getQty(),
                        dto.getUnitPrice()

                );
                System.out.println(""+c);



                tmList.add(c);
            }
            tblOrderDetail.setItems(tmList);
            System.out.println("Load"+dtoList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public void reloadButtonOnAction(ActionEvent actionEvent) {
        loadOrderDetailTable();
    }
    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)tblOrderDetail.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
