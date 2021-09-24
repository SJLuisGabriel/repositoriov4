package com.example.topicos_v3.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Taquimecanografo  extends Stage implements EventHandler <KeyEvent>{

    private Scene escena;
    private VBox vBox, vTeclado;
    private HBox hFuncion, hNum, hTab;
    private TextArea txtFuente, txtEscritura;
    private ToolBar tolOpciones;
    private Button btnAbir;

    private Image imgOpcion;
    private File file, file2;
    private FileChooser flcArchivo;

    private Button[] arBtnFunc = new Button[14];
    private Button[] arBtnNum = new Button[14];
    private Button[] arBtnTab = new Button[14];
    private String[] arSimTab = {"->","q","w","e","r","t","y","u","i","o","p","´","+","}"};
    private Button[] arBtnMayus = new Button[13];
    private Button[] arBtnShift = new Button[13];
    private Button[] arBtnCtrl = new Button[10];

    private boolean ban = false;

    public Taquimecanografo(){
        CrearUI();
        this.setTitle("Taquimecanografo");
        this.setScene(escena);
        this.show();
    } //este constructor debe estar siempre que se crea una clase nueva

    private void CrearUI() {

        vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        tolOpciones = new ToolBar();

        file = new File("src/main/java/com/example/topicos_v3/image2/open_icon.png");
        imgOpcion = new Image(file.toURI().toString());
        ImageView view = new ImageView(imgOpcion);

        view.setFitHeight(24);
        view.setFitWidth(24);

        btnAbir = new Button();
        btnAbir.setGraphic(view);
        tolOpciones.getItems().addAll(btnAbir);

        btnAbir.setOnAction(event -> {
            tlbOpciones(1);
        });

        txtFuente = new TextArea();
        txtFuente.setEditable(false);
        txtEscritura = new TextArea();
        txtEscritura.setOnKeyPressed(this);
        txtEscritura.setOnKeyReleased(this);

        vTeclado = new VBox();

        hFuncion = new HBox();
        hNum = new HBox();
        hTab = new HBox();
        hTab.setSpacing(5d);

        setButtons(arBtnTab,hTab,arSimTab);

        vTeclado.getChildren().addAll(hFuncion,hNum,hTab);

        vBox.getChildren().addAll(tolOpciones, txtFuente, txtEscritura, vTeclado);
        escena = new Scene(vBox,600,300);

        file2 = new File("src/main/java/com/example/topicos_v3/css/style.css");
        escena.getStylesheets().add(file2.toURI().toString());
    }

    private void setButtons(Button[] arBtns,HBox hBtns, String[] arSimbolos){

        for (int i = 0; i < arBtns.length; i++) {
            arBtns[i] = new Button(arSimbolos[i]);
            hBtns.getChildren().add(arBtns[i]);
        }
    }

    private void tlbOpciones(int ipc){
        switch (ipc){
            case 1:
                flcArchivo = new FileChooser();
                flcArchivo.setTitle("Buscar Archivo...");
                flcArchivo.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("TXT","*.txt"));
                File file2 = flcArchivo.showOpenDialog(this);
                break;
        }
    }

    @Override
    public void handle(KeyEvent event) {

        switch (event.getCode().toString()){
            case "Q":
                if (ban == false) {
                    arBtnTab[1].setStyle("-fx-background-color: blue");
                    ban = !ban;
                } else {
                    arBtnTab[1].setStyle("-fx-background-color: white");
                    ban = !ban;
                }
                break;
        }
    }
}
