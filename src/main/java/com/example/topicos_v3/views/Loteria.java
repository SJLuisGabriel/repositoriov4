package com.example.topicos_v3.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

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
    int a = 0, b = 1, c = 0,contador = 0, Ncarta = 9;

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

        hBox1.getChildren().addAll(btnAtras,btnSiguiente);
        hBox1.setSpacing(10);
        hBox2 = new HBox();
        gdpPlantilla = new GridPane();


        CambioDP(arBtnCartas);

        btnSiguiente.setOnAction(event -> {
            b++;
            if (b > 5) b = 1;
            Remover();
            CambioDP(arBtnCartas);
            //Generar();
        });

        btnAtras.setOnAction(event -> {
            b--;
            if (b < 1) b = 5;
            Remover();

        });

        lblCarta = new Label();

        file2 = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[45]);
        imgCarta = new Image(file2.toURI().toString());
        ImageView view = new ImageView(imgCarta);

        btnSeleccionar.setOnAction(event -> {
            //btnSiguiente.setVisible(false);
            //btnAtras.setVisible(false);
            //btnSeleccionar.setVisible(false);
            System.out.println(Ncarta);

            Timer timer = new Timer();
            TimerTask tarea = new TimerTask() {
                @Override

                public void run() {

                }
            };
            CartaA(lblCarta,arImagenes);
            timer.schedule(tarea,1000,1000);

        });


        Butooo();

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
    }

    public void Butooo(){

        arBtnCartas[0][0].setOnAction(event -> {
            file = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[Ncarta-9]);
            System.out.println("numero de carta  " + Ncarta);
            if (file.equals(file2)) {
                System.out.println("Son iguales");
                gdpPlantilla.getChildren().remove(arBtnCartas[0][0]);
                GenerarDorso(0,0);
            }
        });

        arBtnCartas[1][0].setOnAction(event -> {
            file = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[1]);
            if (file.equals(file2)) {
                System.out.println("Son iguales");
                gdpPlantilla.getChildren().remove(arBtnCartas[1][0]);
                for (int i = 0; i <= 1; i++){
                    GenerarDorso(i,0);
                }
            }
        });
    }

    public void CartaA(Label lblCambio, String[] arImage){

        System.out.println(c);
        file2 = new File("src/main/java/com/example/topicos_v3/image/"+arImage[c]);
        imgCarta = new Image(file2.toURI().toString());
        ImageView view2 = new ImageView(imgCarta);
        view2.setFitWidth(180);
        view2.setFitHeight(200);
        view2.setPreserveRatio(true);
        lblCambio.setGraphic(view2);
        lblCambio.setAlignment(Pos.CENTER);
        c++;
    }

    public void GenerarDorso(int a1, int a2){

        arBtnCartas [a1][a2] = new Button();
        arBtnCartas [a1][a2].setPrefSize(180,160);
        file = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[45]);
        imgCarta = new Image(file.toURI().toString());
        ImageView view = new ImageView(imgCarta);
        view.setFitHeight(130);
        view.setFitWidth(110);
        view.setPreserveRatio(true);
        arBtnCartas[a1][a2].setGraphic(view);
        gdpPlantilla.add(arBtnCartas[a1][a2],a2,a2);
    }

    public void Remover(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                gdpPlantilla.getChildren().remove(arBtnCartas[j][i]);
            }
        }
    }

    public void CambioDP(Button[][] arbtncartas, GridPane gdpplantilla ){
        switch (b) {
            case 1 -> Ncarta = 0;
            case 2 -> Ncarta = 9;
            case 3 -> Ncarta = 18;
            case 4 -> Ncarta = 27;
            case 5 -> Ncarta = 36;
        }

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                arbtncartas [j][i] = new Button();
                arbtncartas [j][i].setPrefSize(180,160);
                file = new File("src/main/java/com/example/topicos_v3/image/"+arImagenes[Ncarta]);
                imgCarta = new Image(file.toURI().toString());
                ImageView view = new ImageView(imgCarta);
                view.setFitHeight(130);
                view.setFitWidth(110);
                view.setPreserveRatio(true);
                arbtncartas[j][i].setGraphic(view);
                gdpplantilla.add(arbtncartas[j][i],j,i);
                Ncarta++;
            }
        }
    }
}