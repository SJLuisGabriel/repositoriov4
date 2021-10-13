package com.example.topicos_v3;

import com.example.topicos_v3.models.Conexion;
import com.example.topicos_v3.views.Loteria;
import com.example.topicos_v3.views.Productos;
import com.example.topicos_v3.views.Taquimecanografo;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HelloApplication extends Application implements EventHandler<WindowEvent> {

    private BorderPane bdpPrincipal;
    private MenuBar mnbOPciones;
    private Menu menCompetencia1, menCompetencia2,menSalir;
    private MenuItem miLoteria, mitTaqui, mitTienda, mitSalir;
    private Scene escena;
    private VBox vBox;

    @Override
    public void start(Stage primaryStage) throws Exception {

        CrearUI();
        //escena = new Scene(bdpPrincipal);
        primaryStage.setScene(escena);
        primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWING, this);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Formulario Principal");
        primaryStage.show();

        //
        //escena.getStylesheets().add(file.toURI().toString());
    }

    private void CrearUI() {

        vBox = new VBox();

        bdpPrincipal = new BorderPane();
        mnbOPciones = new MenuBar();
        menCompetencia1 = new Menu("1er. Competencia");
        menCompetencia2 = new Menu("2da. Competencia");
        menSalir = new Menu("Salir");
        //Agregar menus al menubar
        mnbOPciones.setStyle("-fx-background-color: #84b6f4");
        mnbOPciones.getMenus().addAll(menCompetencia1,menCompetencia2,menSalir);
        bdpPrincipal.setTop(mnbOPciones);
        //Instaciamos los MenusItems
        miLoteria = new MenuItem("Loteria");
        mitTaqui = new MenuItem("Taquimecanografo");
        mitTienda = new MenuItem("Tienda");
        /*miLoteria.setOnAction(event -> {
            new Loteria();
        });*/

        miLoteria.setOnAction(event -> MenuOpciones(1));
        mitTaqui.setOnAction(event -> MenuOpciones(2));
        mitTienda.setOnAction(event -> MenuOpciones(3));

        menCompetencia1.getItems().addAll(miLoteria,mitTaqui,mitTienda);
        mitSalir = new MenuItem("Hasta Luego");
        mitSalir.setOnAction(event -> MenuOpciones(20));
        menSalir.getItems().addAll(mitSalir);

        vBox.getChildren().addAll(mnbOPciones);

        escena = new Scene(vBox,280,30);
    }

    private void MenuOpciones(int opc){
        switch (opc){
            case 1: new Loteria(); break;
            case 2: new Taquimecanografo(); break;
            case 3: new Productos(); break;
            case 20: System.exit(0);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(WindowEvent windowEvent) {


        try {
            Conexion.getConexion();
        }catch (Exception e){
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Alerta Del Sistema");
            alerta.setHeaderText("Topicos Avanzados De Programacion");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
            System.exit(0);
        }
    }
}