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
    private final Token Gnumber = new Token(TipoToken.NUMBER, "");
    private final Token Gstring = new Token(TipoToken.STRING, "");
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
    private final Token diferente = new Token(TipoToken.DIFERENTE, "!=");
    private final Token igual_que = new Token(TipoToken.IGUAL_QUE, "==");
    private final Token mayor_igual = new Token(TipoToken.MAYOR_IGUAL, ">=");
    private final Token menor_igual = new Token(TipoToken.MENOR_IGUAL, "<=");
    
    private String comilla = "\"";
    
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
        else if(preanalisis.equals(Gfor) || preanalisis.equals(Gif) 
                || preanalisis.equals(Gprint) || preanalisis.equals(Greturn) || preanalisis.equals(Gwhile) || preanalisis.equals(identificador)){
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

        if(preanalisis.equals(Gvar)){
            coincidir(Gvar);
            coincidir(identificador);
            VAR_INIT();
            coincidir(punto_coma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void VAR_INIT(){
        if(hayErrores) return;

        if(preanalisis.equals(igual)){
            coincidir(igual);
            EXPRESSION();
        }
    }
    
    void STATEMENT(){
        if(hayErrores) return;

        if(preanalisis.equals(Gfor)){
            FOR_STMT();
        }
        else if(preanalisis.equals(Gif)){
            IF_STMT();
        }
        else if(preanalisis.equals(Gprint)){
            PRINT_STMT();
        }
        else if(preanalisis.equals(Greturn)){
            RETURN_STMT();
        }
        else if(preanalisis.equals(Gwhile)){
            WHILE_STMT();
        }
        else if(preanalisis.equals(llave_abierta)){
            BLOCK();
        }
        else if(preanalisis.equals(identificador)){
            EXPR_STMT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void EXPR_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            EXPRESSION();
            coincidir(punto_coma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void FOR_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(Gfor)){
            coincidir(Gfor);
            coincidir(parentesis_abierto);
            FOR_STMT_1();
            FOR_STMT_2();
            FOR_STMT_3();        
            coincidir(parentesis_cerrado);
            STATEMENT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void FOR_STMT_1(){
        if(hayErrores) return;

        if(preanalisis.equals(Gvar)){
            VAR_DECL();
        }
        else if(preanalisis.equals(identificador)){
            EXPR_STMT();
        }
        else if(preanalisis.equals(punto_coma)){
            coincidir(punto_coma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void FOR_STMT_2(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            EXPRESSION();
            coincidir(punto_coma);
        }
        else if(preanalisis.equals(punto_coma)){
            coincidir(punto_coma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void FOR_STMT_3(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            EXPRESSION();
        }
    }
    
    void IF_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(Gif)){
            coincidir(Gif);
            coincidir(parentesis_abierto);
            EXPRESSION();
            coincidir(parentesis_cerrado);
            STATEMENT();
            ELSE_STATEMENT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void ELSE_STATEMENT(){
        if(hayErrores) return;

        if(preanalisis.equals(Gelse)){
            coincidir(Gelse);
            STATEMENT();
        }
    }
    
    void PRINT_STMT(){
        if(hayErrores) return;
        
        if(preanalisis.equals(Gprint)){
            coincidir(Gprint);
            EXPRESSION();
            coincidir(punto_coma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void RETURN_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(Greturn)){
            coincidir(Greturn);
            RETURN_EXP_OPC();
            coincidir(punto_coma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void RETURN_EXP_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            EXPRESSION();
        }
    }

    void WHILE_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(Gwhile)){
            coincidir(Gwhile);
            coincidir(parentesis_abierto);
            EXPRESSION();   
            coincidir(parentesis_cerrado);
            STATEMENT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void BLOCK(){
        if(hayErrores) return;

        if(preanalisis.equals(llave_abierta)){
            coincidir(llave_abierta);
            BLOCK_DECL();   
            coincidir(llave_cerrada);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void BLOCK_DECL(){
        if(hayErrores) return;
        
        DECLARATION();   
        //BLOCK_DECL();

    }
    
    void EXPRESSION(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            ASSIGNMENT();   
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void ASSIGNMENT(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            LOGIC_OR();
            ASSIGNMENT_OPC();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void ASSIGNMENT_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(igual)){
            coincidir(igual);
            EXPRESSION();
        }
    }
    
    void LOGIC_OR(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            LOGIC_AND();
            LOGIC_OR_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void LOGIC_OR_2(){
        if(hayErrores) return;

        if(preanalisis.equals(Gor)){
            coincidir(Gor);
            LOGIC_AND();
            LOGIC_OR_2();
        }
    }
    
    void LOGIC_AND(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            EQUALITY();
            LOGIC_AND_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void LOGIC_AND_2(){
        if(hayErrores) return;

        if(preanalisis.equals(Gand)){
            coincidir(Gand);
            EQUALITY();
            LOGIC_AND_2();
        }
    }
    
    void EQUALITY(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            COMPARISON();
            EQUALITY_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void EQUALITY_2(){
        if(hayErrores) return;

        if(preanalisis.equals(diferente)){
            coincidir(diferente);
            i++;
            preanalisis = tokens.get(i);
            COMPARISON();
            EQUALITY_2();
        }
        else if(preanalisis.equals(igual_que)){
            coincidir(igual_que);
            i++;
            preanalisis = tokens.get(i);
            COMPARISON();
            EQUALITY_2();
        }
    }
    
    void COMPARISON(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            TERM();
            COMPARISON_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void COMPARISON_2(){
        if(hayErrores) return;

        if(preanalisis.equals(mayor)){
            coincidir(mayor);
            TERM();
            COMPARISON_2();
        }
        else if(preanalisis.equals(mayor_igual)){
            coincidir(mayor_igual);
            i++;
            preanalisis = tokens.get(i);
            TERM();
            COMPARISON_2();
        }
        else if(preanalisis.equals(menor)){
            coincidir(menor);
            TERM();
            COMPARISON_2();
        }
        else if(preanalisis.equals(menor_igual)){
            coincidir(menor_igual);
            i++;
            preanalisis = tokens.get(i);
            TERM();
            COMPARISON_2();
        }
    }
    
    void TERM(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            FACTOR();
            TERM_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void TERM_2(){
        if(hayErrores) return;

        if(preanalisis.equals(guion)){
            coincidir(guion);
            FACTOR();
            TERM_2();
        }
        else if(preanalisis.equals(cruz)){
            coincidir(cruz);
            FACTOR();
            TERM_2();
        }
    }
    
    void FACTOR(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            UNARY();
            FACTOR_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void FACTOR_2(){
        if(hayErrores) return;

        if(preanalisis.equals(diagonal)){
            coincidir(diagonal);
            UNARY();
            FACTOR_2();
        }
        else if(preanalisis.equals(asterisco)){
            coincidir(asterisco);
            UNARY();
            FACTOR_2();
        }
    }
    
    void UNARY(){
        if(hayErrores) return;

        if(preanalisis.equals(admiracion)){
            coincidir(admiracion);
            UNARY();
        }
        else if(preanalisis.equals(guion)){
            coincidir(guion);
            UNARY();
        }
        else if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            CALL();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void CALL(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            PRIMARY();
            CALL_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void CALL_2(){
        if(hayErrores) return;

        if(preanalisis.equals(parentesis_abierto)){
            coincidir(parentesis_abierto);
            ARGUMENTS_OPC();
            coincidir(parentesis_cerrado);
            CALL_2();
        }
        else if(preanalisis.equals(punto)){
            coincidir(punto);
            coincidir(identificador);
            CALL_2();
        }
    }
    
    void CALL_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador) || preanalisis.equals(Gstring) || preanalisis.equals(Gnumber)){
            CALL();
            coincidir(punto);
        }
    }
    
    void PRIMARY(){
        if(hayErrores) return;

        if(preanalisis.equals(Gtrue)){
            coincidir(Gtrue);
        }
        else if(preanalisis.equals(Gfalse)){
            coincidir(Gfalse);
        }
        else if(preanalisis.equals(Gnull)){
            coincidir(Gnull);
        }
        else if(preanalisis.equals(Gthis)){
            coincidir(Gthis);
        }
        else if(preanalisis.equals(Gnumber)){
            coincidir(Gnumber);
        }
        else if(preanalisis.equals(Gstring)){
            coincidir(Gstring);
        }
        else if(preanalisis.equals(identificador)){
            coincidir(identificador);
        }
        else if(preanalisis.equals(parentesis_abierto)){
            coincidir(parentesis_abierto);
            EXPRESSION();
            coincidir(parentesis_cerrado);
        }
        else if(preanalisis.equals(Gsuper)){
            coincidir(Gsuper);
            coincidir(punto);
            coincidir(identificador);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    

    void FUNCTION(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            coincidir(identificador);
            coincidir(parentesis_abierto);
            PARAMETERS_OPC();
            coincidir(parentesis_cerrado);
            BLOCK();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void FUNCTIONS(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            FUNCTION();
            FUNCTIONS();
        }
    }
    
    void PARAMETERS_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            PARAMETERS();
        }
    }
    
    void PARAMETERS(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            coincidir(identificador);
            PARAMETERS_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void PARAMETERS_2(){
        if(hayErrores) return;

        if(preanalisis.equals(coma)){
            coincidir(coma);
            coincidir(identificador);
            PARAMETERS_2();
        }
    }
    
    void ARGUMENTS_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            ARGUMENTS();
        }
    }
    
    void ARGUMENTS(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            EXPRESSION();
            ARGUMENTS_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion);
        }
    }
    
    void ARGUMENTS_2(){
        if(hayErrores) return;

        if(preanalisis.equals(coma)){
            coincidir(coma);
            EXPRESSION();
            ARGUMENTS_2();
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

