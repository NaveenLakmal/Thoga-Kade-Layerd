package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemsBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.CustomerDto;
import dto.ItemDto;
import dto.tm.CustomerTm;
import dto.tm.ItemTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import dao.custom.ItemDao;
import dao.custom.impl.ItemDaoImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.function.Predicate;

public class ItemFormController {
    public BorderPane pane;
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTextField txtSearch;
    public TableView tblItem;
    public TableColumn colCode;
    public TableColumn colDesc;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colOption;


    private ItemsBo itemsBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        //loadItems();
        loadItemsTable();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            // Create a predicate to filter the items
            Predicate<ItemTm> predicate = item ->
                    item.getCode().toLowerCase().contains(newValue.toLowerCase()) ||
                            item.getDesc().toLowerCase().contains(newValue.toLowerCase());

            // Wrap the list of items in a FilteredList and set the predicate
            FilteredList<ItemTm> filteredList = new FilteredList<>(tblItem.getItems(), predicate);
            tblItem.setItems(filteredList);
        });

    }

//    private void loadItems() {
//        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
//
//        try {
//            List<ItemDto> dtoList  = itemsBo.allItems();
//            for (ItemDto dto:dtoList) {
//                JFXButton btn = new JFXButton("Delete");
//
//                ItemTm tm = new ItemTm(
//                        dto.getCode(),
//                        dto.getDesc(),
//                        dto.getUnitPrice(),
//                        dto.getQty(),
//                        btn
//                );
//
//                btn.setOnAction(actionEvent -> {
//                   // deleteItems(c.getId());
//                });
//
//                tmList.add(tm);
//            }
//            RecursiveTreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
//            tblItem.setRoot(treeItem);
//            tblItem.setShowRoot(false);
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {

        ItemDto dto = new ItemDto(txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );

        try {
            boolean isSaved = itemsBo.saveItems(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadItemsTable();
                //clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

//DELETE ITEM
    private void deleteItem(String id) {

        try {
            boolean isDeleted = itemsBo.deleteItems(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemsTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadItemsTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        try {
            List<ItemDto> dtoList  = itemsBo.allItems();
            for (ItemDto dto:dtoList) {
                JFXButton btn = new JFXButton("Delete");
                ItemTm c = new ItemTm(
                        dto.getCode() ,
                        dto.getDesc(),
                        dto.getUnitPrice(),
                        dto.getQty(),
                        btn
                );
               System.out.println(dtoList);

                btn.setOnAction(actionEvent -> {
                    deleteItem(c.getCode());
                });

                tmList.add(c);
            }
            tblItem.setItems(tmList);
            //tblItem.set(tmList);



            System.out.println(dtoList);
            System.out.println(tmList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateButtonOnAction(ActionEvent actionEvent) {

        ItemDto dto = new ItemDto(txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );

        try {
            boolean isUpdated = itemsBo.updateItems(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Items "+dto.getCode()+" Updated!").show();
                loadItemsTable();
                //clearFields();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }
}
