package chinczyk;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Klasa Tile(Pole) zawierająca typ danego pola oraz jego numer, posiada również atrybut piece(Pionka) oraz dice(Kostki)
 */
public class Tile extends Rectangle {
    Type type;
    Integer number;

    private Piece piece;
    private Dice dice;

    /**
     * Pozwala sprawdzić czy Pole ma na sobie pionka(posiada obiekt piece)
     * @return zwraca True jeśli piece nie jest null
     */
    public boolean hasPiece()
    {
        return piece != null;
    }

    /**
     * Pozwala dostać się do prywatnego atrybutu
     * @return zwraca obiekt Pionka
     */
    public Piece getPiece() {
        return piece;
    }


    /**
     * Pozwala zmienić wartość prywatnego atrybutu
     * @param piece ustawia wartość atrybutu piece na ten podany jako parametr
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }


    /**
     * Konstruktor tworzy obiekt Tile(pole) na podstawie podanych współrzędnych, w zależności ile one wynoszą ustawiany jest konretny numer pola, typ oraz jego kolor
     * @param x współrzędna x-owa
     * @param y współrzędna y-owa
     */
    public Tile(int x, int y)
    {
        setWidth(Main.TILE_SIZE);
        setHeight(Main.TILE_SIZE);

        relocate(x * Main.TILE_SIZE, y * Main.TILE_SIZE );


        if(x==0 && y==4)
        {
            setFill(Color.valueOf("FF8888"));
            this.number=1;
            this.type = Type.WHITE;
        }else if(x==6 && y==0)
        {
            setFill(Color.valueOf("8888FF"));
            this.number=11;
            this.type = Type.WHITE;
        }else if(x==10 && y==6)
        {
            setFill(Color.valueOf("00FF77"));
            this.number=21;
            this.type = Type.WHITE;
        }else if(x==4 && y==10)
        {
            setFill(Color.valueOf("FFFFBB"));
            this.number=31;
            this.type = Type.WHITE;
        }else if((x==0 && y==0) || (x==1 && y==1) || (x==1 && y==5) || (x==3 && y==5))
        {
            if(x==1 && y==5)
            {
                setFill(Color.valueOf("FF8888"));
                this.number=1;

            }else if(x==3 && y==5)
            {
                setFill(Color.valueOf("8888FF"));
                this.number=3;

            }else
            {
                this.number=0;
            }

            setFill(Color.valueOf("red"));
            this.type = Type.RED;
        }else if((x==0 && y==1) || (x==1 && y==0) || (x==2 && y==5) || (x==4 && y==5))
        {

            if(x==2 && y==5)
            {
                setFill(Color.valueOf("FF8888"));
                this.number=2;

            }else if(x==4 && y==5)
            {
                setFill(Color.valueOf("8888FF"));
                this.number=4;

            }else
            {
                this.number=0;
            }

            setFill(Color.valueOf("880000"));
            this.type = Type.RED;
        }else if((x==10 && y==0) || (x==9 && y==1) || (x==5 && y==1) || (x==5 && y==3))
        {
            if(x==5 && y==1)
            {
                setFill(Color.valueOf("FF8888"));
                this.number=11;

            }else if(x==5 && y==3)
            {
                setFill(Color.valueOf("8888FF"));
                this.number=13;

            }else
            {
                this.number=0;
            }
            setFill(Color.valueOf("blue"));
            this.type = Type.BLUE;
        }else if((x==9 && y==0) || (x==10 && y==1) || (x==5 && y==2) || (x==5 && y==4))
        {
            if(x==5 && y==2)
            {
                setFill(Color.valueOf("FF8888"));
                this.number=12;

            }else if(x==5 && y==4)
            {
                setFill(Color.valueOf("8888FF"));
                this.number=14;

            }else
            {
                this.number=0;
            }
            setFill(Color.valueOf("000088"));
            this.type = Type.BLUE;
        }else if((x==10 && y==10) || (x==9 && y==9) || (x==9 && y==5) || (x==7 && y==5))
        {
            if(x==9 && y==5)
            {
                setFill(Color.valueOf("FF8888"));
                this.number=21;

            }else if(x==7 && y==5)
            {
                setFill(Color.valueOf("8888FF"));
                this.number=23;

            }else
            {
                this.number=0;
            }
            setFill(Color.valueOf("green"));
            this.type = Type.GREEN;
        }else if((x==10 && y==9) || (x==9 && y==10) || (x==8 && y==5) || (x==6 && y==5))
        {
            if(x==8 && y==5)
            {
                setFill(Color.valueOf("FF8888"));
                this.number=22;

            }else if(x==6 && y==5)
            {
                setFill(Color.valueOf("8888FF"));
                this.number=24;

            }else
            {
                this.number=0;
            }
            setFill(Color.valueOf("003300"));
            this.type = Type.GREEN;
        }else if((x==0 && y==10) || (x==1 && y==9) || (x==5 && y==9) || (x==5 && y==7))
        {
            if(x==5 && y==9)
            {
                setFill(Color.valueOf("FF8888"));
                this.number=31;

            }else if(x==5 && y==7)
            {
                setFill(Color.valueOf("8888FF"));
                this.number=33;

            }else
            {
                this.number=0;
            }
            setFill(Color.valueOf("yellow"));
            this.type = Type.YELLOW;
        }else if((x==0 && y==9) || (x==1 && y==10) || (x==5 && y==6) || (x==5 && y==8))
        {
            if(x==5 && y==6)
            {
                setFill(Color.valueOf("FF8888"));
                this.number=34;

            }else if(x==5 && y==8)
            {
                setFill(Color.valueOf("8888FF"));
                this.number=32;

            }else
            {
                this.number=0;
            }
            setFill(Color.valueOf("666600"));
            this.type = Type.YELLOW;
        }else if((x==4 || y==4 || y==6 || x==6) && ((x+y)%2==0))
        {

            if(x==2 & y==4)
            {
                this.number=3;

            }else if(x==4 & y==4)
            {
                this.number=5;

            }else if(x==4 & y==2)
            {
                this.number=7;

            }else if(x==4 & y==0)
            {
                this.number=9;

            }else if(x==6 & y==0)
            {
                this.number=11;

            }else if(x==6 & y==2)
            {
                this.number=13;
                ;
            }else if(x==6 & y==4)
            {
                this.number=15;

            }else if(x==8 & y==4)
            {
                this.number=17;

            }else if(x==10 & y==4)
            {
                this.number=19;

            }else if(x==10 & y==6)
            {
                this.number=21;

            }else if(x==8 & y==6)
            {
                this.number=23;
                ;
            }else if(x==6 & y==6)
            {
                this.number=25;

            }else if(x==6 & y==8)
            {
                this.number=27;

            }else if(x==6 & y==10)
            {
                this.number=29;

            }else if(x==4 & y==10)
            {
                this.number=31;

            }else if(x==4 & y==8)
            {
                this.number=33;

            }else if(x==4 & y==6)
            {
                this.number=35;

            }else if(x==2 & y==6)
            {
                this.number=37;

            }else if(x==0 & y==6)
            {
                this.number=39;

            }else
            {
                this.number=0;
            }



            setFill(Color.valueOf("FFFFFF"));
            this.type = Type.WHITE;
        }else if(x==4 || y==4 || y==6 || x==6 || (x==0 && y==5) || (x==5 && y==0) || (x==5 && y==10) || (x==10 && y==5))
        {

            if(x==1 & y==4)
            {
                this.number=2;

            }else if(x==3 & y==4)
            {
                this.number=4;

            }else if(x==4 & y==3)
            {
                this.number=6;

            }else if(x==4 & y==1)
            {
                this.number=8;

            }else if(x==5 & y==0)
            {
                this.number=10;

            }else if(x==6 & y==1)
            {
                this.number=12;

            }else if(x==6 & y==3)
            {
                this.number=14;

            }else if(x==7 & y==4)
            {
                this.number=16;

            }else if(x==9 & y==4)
            {
                this.number=18;
                ;
            }else if(x==10 & y==5)
            {
                this.number=20;

            }else if(x==9 & y==6)
            {
                this.number=22;

            }else if(x==7 & y==6)
            {
                this.number=24;

            }else if(x==6 & y==7)
            {
                this.number=26;

            }else if(x==6 & y==9)
            {
                this.number=28;

            }else if(x==5 & y==10)
            {
                this.number=30;

            }else if(x==4 & y==9)
            {
                this.number=32;

            }else if(x==4 & y==7)
            {
                this.number=34;

            }else if(x==3 & y==6)
            {
                this.number=36;

            }else if(x==1 & y==6)
            {
                this.number=38;

            }else if(x==0 & y==5)
            {
                this.number=40;

            }else
            {
                this.number=0;
            }

            setFill(Color.valueOf("C0C0C0"));
            this.type = Type.WHITE;
        }else
        {
            setFill(Color.valueOf("886550"));

            this.number=0;

            this.type = Type.BROWN;
        }

    }

    /**
     * Pozwala dostać się do prywatnego atrybutu
     * @return zwraca nam obiekt kostki
     */
    public Dice getKostka() {
        return dice;
    }

    /**
     * Pozwala zmienić wartość prywatnego atrybutu
     * @param dice po podaniu parametru dice(Kostka) ustawia atrybut prywatny na ten podany jak parametr
     */
    public void setKostka(Dice dice) {
        this.dice = dice;
    }
}
