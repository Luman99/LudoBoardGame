package chinczyk;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import static chinczyk.Main.TILE_SIZE;

/**
 * Klasa Piece(Pionek) posiada typ pionka, , kształt, wartość współrzędnych x,y na których stał poprzenim razem i tych na których teraz stoi
 */
public class Piece extends StackPane {

    private Type type;
    private Ellipse bg;
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
     * Ustawia atrybut typ taki jak podany i na tej podstawie ustawia kolor Pionka
     * @param type przyjmuje jako parametr typ(Pionka)
     */
    public void setType(Type type)
    {
        this.type=type;

        if(type == Type.RED)
        {
            bg.setFill(Color.valueOf("#c40003"));
        }else if(type == Type.BLUE)
        {
            bg.setFill(Color.valueOf("#0003c4"));
        }else if(type == Type.GREEN)
        {
            bg.setFill(Color.valueOf("#00c403"));
        }else if(type == Type.YELLOW)
        {
            bg.setFill(Color.valueOf("#c4c403"));
        }else
        {
            bg.setFill(Color.valueOf("FFFFFF"));
        }

    }

    /**
     * Pozwala dostać się do prywatnego atrybutu
     * @return zwraca typ Pionka
     */
    public Type getType()
    {
        return type;
    }

    /**
     * Konstruktor Pionka, który na podstawie parametrów ustawia kolor pionka oraz jego współrzędne, zapewnia on również stały kształt pionka niezależnie od parametrów
     * @param type typ Pionka
     * @param x współrzędna x-owa
     * @param y współrzędna y-owa
     */
    public Piece(Type type, int x, int y)
    {
        this.type = type;

        move(x, y);

        bg= new Ellipse(TILE_SIZE * 0.31225, TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2)/2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2)/2 + TILE_SIZE * 0.07);

        if(type == Type.RED)
        {
            bg.setFill(Color.valueOf("#c40003"));
        }else if(type == Type.BLUE)
        {
            bg.setFill(Color.valueOf("#0003c4"));
        }else if(type == Type.GREEN)
        {
            bg.setFill(Color.valueOf("#00c403"));
        }else if(type == Type.YELLOW)
        {
            bg.setFill(Color.valueOf("#c4c403"));
        }else
        {
            bg.setFill(Color.valueOf("FFFFFF"));
        }


        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2)/2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2)/2);



        getChildren().addAll(bg);


        setOnMousePressed(e ->
        {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e ->
        {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    /**
     * Metoda, która zmienia nam położenie pionka
     * @param x współrzędna x-owa
     * @param y współrzędna y-owa
     */
    public void move(int x, int y)
    {
        oldX = x * TILE_SIZE;
        oldY = y *  TILE_SIZE;
        relocate(oldX, oldY);
    }

    /**
     * W przypadku gdy chcemy, żeby ruch nie zmienił nam położenia pionka
     */
    public void abortMove()
    {
        relocate(oldX, oldY);
    }
}
