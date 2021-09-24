package com.example.topicos_v3;

import com.example.topicos_v3.views.Loteria;
import com.example.topicos_v3.views.Taquimecanografo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    private BorderPane bdpPrincipal;
    private MenuBar mnbOPciones;
    private Menu menCompetencia1, menCompetencia2,menSalir;
    private MenuItem miLoteria, mitTaqui, mitSalir;
    private Scene escena;

    @Override
    public void start(Stage primaryStage) throws Exception {

        CrearUI();
        escena = new Scene(bdpPrincipal);
        primaryStage.setScene(escena);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Formulario Principal");
        primaryStage.show();
    }

    private void CrearUI() {
        bdpPrincipal = new BorderPane();
        mnbOPciones = new MenuBar();
        menCompetencia1 = new Menu("1er. Competencia");
        menCompetencia2 = new Menu("2da. Competencia");
        menSalir = new Menu("Salir");
        //Agregar menus al menubar
        mnbOPciones.getMenus().addAll(menCompetencia1,menCompetencia2,menSalir);
        bdpPrincipal.setTop(mnbOPciones);
        //Instaciamos los MenusItems
        miLoteria = new MenuItem("Loteria");
        mitTaqui = new MenuItem("Taquimecanografo");
        /*miLoteria.setOnAction(event -> {
            new Loteria();
        });*/

        miLoteria.setOnAction(event -> MenuOpciones(1));
        mitTaqui.setOnAction(event -> MenuOpciones(2));

        menCompetencia1.getItems().addAll(miLoteria,mitTaqui);
        mitSalir = new MenuItem("Hasta Luego");
        mitSalir.setOnAction(event -> MenuOpciones(20));
        menSalir.getItems().addAll(mitSalir);

    }

    private void MenuOpciones(int opc){
        switch (opc){
            case 1: new Loteria(); break;
            case 2: new Taquimecanografo(); break;
            case 20: System.exit(0);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}