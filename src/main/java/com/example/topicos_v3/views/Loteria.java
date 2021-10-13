package com.example.topicos_v3.views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;

import java.awt.event.ActionListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

public class Loteria extends Stage{

    private String [] arImagenes = {"anubis.jpg","ares.jpg","artemisa.jpg","atenea.jpg",
                                    "azazel.jpg","basilisco.jpg","cerbero.jpg","chimera.png","cronos.jpg",//Primera Plantilla
                                    "cthulhu.jpg","djinn.jpg","dragon.jpg","fenrir.jpg","freya.jpg",
                                    "ganesha.png","grifo.jpg","hades.jpg","hefesto.jpg",//Segunda Plantilla
                                    "hela.jpg","hercules.jpg","hermes.jpg","hlobo.jpg","hydra.jpg",
                                    "isis.jpg","jinetes.jpg","jormungandr.jpg","leviatan.jpg",//Tercera Plantilla
                                    "loki.jpg","medusa.jpg","mictlantecuhtli.png","minotauro.jpg",
                                    "odin.jpg","osiris.jpg","poseidon.jpg","prometeo.jpg","quetzalcoatl.jpg",//Cuarta Plantilla
                                    "ra.png","rakshasa.jpg","shiva.jpg","sirena.jpg",
                                    "wendigo.jpg","wukong.png","xipetotec.jpg","ymir.jpg","zeus.jpg",//Quinta Plantilla
                                    "Adorso.jpg"};
    private Scene escena;
    private VBox vBox, vBox2;
    private HBox hBox1, hBox2;
    private Button btnAtras, btnSiguiente, btnSeleccionar;
    private Button [][] arBtnCartas = new Button[3][3];
    private Image imgCarta;
    private GridPane gdpPlantilla;
    private Label lblCarta;
    private File file, file2;
    private Timer timer;
    int a = 0, b = 1, c = 0, d = 0, Ncarta = 9, numeroAle, numeroAle2, contadorMazoIgual = 0, NumeroDeCartas[] = new int[45];
    int [] aleatorios = new int [45];
    boolean empezar = false, termino = false;

    public Loteria(){
        CrearUI();
        this.setTitle("Loteria");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){

        vBox = new VBox();
        hBox1 = new HBox();
        vBox2 = new VBox();
        btnAtras = new Button("< Plantilla Anterior");
        btnSiguiente = new Button("Plantilla Siguiente >");
        btnSeleccionar = new Button("Seleccionar Plantilla");

        btnSiguiente.setFont(Font.font("Cambria"));
        btnAtras.setFont(Font.font("Cambria"));
        btnSeleccionar.setFont(Font.font("Cambria"));

        hBox1.getChildren().addAll(btnAtras,btnSiguiente);
        hBox1.setSpacing(10);
        hBox2 = new HBox();
        gdpPlantilla = new GridPane();

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){

                arBtnCartas [j][i] = new Button();
                arBtnCartas [j][i].setPrefSize(180,160);
                file = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[a]);
                imgCarta = new Image(file.toURI().toString());
                ImageView view = new ImageView(imgCarta);
                view.setFitHeight(130);
                view.setFitWidth(110);
                view.setPreserveRatio(true);
                arBtnCartas [j][i].setGraphic(view);
                int numj = j, numi = i;
                arBtnCartas [j][i].setOnAction(event -> {Comparar(numi,numj); Parar();});
                gdpPlantilla.add(arBtnCartas[j][i],j,i);
                a++;
            }
        }

        for (int i = 0; i < 45; i++) {
            NumeroDeCartas[i]=i;
        }

        btnSiguiente.setOnAction(event -> {
            b++;
            if (b > 5) b = 1;
            CambioDPl();
        });

        btnAtras.setOnAction(event -> {
            b--;
            if (b < 1) b = 5;
            CambioDPl();
        });

        lblCarta = new Label();

        file2 = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[45]);
        imgCarta = new Image(file2.toURI().toString());
        ImageView view = new ImageView(imgCarta);

        btnSeleccionar.setOnAction(event -> {
            btnSiguiente.setVisible(false);
            btnAtras.setVisible(false);
            btnSeleccionar.setVisible(false);
            //System.out.println(Ncarta);
            empezar = true;
            numeroAle = (int) (Math.random() * ( 45  + 1 ));
            numeroAle2 = (int) (Math.random() * ( numeroAle + 1 ));

            if (d == 0){

                CartaAleatorio();
                d++;
            }
            //System.out.println("Ncarta " + Ncarta);
            //System.out.println("numeroAle " + numeroAle);
            //System.out.println("numeroAle2 " + numeroAle2);
            TimerCarta();
            //CambioCartaDorso();
        });

        btnSeleccionar.setId("Btns");
        btnAtras.setId("Btns");
        btnSiguiente.setId("Btns");

        view.setFitWidth(180);
        view.setFitHeight(200);
        view.setPreserveRatio(true);
        lblCarta.setGraphic(view);
        lblCarta.setAlignment(Pos.CENTER);

        vBox2.getChildren().addAll(lblCarta,btnSeleccionar);
        vBox2.setSpacing(30);

        hBox2.getChildren().addAll(gdpPlantilla, vBox2);
        hBox2.setSpacing(40);

        vBox.getChildren().addAll(hBox1,hBox2);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        escena = new Scene(vBox,800,535);

        File filecss = new File("src/main/java/com/example/topicos_v3/css/style.css");
        escena.getStylesheets().add(filecss.toURI().toString());
    }

    public void TimerCarta (){

        timer = new Timer(2000, new ActionListener(){

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Platform.runLater(
                    new Runnable() {
                        @Override
                        public void run() {
                            file2 = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[aleatorios[c]]);
                            imgCarta = new Image(file2.toURI().toString());
                            ImageView view2 = new ImageView(imgCarta);
                            view2.setFitWidth(180);
                            view2.setFitHeight(200);
                            view2.setPreserveRatio(true);
                            lblCarta.setGraphic(view2);
                            c++;

                            if (c == 45) {
                                termino = true;
                                Parar();
                            }
                        }
                    }
                );
            }
        });
        timer.start();
    }

    public void playSound() {
        try {
            String archAudio = "";
            if (contadorMazoIgual == 9) archAudio = "src/main/java/com/example/topicos_v3/Audios/nike_to_victory.wav";
            else archAudio = "src/main/java/com/example/topicos_v3/Audios/tyr_still_not_funny.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(archAudio).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error ");
            ex.printStackTrace();
        }
    }

    public void Parar (){

        if (termino) {
            String resultado = "";
            timer.stop();
            System.out.println("Termino");
            if ( contadorMazoIgual == 9) {
                resultado = "LOTERIAA!!!!!!!! \nGanaste!!!!";
                playSound();
            }else {
                resultado = "Buen Intendo Suerte Para La Proxima.... :(";
                playSound();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resultado");
            alert.setHeaderText(null);
            alert.setContentText(resultado);
            alert.showAndWait();
        }
    }

    public void Comparar(int i, int j){
        int quitar = 0;
        if(empezar){
            switch (i){
                case 0:
                    switch (j){
                        case 0 -> quitar = 9;
                        case 1 -> quitar = 8;
                        case 2 -> quitar = 7;
                    }
                    break;
                case 1:
                    switch (j){
                        case 0 -> quitar = 6;
                        case 1 -> quitar = 5;
                        case 2 -> quitar = 4;
                    }
                    break;
                case 2:
                    switch (j){
                        case 0 -> quitar = 3;
                        case 1 -> quitar = 2;
                        case 2 -> quitar = 1;
                    }
                    break;
            }

            File fileC = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[Ncarta-quitar]);
            arBtnCartas[j][i].setOnAction(event -> {
                //System.out.println("numero de carta" + Ncarta);
                //System.out.println(fileC);
                //System.out.println(file2);

                if (file2.equals(fileC)) {
                    //System.out.println("Es igual");
                    CambioDorso(i,j);
                    contadorMazoIgual++;
                    System.out.println(contadorMazoIgual);
                    if (contadorMazoIgual == 9) {
                        termino = true;
                        Parar();
                    }
                }
            });
        }
    }

    public void CambioDorso (int i, int j){

        File fileDC = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[45]);
        imgCarta = new Image(fileDC.toURI().toString());
        ImageView view = new ImageView(imgCarta);
        view.setFitHeight(130);
        view.setFitWidth(110);
        view.setPreserveRatio(true);
        arBtnCartas [j][i].setGraphic(view);
    }

    public void CambioDPl(){
        switch (b) {
            case 1 -> Ncarta = 0;
            case 2 -> Ncarta = 9;
            case 3 -> Ncarta = 18;
            case 4 -> Ncarta = 27;
            case 5 -> Ncarta = 36;
        }

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                file = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[Ncarta]);
                imgCarta = new Image(file.toURI().toString());
                ImageView view = new ImageView(imgCarta);
                view.setFitHeight(130);
                view.setFitWidth(110);
                view.setPreserveRatio(true);
                arBtnCartas [j][i].setGraphic(view);
                Ncarta++;
            }
        }
    }

    public void CartaAleatorio(){

        int cantidad = 45, index = 0;

        while(index < cantidad) {
            int propuesto = (int)(Math.random()*cantidad);
            boolean repetido = false;
            while(!repetido) {
                for(int i=0; i<index; i++) {
                    if(propuesto == aleatorios[i]) {
                        repetido = true;
                        break;
                    }
                }
                if(!repetido) {
                    aleatorios[index] = propuesto;
                    index++;
                }
            }
        }
    }
}