package chinczyk;

/**
 * Klasa enum zawierające możliwe rodzaje(kolory) pól lub pionków
 */
public enum Type {
    RED(0), BLUE(11), GREEN(21), YELLOW(31), WHITE(0), BROWN(0);

    public final int start;

    /**
     * Konstruktor Type, który przypisuje poszczególnemu typowi podaną dla niego liczbe startową
     * @param start czyli wartość startowa, z której będzie zaczynał pionek poszczególnego koloru po wyrzuceniu szóstki
     */
    Type(int start)
    {
        this.start = start;
    }
}

