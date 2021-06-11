package chinczyk;

import static chinczyk.Main.TILE_SIZE;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.scene.shape.Rectangle;

/**
 * Klasa Dice(kostki), która służy do pokazywania obecnej wartości wyrzuconych oczek
 */
public class Dice extends StackPane {

    private Type type;

    private double mouseX, mouseY;
    private double oldX, oldY;

    /**
     * Pozwala dostać się do prywantego atrybutu
     * @return zwraca współrzędną oldX czyli poprzednią wartość współrzędnej X
     */
    public double getOldX()
    {
        return oldX;
    }

    /**
     * Pozwala dostać się do prywantego atrybutu
     * @return zwraca współrzędną oldY czyli poprzednią wartość współrzędnej Y
     */
    public double getOldY()
    {
        return oldY;
    }

    /**
     * Pozwala dostać się do prywatnego atrybutu
     * @return zwraca typ Kostki
     */
    public Type getType()
    {
        return type;
    }

    /**
     * Konstruktor kostki, ustawia ją w konretne miejscie podane przez współrzędne, a na podstawie liczbie oczek nakłada na nią czarne kropki w odpowiednie miejsca aby wyglądała ja strona kostki z poszczęgólna wartością
     * @param diceValue wartość kostki, ile oczek ma obecna kostki
     * @param x współrzędna x-owa
     * @param y współrzędna y-owa
     */
    public Dice(Integer diceValue, int x, int y)
    {

        move(x, y);


        Rectangle bg = new Rectangle(TILE_SIZE * 0.9, TILE_SIZE * 0.9);
        Circle cg1 = new Circle(TILE_SIZE * 1, TILE_SIZE * 1,4);
        Circle cg2 = new Circle(TILE_SIZE * 1, TILE_SIZE ,4);
        Circle cg3 = new Circle(TILE_SIZE * 1, TILE_SIZE ,4);
        Circle cg4 = new Circle(TILE_SIZE * 1, TILE_SIZE ,4);
        Circle cg5 = new Circle(TILE_SIZE * 1, TILE_SIZE ,4);
        Circle cg6 = new Circle(TILE_SIZE * 1, TILE_SIZE ,4);
        Circle cg7 = new Circle(TILE_SIZE * 1, TILE_SIZE ,4);





        cg2.setTranslateX((TILE_SIZE * -0.30 ));
        cg2.setTranslateY((TILE_SIZE * -0.30 ));

        cg3.setTranslateX((TILE_SIZE * 0.3 ));
        cg3.setTranslateY((TILE_SIZE * 0.3 ));

        cg4.setTranslateX((TILE_SIZE * -0.30 ));
        cg4.setTranslateY((TILE_SIZE * 0.30 ));

        cg5.setTranslateX((TILE_SIZE * 0.3 ));
        cg5.setTranslateY((TILE_SIZE * -0.3 ));

        cg6.setTranslateX((TILE_SIZE * 0.30 ));

        cg7.setTranslateX((TILE_SIZE * -0.3 ));

        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);


        bg.setFill(Color.valueOf("FFFFFF"));


        cg1.setFill(Color.valueOf("000000"));
        cg2.setFill(Color.valueOf("000000"));
        cg3.setFill(Color.valueOf("000000"));
        cg4.setFill(Color.valueOf("000000"));
        cg5.setFill(Color.valueOf("000000"));
        cg6.setFill(Color.valueOf("000000"));
        cg7.setFill(Color.valueOf("000000"));

        bg.setStrokeWidth(TILE_SIZE * 0.03);


        bg.setStroke(Color.BLACK);

        getChildren().addAll(bg);
        if(diceValue==1)
        {
            getChildren().addAll(cg1);

        }else if(diceValue==2)
        {

            getChildren().addAll(cg2);
            getChildren().addAll(cg3);
        }else if(diceValue==3)
        {
            getChildren().addAll(cg1);
            getChildren().addAll(cg2);
            getChildren().addAll(cg3);
        }else if(diceValue==4)
        {
            getChildren().addAll(cg2);
            getChildren().addAll(cg3);
            getChildren().addAll(cg4);
            getChildren().addAll(cg5);

        }else if(diceValue==5)
        {
            getChildren().addAll(cg1);
            getChildren().addAll(cg2);
            getChildren().addAll(cg3);
            getChildren().addAll(cg4);
            getChildren().addAll(cg5);


        }else if(diceValue==6)
        {
            getChildren().addAll(cg2);
            getChildren().addAll(cg3);
            getChildren().addAll(cg4);
            getChildren().addAll(cg5);
            getChildren().addAll(cg6);
            getChildren().addAll(cg7);
        }



    }



    /**
     * Metoda, która zmienia nam położenie kostki
     * @param x współrzędna x-owa
     * @param y współrzędna y-owa
     */
    public void move(int x, int y)
    {
        oldX = x * Main.TILE_SIZE;
        oldY = y *  Main.TILE_SIZE;
        relocate(oldX, oldY);
    }

    /**
     * W przypadku gdy chcemy, żeby ruch nie zmienił nam położenia kostki
     */
    public void abortMove()
    {
        relocate(oldX, oldY);
    }



}

