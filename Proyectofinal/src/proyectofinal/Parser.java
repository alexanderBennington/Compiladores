package proyectofinal;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
/*
    private final Token identificador = new Token(TipoToken.IDENTIFICADOR, "");
    private final Token select = new Token(TipoToken.SELECT, "select");
    private final Token from = new Token(TipoToken.FROM, "from");
    private final Token distinct = new Token(TipoToken.DISTINCT, "distinct");
    private final Token coma = new Token(TipoToken.COMA, ",");
    private final Token punto = new Token(TipoToken.PUNTO, ".");
    private final Token asterisco = new Token(TipoToken.ASTERISCO, "*");
    private final Token finCadena = new Token(TipoToken.EOF, "");
*/
    private final Token identificador = new Token(TipoToken.IDENTIFICADOR, "");
    private final Token Gclass = new Token(TipoToken.CLASS, "class");
    private final Token Gvar = new Token(TipoToken.VAR, "var");
    private final Token Gfun = new Token(TipoToken.FUN, "fun");
    private final Token Gfor = new Token(TipoToken.FOR, "for");
    private final Token Gif = new Token(TipoToken.IF, "if");
    private final Token Gprint = new Token(TipoToken.PRINT, "print");
    private final Token Greturn = new Token(TipoToken.RETURN, "return");
    private final Token Gwhile = new Token(TipoToken.WHILE, "while");
    private final Token Gelse = new Token(TipoToken.ELSE, "else");
    private final Token Gor = new Token(TipoToken.OR, "or");
    private final Token Gand = new Token(TipoToken.AND, "and");
    private final Token Gtrue = new Token(TipoToken.TRUE, "true");
    private final Token Gfalse = new Token(TipoToken.FALSE, "false");
    private final Token Gnull = new Token(TipoToken.NULL, "null");
    private final Token Gthis = new Token(TipoToken.THIS, "this");
    private final Token Gnumber = new Token(TipoToken.NUMBER, "number");
    private final Token Gstring = new Token(TipoToken.STRING, "string");
    private final Token Gsuper = new Token(TipoToken.SUPER, "super");
    private final Token mayor = new Token(TipoToken.MAYOR, ">");
    private final Token menor = new Token(TipoToken.MENOR, "<");
    private final Token guion = new Token(TipoToken.GUION, "-");
    private final Token cruz = new Token(TipoToken.CRUZ, "+");
    private final Token diagonal = new Token(TipoToken.DIAGONAL, "/");
    private final Token asterisco = new Token(TipoToken.ASTERISCO, "*");
    private final Token admiracion = new Token(TipoToken.ADMIRACION, "!");
    private final Token igual = new Token(TipoToken.IGUAL, "=");
    private final Token coma = new Token(TipoToken.COMA, ",");
    private final Token punto = new Token(TipoToken.PUNTO, ".");
    private final Token parentesis_abierto = new Token(TipoToken.PARENTESIS_ABIERTO, "(");
    private final Token parentesis_cerrado = new Token(TipoToken.PARENTESIS_CERRADO, ")");
    private final Token llave_abierta = new Token(TipoToken.LLAVE_ABIERTA, "{");
    private final Token llave_cerrada = new Token(TipoToken.LLAVE_CERRADA, "}");
    private final Token punto_coma = new Token(TipoToken.PUNTO_COMA, ";");
    private final Token finCadena = new Token(TipoToken.EOF, "");
    

    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void parse(){
        i = 0;
        preanalisis = tokens.get(i);
        PROGRAM();

        if(!hayErrores && !preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }
        else if(!hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Consulta válida");
        }

        /*if(!preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }else if(!hayErrores){
            System.out.println("Consulta válida");
        }*/
    }

    void PROGRAM(){
        DECLARATION();
    }

    void DECLARATION(){
        if(hayErrores) return;

        if(preanalisis.equals(Gclass)){
            CLASS_DECL();
            DECLARATION();
        }
        else if(preanalisis.equals(Gfun)){
            FUN_DECL();
            DECLARATION();
        }
        else if(preanalisis.equals(Gvar)){
            VAR_DECL();
            DECLARATION();
        }
        else if(preanalisis.equals(identificador) || preanalisis.equals(Gfor) || preanalisis.equals(Gif) 
                || preanalisis.equals(Gprint) || preanalisis.equals(Greturn) || preanalisis.equals(Gwhile)){
            STATEMENT();
            DECLARATION();
        }
    }
    
    void CLASS_DECL(){
        if(hayErrores) return;

        if(preanalisis.equals(Gclass)){
            coincidir(Gclass);
            coincidir(identificador);
            CLASS_INHER();
            coincidir(llave_abierta);
            FUNCTIONS();
            coincidir(llave_cerrada);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void CLASS_INHER(){
        if(hayErrores) return;

        if(preanalisis.equals(menor)){
            coincidir(menor);
            coincidir(identificador);
        }
    }

    void FUN_DECL(){
        if(hayErrores) return;

        if(preanalisis.equals(Gfun)){
            coincidir(Gfun);
            FUNCTION();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void VAR_DECL(){
        if(hayErrores) return;

        if(preanalisis.equals(Gfun)){
            coincidir(Gfun);
            FUNCTION();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    


    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un  " + t.tipo);

        }
    }

}

