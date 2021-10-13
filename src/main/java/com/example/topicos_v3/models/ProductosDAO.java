package com.example.topicos_v3.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProductosDAO {

    private int idProductos;
    private String nomProducto;
    private int idCategoria;
    private int stockProducto;
    private float precioProducto;
    private float costoProducto;

    public int getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(int idProductos) {
        this.idProductos = idProductos;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategora) {
        this.idCategoria = idCategora;
    }

    public int getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }

    public float getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(float precioProducto) {
        this.precioProducto = precioProducto;
    }

    public float getCostoProducto() {
        return costoProducto;
    }

    public void setCostoProducto(float costoProducto) {
        this.costoProducto = costoProducto;
    }

    //C -> create; R -> read

    public void INSERT(){
        try {
            String query = "INSERT INTO tblProductos (nomProducto, idCategoria, stockProducto, precioProducto, costoProducto) "
                    + "values('"+nomProducto+"',"+idCategoria+","+stockProducto+","+precioProducto+","+costoProducto+")";
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();//DEBUG MODE
        }
    }

    public void UPDATE(){
        try {
            String query = "UPDATE tblProductos SET nomProducto = '"+nomProducto+"', idCategoria = "+idCategoria+", " +
                    "stockProducto = "+stockProducto+", precioProducto = "+precioProducto+"" +
                    ", costoProducto = "+costoProducto+"  WHERE idProductos = " + idProductos;
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();//DEBUG MODE
        }
    }

    public void DELETE(){
        try {
            String query = "DELETE FROM tblProductos WHERE = " + idProductos;
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //recupera todos los registros de la tabla
    public ObservableList<ProductosDAO> SELECTALL(){
        ProductosDAO objP;
        ObservableList<ProductosDAO> listaP = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM tblProductos";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                objP = new ProductosDAO();
                objP.setIdProductos(res.getInt("idProductos"));
                objP.setNomProducto(res.getString("nomProducto"));
                objP.setIdCategoria(res.getInt("idCategoria"));
                objP.setStockProducto(res.getInt("stockProducto"));
                objP.setPrecioProducto(res.getInt("precioProducto"));
                objP.setCostoProducto(res.getInt("costoProducto"));
                listaP.add(objP);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaP;
    }

    //Recupera solo un registro identificado por el ID
    public ProductosDAO SELECTID(){
        ProductosDAO objP = null;
        try {
            String query = "SELECT * FROM tblProductos " + idProductos;
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                objP = new ProductosDAO();
                objP.setIdProductos(res.getInt("idProductos"));
                objP.setNomProducto(res.getString("nomProducto"));
                objP.setIdCategoria(res.getInt("idCategoria"));
                objP.setStockProducto(res.getInt("stockProducto"));
                objP.setPrecioProducto(res.getInt("precioProducto"));
                objP.setCostoProducto(res.getInt("costoProducto"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return objP;
    }
}
