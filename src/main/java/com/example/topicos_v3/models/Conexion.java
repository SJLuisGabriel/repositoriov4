package com.example.topicos_v3.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static String server = "localhost";
    private static String user = "topicos2021";
    private static String pwd = "852741";
    private static String db = "tiendabd";


    public static Connection conexion;
    public static void getConexion() throws Exception{
            Class.forName("org.mariadb.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mariadb://" + server + ":3306/" + db,user,pwd);
    }
}
