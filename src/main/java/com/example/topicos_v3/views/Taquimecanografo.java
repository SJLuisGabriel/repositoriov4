package com.example.topicos_v3.views;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

public class Taquimecanografo  extends Stage implements EventHandler <KeyEvent> {

    private Scene escena;
    private VBox vBox, vTeclado;
    private HBox hFuncion, hNum, hTab, hMayus, hShift, hCtrl, hBotons;
    private TextArea txtFuente, txtEscritura;
    private Button btnAbrir, btnEmpezar, btnResultado;
    private Timer tiemp;

    private Image imgOpcion;
    private File file, fileA;
    private FileChooser flcArchivo;
    private Label lblTiempo;

    private Button[] arBtnFunc = new Button[14];
    private String[] arSiFunc = {"Esc", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "DELETE"};
    private Button[] arBtnNum = new Button[14];
    private String[] arSiNum = {"|", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "'", "¿", "<---"};
    private Button[] arBtnTab = new Button[14];
    private String[] arSiTab = {"⮂", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "´", "+", "}"};
    private Button[] arBtnMayus = new Button[13];
    private String[] arSiMayus = {"Mayus", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ", "{", "⮨"};
    private Button[] arBtnShift = new Button[12];
    private String[] arSiShift = {"Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "-", "⮙"};
    private Button[] arBtnCtrl = new Button[9];
    private String[] arSiCtrl = {"Ctrl", "Fn", "wind", "Alt", "Space", "AltGr", "⮘", "⮛", "⮚"};

    String documento = "", comp = "";
    int cntError = 0, cnttiempS1 = 0, cnttiempS2 = 0, cnttiempM1 = 0, cnttiempM2 = 0, resLetra = 0, rsRes = 0, numLetras = 0;
    Font fuente2 = new Font("Times New Roman",20);

    private boolean ban = false;

    public Taquimecanografo(){
        CrearUI();
        this.setTitle("Taquimecanografo");
        this.setScene(escena);
        this.show();
    } //este constructor debe estar siempre que se crea una clase nueva

    private void CrearUI() {

        vBox = new VBox();
        lblTiempo = new Label("00:00");
        lblTiempo.setMaxSize(100,100);
        lblTiempo.setFont(fuente2);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        hBotons = new HBox();
        hBotons.setSpacing(10);

        file = new File("src/main/java/com/example/topicos_v3/image2/open_icon.png");
        imgOpcion = new Image(file.toURI().toString());
        ImageView view = new ImageView(imgOpcion);

        view.setFitHeight(24);
        view.setFitWidth(24);

        btnEmpezar = new Button("Empezar Practica");
        btnResultado = new Button("Resultados");
        btnEmpezar.setVisible(false);
        btnResultado.setVisible(false);
        btnAbrir = new Button();
        btnAbrir.setGraphic(view);
        btnAbrir.setId("btn1");
        btnEmpezar.setId("btn1");
        btnResultado.setId("btn1");
        hBotons.getChildren().addAll(btnAbrir,lblTiempo,btnEmpezar,btnResultado);

        btnAbrir.setOnAction(event -> {
            tlbOpciones(1);
            if (fileA != null) {
                btnEmpezar.setVisible(true);
            }
        });

        btnResultado.setOnAction(event ->{
            char[] aCntText = comp.toCharArray();
            if (rsRes == 0) {
                for (char c : aCntText) {
                    if (String.valueOf(c).equals(" ")) {
                        resLetra++;
                    }
                }
                rsRes++;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resultado");
            alert.setHeaderText(null);
            alert.setContentText("Numero De Errores: " + cntError +
                    "\nNumero De Letras Escritas: " + (aCntText.length-resLetra+cntError));
            alert.showAndWait();
        });

        txtFuente = new TextArea();
        txtFuente.setEditable(false);
        txtFuente.setFont(fuente2);
        txtEscritura = new TextArea();
        txtEscritura.setEditable(false);
        txtEscritura.setFont(fuente2);

        btnEmpezar.setOnAction(event -> {
            txtEscritura.setEditable(true);
            txtEscritura.setOnKeyPressed(this);
            txtEscritura.setOnKeyReleased(this);
            txtEscritura.setOnKeyTyped(this);
            TimerTiemp();
            btnEmpezar.setVisible(false);
        });

        vTeclado = new VBox();
        hFuncion = new HBox();
        hNum = new HBox();
        hTab = new HBox();
        hMayus = new HBox();
        hShift = new HBox();
        hCtrl = new HBox();

        vTeclado.setSpacing(8);
        hFuncion.setSpacing(3);
        hNum.setSpacing(3);
        hTab.setSpacing(3);
        hMayus.setSpacing(3);
        hShift.setSpacing(3);
        hCtrl.setSpacing(3);

        setButtons(arBtnFunc,hFuncion,arSiFunc);
        setButtons(arBtnNum,hNum,arSiNum);
        setButtons(arBtnTab,hTab,arSiTab);
        setButtons(arBtnMayus,hMayus,arSiMayus);
        setButtons(arBtnShift,hShift,arSiShift);
        setButtons(arBtnCtrl,hCtrl,arSiCtrl);
        TamBtn();

        vTeclado.getChildren().addAll(hFuncion,hNum,hTab,hMayus,hShift,hCtrl);

        vBox.getChildren().addAll(hBotons, txtFuente, txtEscritura, vTeclado);
        escena = new Scene(vBox,730,500);

        File file3 = new File("src/main/java/com/example/topicos_v3/css/styletaqui.css");
        escena.getStylesheets().add(file3.toURI().toString());
    }

    private void tlbOpciones(int ipc){
        switch (ipc){
            case 1:
                flcArchivo = new FileChooser();
                flcArchivo.setTitle("Buscar Archivo...");
                flcArchivo.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("TXT","*.txt"));
                fileA = flcArchivo.showOpenDialog(this);
                //String conetido = flcArchivo.getAbsolutePath();
                //txtFuente.setText(conetido);
                //txtFuente.setText(fileA.getAbsolutePath());
                if (fileA != null) {
                    //System.out.println(fileA.getAbsolutePath());
                    AbrirAr(fileA);
                }
                //txtFuente.setText(documento);
                break;
        }
    }

    public void AbrirAr (File archivo){
        try{
            FileInputStream entrada = new FileInputStream(archivo);
            int asci;

            while ((asci = entrada.read()) != -1){
                char caracter = (char) asci;
                documento += caracter;
            }
        }catch (Exception e){e.getMessage();}

        txtFuente.setText(documento);
    }

    private void setButtons(Button[] arBtns,HBox hBtns, String[] arSimbolos){
        for (int i = 0; i < arBtns.length; i++) {
            arBtns[i] = new Button(arSimbolos[i]);
            hBtns.getChildren().add(arBtns[i]);
        }
    }

    private void TamBtn (){
        //Botones funciones
        arBtnFunc[0].setPrefWidth(64);
        arBtnFunc[1].setPrefWidth(45);
        arBtnFunc[2].setPrefWidth(45);
        arBtnFunc[3].setPrefWidth(45);
        arBtnFunc[4].setPrefWidth(45);
        arBtnFunc[5].setPrefWidth(45);
        arBtnFunc[6].setPrefWidth(45);
        arBtnFunc[7].setPrefWidth(45);
        arBtnFunc[8].setPrefWidth(45);
        arBtnFunc[9].setPrefWidth(45);
        arBtnFunc[10].setPrefWidth(45);
        arBtnFunc[11].setPrefWidth(45);
        arBtnFunc[12].setPrefWidth(45);
        arBtnFunc[13].setPrefWidth(58);

        //Botones Numeros
        arBtnNum[0].setPrefWidth(45);
        arBtnNum[1].setPrefWidth(45);
        arBtnNum[2].setPrefWidth(45);
        arBtnNum[3].setPrefWidth(45);
        arBtnNum[4].setPrefWidth(45);
        arBtnNum[5].setPrefWidth(45);
        arBtnNum[6].setPrefWidth(45);
        arBtnNum[7].setPrefWidth(45);
        arBtnNum[8].setPrefWidth(45);
        arBtnNum[9].setPrefWidth(45);
        arBtnNum[10].setPrefWidth(45);
        arBtnNum[11].setPrefWidth(45);
        arBtnNum[12].setPrefWidth(45);
        arBtnNum[13].setPrefWidth(75);

        //Botones Tab
        arBtnTab[0].setPrefWidth(76);
        arBtnTab[1].setPrefWidth(45);
        arBtnTab[2].setPrefWidth(45);
        arBtnTab[3].setPrefWidth(45);
        arBtnTab[4].setPrefWidth(45);
        arBtnTab[5].setPrefWidth(45);
        arBtnTab[6].setPrefWidth(45);
        arBtnTab[7].setPrefWidth(45);
        arBtnTab[8].setPrefWidth(45);
        arBtnTab[9].setPrefWidth(45);
        arBtnTab[10].setPrefWidth(45);
        arBtnTab[11].setPrefWidth(45);
        arBtnTab[12].setPrefWidth(45);
        arBtnTab[13].setPrefWidth(45);

        //Botones Mayus
        arBtnMayus[0].setPrefWidth(80);
        arBtnMayus[1].setPrefWidth(45);
        arBtnMayus[2].setPrefWidth(45);
        arBtnMayus[3].setPrefWidth(45);
        arBtnMayus[4].setPrefWidth(45);
        arBtnMayus[5].setPrefWidth(45);
        arBtnMayus[6].setPrefWidth(45);
        arBtnMayus[7].setPrefWidth(45);
        arBtnMayus[8].setPrefWidth(45);
        arBtnMayus[9].setPrefWidth(45);
        arBtnMayus[10].setPrefWidth(45);
        arBtnMayus[11].setPrefWidth(50);
        arBtnMayus[12].setPrefWidth(85);

        //Botones Shift
        arBtnShift[0].setPrefWidth(87);
        arBtnShift[1].setPrefWidth(45);
        arBtnShift[2].setPrefWidth(45);
        arBtnShift[3].setPrefWidth(45);
        arBtnShift[4].setPrefWidth(45);
        arBtnShift[5].setPrefWidth(45);
        arBtnShift[6].setPrefWidth(45);
        arBtnShift[7].setPrefWidth(45);
        arBtnShift[8].setPrefWidth(45);
        arBtnShift[9].setPrefWidth(45);
        arBtnShift[10].setPrefWidth(45);
        arBtnShift[11].setPrefWidth(66);
        //Botones del Ctrl
        arBtnCtrl[0].setPrefWidth(66);
        arBtnCtrl[1].setPrefWidth(42);
        arBtnCtrl[2].setPrefWidth(42);
        arBtnCtrl[3].setPrefWidth(42);
        arBtnCtrl[4].setPrefWidth(247);
        arBtnCtrl[5].setPrefWidth(45);
        arBtnCtrl[6].setPrefWidth(66);
        arBtnCtrl[7].setPrefWidth(66);
        arBtnCtrl[8].setPrefWidth(66);
    }

    @Override
    public void handle(KeyEvent event) {

        //String caracter = event.getCode().toString();
        //System.out.println(caracter);

        switch (event.getCode().toString()){
            case "ESCAPE" -> CambioDeColor(0,1);
            case "F1" -> CambioDeColor(1,1);
            case "F2" -> CambioDeColor(2,1);
            case "F3" -> CambioDeColor(3,1);
            case "F4" -> CambioDeColor(4,1);
            case "F5" -> CambioDeColor(5,1);
            case "F6" -> CambioDeColor(6,1);
            case "F7" -> CambioDeColor(7,1);
            case "F8" -> CambioDeColor(8,1);
            case "F9" -> CambioDeColor(9,1);
            case "F10" -> CambioDeColor(10,1);
            case "F11" -> CambioDeColor(11,1);
            case "F12" -> CambioDeColor(12,1);
            case "DELETE" -> CambioDeColor(13,1);
            case "|" -> CambioDeColor(0,2);
            case "DIGIT1" -> CambioDeColor(1,2);
            case "DIGIT2" -> CambioDeColor(2,2);
            case "DIGIT3" -> CambioDeColor(3,2);
            case "DIGIT4" -> CambioDeColor(4,2);
            case "DIGIT5" -> CambioDeColor(5,2);
            case "DIGIT6" -> CambioDeColor(6,2);
            case "DIGIT7" -> CambioDeColor(7,2);
            case "DIGIT8" -> CambioDeColor(8,2);
            case "DIGIT9" -> CambioDeColor(9,2);
            case "DIGIT0" -> CambioDeColor(10,2);
            case "QUOTE" -> CambioDeColor(11,2);
            case "¿" -> CambioDeColor(12,2);
            case "BACK_SPACE" -> CambioDeColor(13,2);
            case "TAB" -> CambioDeColor(0,3);
            case "Q" -> CambioDeColor(1,3);
            case "W" -> CambioDeColor(2,3);
            case "E" -> CambioDeColor(3,3);
            case "R" -> CambioDeColor(4,3);
            case "T" -> CambioDeColor(5,3);
            case "Y" -> CambioDeColor(6,3);
            case "U" -> CambioDeColor(7,3);
            case "I" -> CambioDeColor(8,3);
            case "O" -> CambioDeColor(9,3);
            case "P" -> CambioDeColor(10,3);
            case "DEAD_ACUTE" -> CambioDeColor(11,3);
            case "PLUS" -> CambioDeColor(12,3);
            case "BRACERIGHT" -> CambioDeColor(13,3);
            case "CAPS" -> CambioDeColor(0,4);
            case "A" -> CambioDeColor(1,4);
            case "S" -> CambioDeColor(2,4);
            case "D" -> CambioDeColor(3,4);
            case "F" -> CambioDeColor(4,4);
            case "G" -> CambioDeColor(5,4);
            case "H" -> CambioDeColor(6,4);
            case "J" -> CambioDeColor(7,4);
            case "K" -> CambioDeColor(8,4);
            case "L" -> CambioDeColor(9,4);
            case "Ñ" -> CambioDeColor(10,4);
            case "BRACELEFT" -> CambioDeColor(11,4);
            case "ENTER" -> CambioDeColor(12,4);
            case "SHIFT" -> CambioDeColor(0,5);
            case "Z" -> CambioDeColor(1,5);
            case "X" -> CambioDeColor(2,5);
            case "C" -> CambioDeColor(3,5);
            case "V" -> CambioDeColor(4,5);
            case "B" -> CambioDeColor(5,5);
            case "N" -> CambioDeColor(6,5);
            case "M" -> CambioDeColor(7,5);
            case "COMMA" -> CambioDeColor(8,5);
            case "PERIOD" -> CambioDeColor(9,5);
            case "MINUS" -> CambioDeColor(10,5);
            case "UP" -> CambioDeColor(11,5);
            case "CONTROL" -> CambioDeColor(0,6);
            case "FN" -> CambioDeColor(1,6);
            case "WINDOWS" -> CambioDeColor(2,6);
            case "ALT" -> CambioDeColor(3,6);
            case "SPACE" -> CambioDeColor(4,6);
            case "ALT_GRAPH" -> CambioDeColor(5,6);
            case "LEFT" -> CambioDeColor(6,6);
            case "DOWN" -> CambioDeColor(7,6);
            case "RIGHT" -> CambioDeColor(8,6);
        }
        if (!ban){
            Comparar();
        }
    }

    public void CambioDeColor(int num, int fila){
        switch (fila) {
            case 1 -> {
                if (!ban) arBtnFunc[num].setStyle("-fx-background-color: blue");
                 else arBtnFunc[num].setStyle("-fx-background-color: #6a9eda");
                ban = !ban;
            }
            case 2 -> {
                if (!ban) arBtnNum[num].setStyle("-fx-background-color: blue");
                else arBtnNum[num].setStyle("-fx-background-color: #6a9eda");
                ban = !ban;
            }
            case 3 -> {
                if (!ban) arBtnTab[num].setStyle("-fx-background-color: blue");
                 else arBtnTab[num].setStyle("-fx-background-color: #6a9eda");
                ban = !ban;
            }
            case 4 -> {
                if (!ban) arBtnMayus[num].setStyle("-fx-background-color: blue");
                else arBtnMayus[num].setStyle("-fx-background-color: #6a9eda");
                ban = !ban;
            }
            case 5 -> {
                if (!ban) arBtnShift[num].setStyle("-fx-background-color: blue");
                 else arBtnShift[num].setStyle("-fx-background-color: #6a9eda");
                ban = !ban;
            }
            case 6 -> {
                if (!ban) arBtnCtrl[num].setStyle("-fx-background-color: blue");
                 else arBtnCtrl[num].setStyle("-fx-background-color: #6a9eda");
                ban = !ban;
            }
        }
        if (!ban) AceptTecl(num,fila);
    }

    public void TimerTiemp (){

        tiemp = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Platform.runLater(
                    new Runnable() {
                        @Override
                        public void run() {
                            cnttiempS2++;
                            if (cnttiempS2 == 10) {
                                cnttiempS1++;
                                cnttiempS2 = 0;
                                if (cnttiempS1 == 6) {
                                    cnttiempS1 = 0;
                                    cnttiempM2++;
                                    if (cnttiempM2 == 10) {
                                        cnttiempM2 = 0;
                                        cnttiempM1++;
                                    }
                                }
                            }
                            lblTiempo.setText(cnttiempM1 + "" + cnttiempM2 + ":" + cnttiempS1 + cnttiempS2);
                        }
                    }
                );
            }
        });
        tiemp.start();
    }

    private void Comparar(){

        comp = txtEscritura.getText();
        //System.out.println(comp);
        //System.out.println(documento);

        if (documento.equals(comp)) {
            //System.out.println("Es igual el texto");
            txtEscritura.setEditable(false);
            btnResultado.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informe");
            alert.setHeaderText(null);
            alert.setContentText("El Texto Introducido Es Igual Al Texto Del Archivo");
            alert.showAndWait();
            tiemp.stop();
        }
    }

    public void AceptTecl(int num, int fila){

        if (fila == 2){
            if (num != 13) numLetras++;
        }else if (fila == 4) {
            if (num != 0 && num !=12) numLetras++;
        }else if (fila == 5) {
            if (num != 0 && num !=11) numLetras++;
        }
        if (fila == 2 && num == 13) {
            if (numLetras != 0) {
                numLetras--;
                cntError++;
            }
        }
    }
}
