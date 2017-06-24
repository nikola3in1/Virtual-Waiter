package Client;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grafika extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    ArrayList<Sto> stolovi;
    final int width = 980;
    final int height = 717;
    final int widthSto = 400;
    final int heightSto = 500;
    final int btnWidth = 200;
    final int btnHeight = 100;


    private final EventHandler<MouseEvent> OtvoriMagacin = event ->{
        Stage stage = new Stage();
        VBox root = new VBox();
        HBox dodajToMagacin =new HBox();
        BorderPane ucitajIzMagaina= new BorderPane();
        Label lbl = new Label("Dodaj u magacin:");
        Label lbl1 = new Label("Proizvod:");
        TextField tf = new TextField();
        Label lbl2 = new Label("Kolicina:");
        TextField tf1 = new TextField();
        Label lbl3 = new Label("Cena:");
        TextField tf2 = new TextField();
        Button btn = new Button("Dodaj");
        root.setPadding(new Insets(30,8,8,20));
        dodajToMagacin.setPadding(new Insets(15,8,8,8));
        lbl1.setAlignment(tf.getAlignment());
        lbl2.setLabelFor(tf1);
        tf1.setPrefSize(50,20);
        tf1.setMaxSize(50,20);
        tf2.setPrefSize(50,20);
        tf2.setMaxSize(50,20);

        dodajToMagacin.setSpacing(12);
        dodajToMagacin.getChildren().addAll(lbl1,tf,lbl2,tf1,lbl3,tf2,btn);
        Button btn1 = new Button("Unesi iz magacina");
        btn1.setTranslateY(btn1.getLayoutY()+30);
        btn1.setPrefSize(150,60);
        btn1.setMaxSize(150,60);
        ucitajIzMagaina.setCenter(btn1);
        root.getChildren().addAll(lbl,dodajToMagacin);
        root.getChildren().add(ucitajIzMagaina);



        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean) {
                    stage.close();
                }
            }
        });


        Scene scene = new Scene(root,630,230);
        stage.setMaxHeight(230);
        stage.setMaxWidth(630);


        ////Client
        //unos iz magacina
        btn1.setOnMouseReleased(mouseEvent -> {
            Client.posaljiRequestZaUcitavanje();
            Client.ucitajIzBaze();
        });
        //dodaj u bazu
        btn.setOnMouseReleased(mouseEvent -> {
            if(tf.getText()!=null && !tf.getText().equals("") && tf1.getText()!=null && !tf1.getText().equals("") && tf2.getText()!=null && !tf2.getText().equals("") )
                Client.dodajToBaza(tf.getText(),Integer.parseInt(tf2.getText()),Integer.parseInt(tf1.getText()));
        });

        stage.setTitle("Magacin");
        stage.setScene(scene);
        stage.show();

    };


    private final EventHandler<MouseEvent> OtvoriPopis = event ->{
        HashMap<String,Integer> prodataRoba = Pazar.prodataRoba;
        Integer pazar = Pazar.getPazarDin();
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();
        Button btn = new Button("Upisi u bazu");
        Label zarada = new Label("Zarada: "+pazar);
        Label prodatiProizvodi  = new Label("Prodati proizvodi:");
        zarada.setStyle("-fx-font: 17 arial;");
        prodatiProizvodi.setStyle("-fx-font: 17 arial;");
        vBox.getChildren().add(prodatiProizvodi);

        for (Map.Entry<String, Integer> entry : prodataRoba.entrySet()) {
            Label temp = new Label(" - "+entry.getKey() + ", kolicina: " + entry.getValue());
            temp.setStyle("-fx-font: 16 arial;");
            vBox.getChildren().add(temp);
        }

        root.setTop(zarada);
        root.setCenter(vBox);
        root.setBottom(btn);
        root.setPadding(new Insets(15));

        btn.setOnMousePressed(mouseEvent -> {
            Client.upisiPopis(prodataRoba,pazar);
            Pazar.stampaj();
        });

        Scene scene = new Scene(root,widthSto,heightSto);
        stage.setMaxHeight(230);
        stage.setMaxWidth(630);
        stage.setTitle("Popis");
        stage.setMaxWidth(widthSto);
        stage.setMaxHeight(heightSto);
        stage.setScene(scene);
        stage.show();

    };



        private final EventHandler<MouseEvent> OtvoriSto = event -> {
        Magacin magacin = new Magacin();
        Sto thisSto = null;
        Stage stage = new Stage();
        GridPane glavniPane = new GridPane();
        HBox naplata = new HBox();
        BorderPane root = new BorderPane();
        Button btn = new Button("Dodaj");
        Button btn1= new Button("Naplati");
        RadioButton din = new RadioButton("Din");
        RadioButton eur = new RadioButton("Eur");
        Label racun = new Label("Racun:                            ");
        root.setPadding(new Insets(5));
        root.setTop(glavniPane);
        racun.setTranslateX(2);
        naplata.getChildren().addAll(racun,din,eur,btn1);
        naplata.setSpacing(15);
        naplata.setPadding(new Insets(0,0,0,widthSto-400));
        root.setBottom(naplata);

        glavniPane.setPadding(new Insets(5));
        HBox unos = new HBox();

        VBox velikiPane = new VBox();
        velikiPane.setSpacing(5);
        boolean addNew=false;

        Scene scene = new Scene(root, widthSto, heightSto);
        String[] tempBr = event.getSource().toString().split("]");
        int brStola = Integer.parseInt(tempBr[1].replace("'", ""));
        System.out.println("BR STOLA:" + brStola);

        ///Pronalazenje/Instanciranje Stola
        if (!stolovi.isEmpty()) {
            for (Sto s : stolovi) {
                if (s.brStola == brStola) {
                    thisSto = s;
                    addNew=false;
                    break;
                } else{
                    addNew=true;}
            }
        }else{
            thisSto= new Sto();
            thisSto.brStola = brStola;
            stolovi.add(thisSto);}

        if(addNew){
            thisSto = new Sto();
            thisSto.brStola = brStola;
            stolovi.add(thisSto);
            addNew=false;
        }



        ///Unos HBOX
        ObservableList<String> options = Magacin.dostupniProizvodi();
        ComboBox comboBox = new ComboBox(options);
        TextField tf = new TextField();
        tf.setPrefSize(50,35);
        tf.setMaxSize(50,35);
        comboBox.setPrefSize(245, 35);
        comboBox.setMaxSize(245, 35);
        btn.setPrefSize(105, 35);
        btn.setMaxSize(105, 35);

        unos.setSpacing(5);
        unos.getChildren().add(comboBox);
        unos.getChildren().add(tf);
        unos.getChildren().add(btn);
        glavniPane.add(unos, 0, 0);

        //RadioButtons
        din.setOnMousePressed(mouseEvent -> {
            eur.setSelected(false);
        });
        eur.setOnMousePressed(mouseEvent -> {
            din.setSelected(false);
        });

        ///Dodavanje Porudzbine
        Sto finalThisSto = thisSto;
        btn.setOnMousePressed(mouseEvent -> {
            try{
            if( Integer.parseInt(tf.getText())>0 && Integer.parseInt(tf.getText()) < 999){

            if(!finalThisSto.dodajPorudzbinu(comboBox.getValue().toString(),Integer.parseInt(tf.getText()))){
                comboBox.getItems().remove(comboBox.getValue());
                comboBox.getItems().clear();
                comboBox.setItems(Magacin.dostupniProizvodi());
            }
            ispisProizvoda(finalThisSto,velikiPane,glavniPane,comboBox);}
        }catch (NumberFormatException nfe){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("Wrong input!");
                alert.showAndWait();
            }});

        ///Gasenje prozora ako nije u fokusu
        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean) {
                    stage.close();
                }
            }
        });

        if(din.isSelected()){
            eur.setSelected(false);
        }else if(eur.isSelected()){
            din.setSelected(false);
        }

        //Naplata
        DecimalFormat df = new DecimalFormat("#.00");

        btn1.setOnMouseClicked(mouseEvent -> {
            if(din.isSelected()){
                racun.setText("Racun:"+finalThisSto.naplatiDin()+"                          ");
                ispisProizvoda(finalThisSto,velikiPane,glavniPane,comboBox);
            }else if(eur.isSelected()){
                racun.setText("Racun:"+df.format(finalThisSto.naplatiEur())+"                       ");
                ispisProizvoda(finalThisSto,velikiPane,glavniPane,comboBox);
            }
        });




        ///Ispis porudzbine
        ispisProizvoda(thisSto,velikiPane,glavniPane,comboBox);




        stage.setTitle("Sto broj: " + brStola);
        stage.setMaxWidth(widthSto);
        stage.setMaxHeight(heightSto);
        stage.setScene(scene);
        stage.show();
    };



    @Override
    public void start(Stage primaryStage) {
        (new Thread(new Evro())).start();
        stolovi = new ArrayList<>();
        Image image = new Image("Client/Restoran.png");
        Pane root = new Pane();
        VBox menu = new VBox();
        Group stolovi = new Group();

        Scene scene = new Scene(root, width, height);


        //Pozadinska Slika
        BackgroundImage myBI = new BackgroundImage(image,
                BackgroundRepeat.SPACE, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(width, height, false, false, true, false));
        root.setBackground(new Background(myBI));


        ///Meni
        Button btn = new Button("Magacin");
        btn.setTranslateX(650);
        btn.setTranslateY(20);
        Button btn2 = new Button("Popis");
        btn2.setTranslateX(650);
        btn2.setTranslateY(20);
        btn.setMaxSize(20, 10);
        btn2.setMaxSize(20, 10);
        btn.setPrefSize(btnWidth, btnHeight);
        btn2.setPrefSize(btnWidth, btnHeight);
        btn.setMaxSize(btnWidth, btnHeight);
        btn2.setMaxSize(btnWidth, btnHeight);
        menu.setPadding(new Insets(width / 19.6, height / 7.2, 50, 0));
        menu.setCache(true);
        menu.setSpacing(10);
        menu.getChildren().add(btn);
        menu.getChildren().add(btn2);



        btn.setOnMouseClicked(OtvoriMagacin);
        btn2.setOnMouseClicked(OtvoriPopis);


            //Stolovi
        ArrayList<Sto> stoloviLista = new ArrayList<Sto>();
        for (int i = 1; i < 47; i++) {
            Sto temp = new Sto(i + "");
            temp.init();
            temp.setTranslateX(Sto.getX(i - 1));
            temp.setTranslateY(Sto.getY(i - 1));
            if (i == 31) {
                temp.setPrefSize(149, 20);
            } else if (i > 42) {
                temp.setPrefSize(63, 113);
            }
            temp.brStola = i;
            temp.setOnMouseClicked(OtvoriSto);
            stoloviLista.add(temp);
        }

        stolovi.getChildren().addAll(stoloviLista);
        root.getChildren().addAll(menu, stolovi);

        primaryStage.setTitle("Restoran");
        primaryStage.setMaxWidth(width);
        primaryStage.setMaxHeight(height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    void ispisProizvoda(Sto finalThisSto,VBox velikiPane,GridPane glavniPane , ComboBox comboBox){
        if(finalThisSto.isInitPorudzbina()){
            velikiPane.getChildren().clear();
            glavniPane.getChildren().removeAll(velikiPane);
            for (Map.Entry<String,Integer> entry : finalThisSto.getPorudzbina().entrySet()){
                if(entry.getValue()<1){
                    continue;
                }
                HBox red= new HBox();
                velikiPane.setPadding(new Insets(8));
                red.setSpacing(5);
                Label lbl1 = new Label(entry.getKey());
                Label lbl2 = new Label(" x ");
                Label lbl3 = new Label(entry.getValue().toString());
                Button btn1 = new Button("X");
                lbl1.setStyle("-fx-font: 17 arial;");
                lbl2.setStyle("-fx-font: 17 arial;");
                lbl3.setStyle("-fx-font: 17 arial;");
                btn1.setStyle("-fx-font: 10 arial; -fx-base: #a80c03;");
                btn1.setOnMouseClicked(mouseEvent -> {
                    if(Integer.parseInt(lbl3.getText().toString())>1) {
                        lbl3.setText(finalThisSto.ispraviPorudzbinu(lbl1.getText().toString()) + "");
                        comboBox.getItems().clear();
                        comboBox.setItems(Magacin.dostupniProizvodi());
                    }else{
                        finalThisSto.ispraviPorudzbinu(lbl1.getText().toString());
                        red.getChildren().clear();
                    }

                });
                red.getChildren().addAll(lbl1,lbl2,lbl3,btn1);
                velikiPane.getChildren().add(red);
                glavniPane.getChildren().removeAll(velikiPane);
                glavniPane.add(velikiPane,0,1);
            }
        }
    }
}
