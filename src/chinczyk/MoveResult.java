package chinczyk;

/**
 * Klasa Wynik ruchu, która zawiera w sobie rodzaj ruchu, który został wykonany oraz obiekt Pionka, który ten ruch wykonał
 */
public class MoveResult {

    private MoveType type;
    private Piece piece;

    /**
     * Metoda pozwalająca dostać się do prywantego atrybutu
     * @return zwraca typ ruchu
     */
    public MoveType getType()
    {
        return type;
    }

    /**
     * Metoda pozwalająca dostać się do prywatnego aytrybutu
     * @return zwraca obiekt pionka
     */
    public Piece getPiece()
    {
        return piece;
    }

    /**
     * Konstruktor, który po podaniu typu ruchu tworzy nam MoveResult, ustawiając piece(Pionek) na null
     * @param type podajemy typ ruchu jako parametr
     */
    public MoveResult(MoveType type)
    {
        this(type, null);
    }

    /**
     * Konstruktor, który po podaniu typu ruchu oraz pionka tworzy nam MoveResult
     * @param type podajemy typ ruchu
     * @param piece podajemy obiekt pionka
     */
    public MoveResult(MoveType type, Piece piece)
    {
        this.type = type;
        this.piece = piece;
    }
}
