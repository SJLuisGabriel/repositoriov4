package com.example.topicos_v3.views;

import com.example.topicos_v3.models.ProductosDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Productos extends Stage {

    private VBox vBox;
    private TableView<ProductosDAO> tbvProductos;
    private Button btnNuevo;
    private Scene escena;
    private ProductosDAO objDAO;

    public Productos() {
        objDAO = new ProductosDAO();
        CrearUI();
        this.setTitle("Administracion de Productos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvProductos = new TableView<>();
        btnNuevo = new Button();

        btnNuevo.setText("Nuervo Producto");
        btnNuevo.setOnAction(event -> {});

        vBox = new VBox();
        vBox.getChildren().addAll(tbvProductos,btnNuevo);
        escena = new Scene(vBox,480,300);
        CrearTabla();
    }

    private void CrearTabla(){
        TableColumn<ProductosDAO,Integer> tbcIdProductos = new TableColumn<>("ID");
        tbcIdProductos.setCellValueFactory(new PropertyValueFactory<>("idProductos"));

        TableColumn<ProductosDAO,String> tbcNomProduto = new TableColumn<>("NOMBRE");
        tbcNomProduto.setCellValueFactory(new PropertyValueFactory<>("nomProdcuto"));

        TableColumn<ProductosDAO,Integer> tbcIdCategoria = new TableColumn<>("IDCATEGORIA");
        tbcIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));

        TableColumn<ProductosDAO,Integer> tbcStockProduto = new TableColumn<>("EXISTENCIA");
        tbcStockProduto.setCellValueFactory(new PropertyValueFactory<>("stockProducto"));

        TableColumn<ProductosDAO,Float> tbcPrecioProducto = new TableColumn<>("PRECIO");
        tbcPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));

        TableColumn<ProductosDAO,Float> tbcCostoProducto = new TableColumn<>("COSTO");
        tbcPrecioProducto.setCellValueFactory(new PropertyValueFactory<>("costoProducto"));

        tbvProductos.getColumns().addAll(tbcIdProductos,tbcNomProduto,tbcIdCategoria,tbcStockProduto,tbcPrecioProducto,tbcCostoProducto);
        tbvProductos.setItems(objDAO.SELECTALL());
    }
}
