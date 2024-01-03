package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.OrderBo;
import bo.custom.OrderDetailBo;
import dao.util.BoType;
import dto.CustomerDto;
import dto.OrderDetailDto;
import dto.OrderDto;
import dto.tm.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.function.Predicate;

public class OrderDetailsFormController {
    public TableView tblOrderDetail;

    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TextField txtCode;

    // ORDER Table
    public TableView tblOrder;
    public TableColumn orderColOrderId;
    public TableColumn orderColDate;
    public TableColumn orderColCustomerId;
    public TableColumn colOrderCode;
    public TableColumn colItemId;



    private OrderDetailBo orderDetailBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);

    public void initialize(){
        //System.out.println("Load");

         colOrderCode.setCellValueFactory(new PropertyValueFactory<>("orderId"));
         colItemId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
          colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
         colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        loadOrderDetailTable();


        orderColOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        orderColCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        loadOrderTable();

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData((OrderDto) newValue);
        });

        //tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
           // setData(newValue);
        //});

        txtCode.textProperty().addListener((observable, oldValue, newValue) -> {
            // Create a predicate to filter the items
            Predicate<OrderDto> predicate = orderDto ->
                    orderDto.getOrderId().toLowerCase().contains(newValue.toLowerCase()) ;

            // Wrap the list of items in a FilteredList and set the predicate
            FilteredList<OrderDto> filteredList = new FilteredList<>(tblOrder.getItems(), predicate);
            tblOrder.setItems(filteredList);
        });

    }


    private void setData(OrderDto newValue) {
        loadOrderDetailTable();
        if (newValue != null) {

//            if(newValue.getCustId().equals()){
//
//            }else{
//                return;
//            }




            //colOrderCode.setCellValueFactory(new PropertyValueFactory<>("orderId"));


           txtCode.setText(newValue.getCustId());
//            txtName.setText(newValue.getName());
//            txtAddress.setText(newValue.getAddress());
//            txtSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }


    private void loadOrderDetailTable(){

        ObservableList<OrderDetailTm> tmList = FXCollections.observableArrayList();

        try {

            List<OrderDetailDto> dtoList = orderDetailBo.allOrderDetails();
            System.out.println("Load list" + dtoList);



            for (OrderDetailDto dto : dtoList) {

                OrderDetailTm c = new OrderDetailTm(
                        dto.getOrderId(),
                        dto.getItemCode(),
                        dto.getQty(),
                        dto.getUnitPrice()

                );
                System.out.println("" + c);


                tmList.add(c);
            }
            tblOrderDetail.setItems(tmList);
            System.out.println("Load" + dtoList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void loadOrderTable() {
        ObservableList<OrderDto> tmList = FXCollections.observableArrayList();

        try {

            List<OrderDto> dtoList = orderBo.allOrder();



            for (OrderDto dto : dtoList) {

                OrderDto c = new OrderDto(
                        dto.getOrderId(),
                        dto.getDate(),
                        dto.getCustId()


                );
                tmList.add(c);
            }


            tblOrder.setItems(tmList);
            System.out.println("Load" + dtoList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void reloadButtonOnAction(ActionEvent actionEvent) {
       // loadOrderTable();
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
