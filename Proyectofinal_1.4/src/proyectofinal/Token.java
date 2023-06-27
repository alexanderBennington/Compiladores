package proyectofinal;

public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
    }

    public String toString(){
        return tipo + " " + lexema + " " + (literal == null ? " " : literal.toString());
    }
    
    // Métodos auxiliares
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMBER:
            case STRING:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case CRUZ:
            case GUION:
            case ASTERISCO:
            case DIAGONAL:
            case IGUAL:
            case MAYOR:
            case MAYOR_IGUAL:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case IF:
            case PRINT:
            case ELSE:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case IF:
            case ELSE:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            case ASTERISCO:
            case DIAGONAL:
                return 7;
            case CRUZ:
            case GUION:
                return 6;
            case MAYOR:
            case MAYOR_IGUAL:
            case MENOR:
            case MENOR_IGUAL:
                return 5;
            case IGUAL_QUE:
            case DIFERENTE:
                return 4;
            case AND:
                return 3;
            case OR:
                return 2;
            case IGUAL:
                return 1;
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case ASTERISCO:
            case DIAGONAL:
            case CRUZ:
            case GUION:
            case IGUAL:
            case MAYOR:
            case MAYOR_IGUAL:
            case MENOR:
            case MENOR_IGUAL:
            case IGUAL_QUE:
            case DIFERENTE:
            case AND:
            case OR:
                return 2;
        }
        return 0;
    }
}

