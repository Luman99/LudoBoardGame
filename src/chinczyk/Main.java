package chinczyk;

import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Klasa główna odpowiedzialna za odpalanie aplikacji(gry) oraz posiadająca znaczną część mechanik
 */
public class Main extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 11;
    public static final int HEIGHT = 11;
    public int diceValue=0;
    public boolean canMove = false;
    public boolean canRoll = true;
    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    public Type whoseTurn= Type.RED; //zmienna służy synchronizacji kolejności graczy
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    public Type whoStarts;
    public boolean CzygraRed=false;
    public boolean CzygraBlue=false;
    public boolean CzygraGreen=false;
    public boolean CzygraYellow=false;

    public Integer b=1;


    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);
        KtoZaczyna();

        for( int y = 0; y < HEIGHT; y++)
        {
            for(int x=0; x< WIDTH; x++)
            {

                Tile tile = new Tile(x,y);

                board[x][y] = tile;

                tileGroup.getChildren().add(tile);
                Piece piece = null;
                if((x==0 && y==0) || (x==0 && y==1) || (x==1 && y==1) || (x==1 && y==0))
                {
                    piece = makePiece(Type.RED, x, y);
                }

                if((x==9 && y==0) || (x==10 && y==1) || (x==9 && y==1) || x==10 && y==0)
                {
                    piece = makePiece(Type.BLUE, x, y);
                }

                if((x==10 && y==10) || (x==10 && y==9) || (x==9 && y==10) || (x==9 && y==9))
                {
                    piece = makePiece(Type.GREEN, x, y);
                }

                if((x==0 && y==10) || (x==0 && y==9) || (x==1 && y==10) || (x==1 && y==9))
                {
                    piece = makePiece(Type.YELLOW, x, y);
                }
                if(x==1 && y==3)
                {
                    piece = makePiece(Type.WHITE, x, y);
                    whoseTurn=whoStarts;
                }

                if(piece != null)
                {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY, int liczbaOczek) {
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        CzyKoniec();
        // Czy rzuca użytkownik
        if (newX == 2 && newY == 3 || x0 == 1 && y0 == 3) {
            return new MoveResult(MoveType.ROLL);
        }


        //Te warunki zapobiegają poruszaniu się wstecz po pierwszym ruchu gracza, gdy wyrzuci drugi raz tę samą wartość
        if(board[newX][newY].number==board[x0][y0].getPiece().getType().start && whoseTurn!=Type.RED &&board[x0][y0].number!=0)
        {
            return new MoveResult(MoveType.NONE);
        }

        if(board[newX][newY].number==board[x0][y0].getPiece().getType().start+1 && whoseTurn==Type.RED &&board[x0][y0].number!=0)
        {
            return new MoveResult(MoveType.NONE);
        }



        if (board[x0][y0].getPiece().getType() == whoseTurn && board[newX][newY].type != Type.BROWN)
        {

            //Nie można swoim pionkiem na swojego
            if(board[newX][newY].hasPiece())
            {
                if(board[newX][newY].getPiece().getType() == piece.getType())
                {
                    return new MoveResult(MoveType.NONE);
                }
            }

            if((board[x0][y0].number + liczbaOczek) > ((board[x0][y0].getPiece().getType().start + 39)%41+1) && board[x0][y0].number<=((board[x0][y0].getPiece().getType().start + 39)%41+1) &&board[x0][y0].number!=0 ) //1 11 21 31  40 50(10) 60(20) 70(30)
            {

                if(board[x0][y0].type== Type.WHITE && board[newX][newY].type==board[x0][y0].getPiece().getType() && (!board[newX][newY].hasPiece()))
                {

                    if((board[newX][newY].number == (board[x0][y0].number + liczbaOczek) % 40) || (board[x0][y0].number + liczbaOczek) == 40)
                    {
                        return new MoveResult(MoveType.NORMAL);
                    }
                }
            }else {

                if ((board[newX][newY].number == (board[x0][y0].number + liczbaOczek) % 40) || (board[x0][y0].number + liczbaOczek) == 40) {
                    if (board[newX][newY].hasPiece() && board[newX][newY].getPiece().getType() != piece.getType()) {
                        return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
                    } else {
                        return new MoveResult(MoveType.NORMAL);
                    }

                }


                if (liczbaOczek == 6) {

                    if (piece.getType() == Type.RED) {
                        if (board[0][0].hasPiece() || board[0][1].hasPiece() || board[1][0].hasPiece() || board[1][1].hasPiece()) {
                            if (board[newX][newY].number == 1) {
                                if (board[newX][newY].hasPiece()) {
                                    return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
                                } else {
                                    return new MoveResult(MoveType.NORMAL);
                                }
                            }
                        }
                    } else if (piece.getType() == Type.BLUE) {
                        if (board[10][0].hasPiece() || board[10][1].hasPiece() || board[9][0].hasPiece() || board[9][1].hasPiece()) {
                            if (board[newX][newY].number == 11) {
                                if (board[newX][newY].hasPiece()) {
                                    return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
                                } else {
                                    return new MoveResult(MoveType.NORMAL);
                                }
                            }
                        }
                    } else if (piece.getType() == Type.GREEN) {
                        if (board[0][10].hasPiece() || board[1][10].hasPiece() || board[1][9].hasPiece() || board[0][9].hasPiece()) {
                            if (board[newX][newY].number == 21) {
                                if (board[newX][newY].hasPiece()) {
                                    return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
                                } else {
                                    return new MoveResult(MoveType.NORMAL);
                                }
                            }
                        }

                    } else if (piece.getType() == Type.YELLOW) {
                        if (board[10][10].hasPiece() || board[10][9].hasPiece() || board[9][10].hasPiece() || board[9][9].hasPiece()) {
                            if (board[newX][newY].number == 31) {
                                if (board[newX][newY].hasPiece()) {
                                    return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
                                } else {
                                    return new MoveResult(MoveType.NORMAL);
                                }

                            }
                        }
                    }


                }


            }
        }
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel)
    {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }




    /**
     * Metoda odpowiedzialna za wyświetlanie menu gry, oraz samej gry
     * @param primaryStage jako parametr przyjmyuje Stage, podstawowy element naszej aplikacji
     */
    @Override
    public void start(Stage primaryStage)
    {
        Button button = new Button();
        Button button2 = new Button();
        Button button3 = new Button();
        Button button4 = new Button();

        button.setText("1 Gracz");
        button2.setText("2 Graczy");
        button3.setText("3 Graczy");
        button4.setText("4 Graczy");

        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                b=1;

                Button button = new Button();
                Button button2 = new Button();
                Button button3 = new Button();
                Button button4 = new Button();

                button.setText("Czerwony");
                button2.setText("Niebieski");
                button3.setText("Zielony");
                button4.setText("Żółty");

                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);

                Text scenetitle = new Text("Wybierz Liczbę graczy");
                grid.add(scenetitle, 5/2, 0, 5, 1);

                button.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraRed)
                        {
                            b-=1;
                            CzygraRed=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button2.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraBlue)
                        {
                            b-=1;
                            CzygraBlue=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button3.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraGreen)
                        {
                            b-=1;
                            CzygraGreen=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button4.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraYellow)
                        {
                            b-=1;
                            CzygraYellow=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });


                grid.add(button, 1, 1);
                grid.add(button2, 2, 1);
                grid.add(button3, 3, 1);
                grid.add(button4, 4, 1);


                Scene scene = new Scene(grid, 950, 550);

                primaryStage.setTitle("Chińczyk, wybór kolorów");
                primaryStage.setScene(scene);
                primaryStage.show();


            }
        });


        button2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                b=2;

                Button button = new Button();
                Button button2 = new Button();
                Button button3 = new Button();
                Button button4 = new Button();

                button.setText("Czerwony");
                button2.setText("Niebieski");
                button3.setText("Zielony");
                button4.setText("Żółty");

                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);

                Text scenetitle = new Text("Wybierz Liczbę graczy");
                grid.add(scenetitle, 5/2, 0, 5, 1);

                button.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraRed)
                        {
                            b-=1;
                            CzygraRed=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button2.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraBlue)
                        {
                            b-=1;
                            CzygraBlue=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button3.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraGreen)
                        {
                            b-=1;
                            CzygraGreen=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button4.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraYellow)
                        {
                            b-=1;
                            CzygraYellow=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });


                grid.add(button, 1, 1);
                grid.add(button2, 2, 1);
                grid.add(button3, 3, 1);
                grid.add(button4, 4, 1);


                Scene scene = new Scene(grid, 950, 550);

                primaryStage.setTitle("Chińczyk, wybór kolorów");
                primaryStage.setScene(scene);
                primaryStage.show();


            }
        });


        button3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                b=3;

                Button button = new Button();
                Button button2 = new Button();
                Button button3 = new Button();
                Button button4 = new Button();

                button.setText("Czerwony");
                button2.setText("Niebieski");
                button3.setText("Zielony");
                button4.setText("Żółty");

                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);

                Text scenetitle = new Text("Wybierz Liczbę graczy");
                grid.add(scenetitle, 5/2, 0, 5, 1);

                button.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraRed)
                        {
                            b-=1;
                            CzygraRed=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button2.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraBlue)
                        {
                            b-=1;
                            CzygraBlue=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button3.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraGreen)
                        {
                            b-=1;
                            CzygraGreen=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button4.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraYellow)
                        {
                            b-=1;
                            CzygraYellow=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });


                grid.add(button, 1, 1);
                grid.add(button2, 2, 1);
                grid.add(button3, 3, 1);
                grid.add(button4, 4, 1);


                Scene scene = new Scene(grid, 950, 550);

                primaryStage.setTitle("Chińczyk, wybór kolorów");
                primaryStage.setScene(scene);
                primaryStage.show();


            }
        });


        button4.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                b=4;

                Button button = new Button();
                Button button2 = new Button();
                Button button3 = new Button();
                Button button4 = new Button();

                button.setText("Czerwony");
                button2.setText("Niebieski");
                button3.setText("Zielony");
                button4.setText("Żółty");

                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);

                Text scenetitle = new Text("Wybierz Liczbę graczy");
                grid.add(scenetitle, 5/2, 0, 5, 1);

                button.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraRed)
                        {
                            b-=1;
                            CzygraRed=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button2.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraBlue)
                        {
                            b-=1;
                            CzygraBlue=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button3.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraGreen)
                        {
                            b-=1;
                            CzygraGreen=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });

                button4.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        if(!CzygraYellow)
                        {
                            b-=1;
                            CzygraYellow=true;
                        }
                        if(b==0)
                        {
                            Label secondLabel = new Label("I'm a Label on new Window");

                            StackPane secondaryLayout = new StackPane();
                            secondaryLayout.getChildren().add(secondLabel);


                            Scene secondScene = new Scene(createContent());

                            Stage newWindow = new Stage();
                            newWindow.setTitle("Second Stage");
                            newWindow.setScene(secondScene);

                            newWindow.show();
                        }
                    }
                });


                grid.add(button, 1, 1);
                grid.add(button2, 2, 1);
                grid.add(button3, 3, 1);
                grid.add(button4, 4, 1);


                Scene scene = new Scene(grid, 950, 550);

                primaryStage.setTitle("Chińczyk, wybór kolorów");
                primaryStage.setScene(scene);
                primaryStage.show();


            }
        });



        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Text scenetitle = new Text("Wybierz Liczbę graczy");
        grid.add(scenetitle, 5/2, 0, 5, 1);


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                //userName.setText(ile.getText());
                // a=(ile.getText());
                // b=(pwBox.getLength());
                // System.out.println(a);
                // System.out.println(b);
            }
        };


        grid.add(button, 1, 1);
        grid.add(button2, 2, 1);
        grid.add(button3, 3, 1);
        grid.add(button4, 4, 1);


        Scene scene = new Scene(grid, 950, 550);

        primaryStage.setTitle("Chińczyk, wybór kolorów");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Zmiana tury(czyja teraz będzie tura), bierze pod uwagę czyja była poprzednio tura oraz, który gracz jest aktywny
     */
    public void zmianaKolejki()
    {

        System.out.println(whoseTurn);
        if((whoseTurn.start+9)%40== Type.RED.start)
        {
            whoseTurn= Type.RED;
            if(!CzygraRed)
            {
                zmianaKolejki();
            }
        }else if((whoseTurn.start+11)%40== Type.BLUE.start)
        {
            whoseTurn= Type.BLUE;
            if(!CzygraBlue)
            {
                zmianaKolejki();
            }
        }else if((whoseTurn.start+10)%40== Type.GREEN.start)
        {
            whoseTurn= Type.GREEN;
            if(!CzygraGreen)
            {
                zmianaKolejki();
            }
        }else if((whoseTurn.start+10)%40== Type.YELLOW.start)
        {
            whoseTurn= Type.YELLOW;
            if(!CzygraYellow)
            {
                zmianaKolejki();
            }
        }
        System.out.println(whoseTurn);
    }


    private Piece makePiece(Type type, int x, int y)
    {


        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e ->
        {

            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());




            MoveResult result;



            if(newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT)
            {
                result = new MoveResult(MoveType.NONE);
            }else
            {

                result = tryMove(piece, newX, newY, diceValue);
            }
            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());


            switch(result.getType())
            {
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    if(canMove==true)
                    {
                        piece.move(newX, newY);
                        board[x0][y0].setPiece(null);
                        board[newX][newY].setPiece(piece);
                        System.out.println("liczbaoczek" + diceValue);
                        CzyKoniec();
                    }else
                    {
                        piece.abortMove();
                    }
                    canRoll = true;
                    canMove= false;
                    break;
                case KILL:
                    System.out.printf("okej Tu whcodzi");
                    piece.move(newX, newY);

                    board[x0][y0].setPiece(null);

                    Type oldType=board[newX][newY].getPiece().getType();

                    Piece otherPiece = result.getPiece();

                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    board[newX][newY].setPiece(piece);
                    zbicie(result, oldType);

                    break;
                case ROLL:
                    piece.abortMove();

                    if(board[1][3].getPiece().getType()== Type.WHITE)
                    {
                        board[1][3].getPiece().setType(whoseTurn);
                        canRoll = false;
                        diceValue = Rzut();
                        canMove=true;
                        break;
                    }

                    canMove=true;
                    if(diceValue==6)
                    {
                        board[1][3].getPiece().setType(whoseTurn);
                        canRoll = true;
                    }else
                    {
                        zmianaKolejki();
                        board[1][3].getPiece().setType(whoseTurn);
                        canRoll = false;
                    }

                    diceValue = Rzut();
                    break;

            }
        });

        return piece;
    }

    /**
     * Rzut, który zmienia wygląd kostki na podstawie wyrzuconych oczek
     * @return zwraca rzuconą liczbę oczek
     */
    public Integer Rzut()
    {
        Random generator = new Random();
        Integer liczbaOczek = generator.nextInt(6)+1 ;
        System.out.println(liczbaOczek);


        Dice dice = new Dice(liczbaOczek, 2, 3);

        pieceGroup.getChildren().remove(board[2][3].getKostka());
        board[2][3].setKostka(null);

        board[2][3].setKostka(dice);
        pieceGroup.getChildren().add(dice);


        return liczbaOczek;
    }

    /**
     * Rzut teoretyczny, nie zmieniający stanu(wyglądu) kostki
     * @return zwraca rzuconą liczbę oczek
     */
    public Integer RzutBezKostki()
    {
        Random generator = new Random();
        Integer liczbaOczek = generator.nextInt(6)+1 ;

        return liczbaOczek;
    }

    /**
     * Metoda zbicie na podstawie tego, jaki pionek został zbity ustawia go na odpowiednie miejscie startowe
     * @param result jako parametr podajemy ruch, który się właśnie wykonał
     * @param oldType jako parametr podajemy typ pionka, którt został zbity
     */
    public void zbicie(MoveResult result, Type oldType)
    {

        if(oldType== Type.RED)
        {

            if(!board[0][0].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),0,0);
                pieceGroup.getChildren().add(newPiece);
                board[0][0].setPiece(newPiece);
            }else if(!board[0][1].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),0,1);
                pieceGroup.getChildren().add(newPiece);
                board[0][1].setPiece(newPiece);
            }else if(!board[1][0].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),1,0);
                pieceGroup.getChildren().add(newPiece);
                board[1][0].setPiece(newPiece);
            }else if(!board[1][1].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),1,1);
                pieceGroup.getChildren().add(newPiece);
                board[1][1].setPiece(newPiece);
            }
        }else if(oldType== Type.BLUE)
        {
            if(!board[10][0].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),10,0);
                pieceGroup.getChildren().add(newPiece);
                board[10][0].setPiece(newPiece);
            }else if(!board[10][1].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),10,1);
                pieceGroup.getChildren().add(newPiece);
                board[10][1].setPiece(newPiece);
            }else if(!board[9][0].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),9,0);
                pieceGroup.getChildren().add(newPiece);
                board[9][0].setPiece(newPiece);
            }else if(!board[9][1].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),9,1);
                pieceGroup.getChildren().add(newPiece);
                board[9][1].setPiece(newPiece);
            }
        }else if(oldType== Type.GREEN)
        {
            if(!board[10][10].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),10,10);
                pieceGroup.getChildren().add(newPiece);
                board[10][10].setPiece(newPiece);
            }else if(!board[10][9].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),10,9);
                pieceGroup.getChildren().add(newPiece);
                board[10][9].setPiece(newPiece);
            }else if(!board[9][10].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),9,10);
                pieceGroup.getChildren().add(newPiece);
                board[9][10].setPiece(newPiece);
            }else if(!board[9][9].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),9,9);
                pieceGroup.getChildren().add(newPiece);
                board[9][9].setPiece(newPiece);
            }
        }else if(oldType== Type.YELLOW)
        {
            if(!board[0][10].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),0,10);
                pieceGroup.getChildren().add(newPiece);
                board[0][10].setPiece(newPiece);
            }else if(!board[0][9].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),0,9);
                pieceGroup.getChildren().add(newPiece);
                board[0][9].setPiece(newPiece);
            }else if(!board[1][10].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),1,10);
                pieceGroup.getChildren().add(newPiece);
                board[1][10].setPiece(newPiece);
            }else if(!board[1][9].hasPiece())
            {
                Piece newPiece = makePiece(result.getPiece().getType(),1,9);
                pieceGroup.getChildren().add(newPiece);
                board[1][9].setPiece(newPiece);
            }

        }
    }

    /**
     * Jeśli wszystkie Pionki któregoś koloru znajdują się na polach mety gra zostaje zakończona, a zwycięzca wypisany na konsoli
     */
    public void CzyKoniec()
    {

        if(board[1][5].hasPiece() && board[2][5].hasPiece() && board[3][5].hasPiece() && board[4][5].hasPiece())
        {
            System.out.println("Wygrał Czerwony\n");
            System.exit(0);
        }else if(board[5][1].hasPiece() && board[5][2].hasPiece() && board[5][3].hasPiece() && board[5][4].hasPiece())
        {
            System.out.println("Wygrał Niebieski\n");
            System.exit(0);
        }else if(board[6][5].hasPiece() && board[7][5].hasPiece() && board[8][5].hasPiece() && board[9][5].hasPiece())
        {
            System.out.println("Wygrał Zielony\n");
            System.exit(0);
        }else if(board[5][6].hasPiece() && board[5][7].hasPiece() && board[5][8].hasPiece() && board[5][9].hasPiece())
        {
            System.out.println("Wygrał Żółty\n");
            System.exit(0);
        }
    }


    /**
     * W uczciwy sposób losuje, który gracz będzie zaczynał, działa to analogiczny sposób jak w fizycznym chińczyku w celu oddania realizmu
     */
    public void KtoZaczyna()
    {
        Integer najwieksza = 0;
        Integer licznik = 0;
        Integer graczCzerwony=0;
        Integer graczNiebieski=0;
        Integer graczZielony=0;
        Integer graczZolty=0;
        ///////********* Najpierw losowanie kto zaczyna *****////////

        while(true)
        {
            graczNiebieski = RzutBezKostki();
            graczCzerwony = RzutBezKostki();
            graczZielony = RzutBezKostki();
            graczZolty = RzutBezKostki();

            if(CzygraRed)
            {
                najwieksza = graczCzerwony;
                whoStarts= Type.RED;
            }
            if((graczNiebieski > najwieksza) && CzygraBlue)
            {
                najwieksza = graczNiebieski;
                whoStarts= Type.BLUE;
            }
            if(graczZielony > najwieksza && CzygraGreen)
            {
                najwieksza = graczZielony;
                whoStarts= Type.GREEN;
            }
            if(graczZolty > najwieksza && CzygraYellow)
            {
                najwieksza = graczZolty;
                whoStarts= Type.YELLOW;
            }
            licznik=0;
            if(najwieksza == graczCzerwony)
            {
                licznik = licznik +1;
            }
            if(najwieksza == graczNiebieski)
            {
                licznik = licznik +1;
            }
            if(najwieksza == graczZielony)
            {
                licznik = licznik +1;
            }
            if(najwieksza == graczZolty)
            {
                licznik = licznik +1;
            }
            if(licznik == 1)
            {
                System.out.println("zaczyna " + whoStarts);
                break;
            }
        }

    }

    /**
     *
     * Mój głowny main, który odpala naszą aplikację
     * @param args argument typu String[]
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
