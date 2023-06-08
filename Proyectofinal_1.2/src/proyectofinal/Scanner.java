package proyectofinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("class", TipoToken.CLASS);
        palabrasReservadas.put("var", TipoToken.VAR);
        palabrasReservadas.put("fun", TipoToken.FUN);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("while", TipoToken.WHILE);
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("or", TipoToken.OR);
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("false", TipoToken.FALSE);
        palabrasReservadas.put("null", TipoToken.NULL);
        palabrasReservadas.put("this", TipoToken.THIS);
        palabrasReservadas.put("super", TipoToken.SUPER);
        
    }

    Scanner(String source){
        this.source = source + " ";
    }

    List<Token> scanTokens(){
        int estado = 0;
        char caracter = 0;
        char caracter_2 = 0;
        String lexema = "";
        int inicioLexema = 0;

        for(int i=0; i<source.length(); i++){
            if(i < source.length()-1) caracter_2 = source.charAt(i+1);
            caracter = source.charAt(i);
            switch (estado){
                case 0:
                    if(caracter == '=' && caracter_2 == '='){
                        System.out.print("AQUI");
                        tokens.add(new Token(TipoToken.IGUAL_QUE, "==", i + 2));
                    }
                    else if(caracter == '!' && caracter_2 == '='){
                        tokens.add(new Token(TipoToken.DIFERENTE, "!=", i + 2));
                    }
                    else if(caracter == '>' && caracter_2 == '='){
                        tokens.add(new Token(TipoToken.MAYOR_IGUAL, ">=", i + 2));
                    }
                    else if(caracter == '<' && caracter_2 == '='){
                        tokens.add(new Token(TipoToken.MENOR_IGUAL, "<=", i + 2));
                    }
                    else if(caracter == '/' && caracter_2 == '*'){
                        i++;
                        i++;
                        caracter = source.charAt(i);
                        if(i < source.length()-1) caracter_2 = source.charAt(i+1);
                        while(caracter != '*' && caracter_2 != '/'){
                            i++;
                            caracter = source.charAt(i);
                            if(i < source.length()-1) caracter_2 = source.charAt(i+1);
                        }
                        i++;
                    }
                    else if(caracter == '*'){
                        tokens.add(new Token(TipoToken.ASTERISCO, "*", i + 1));
                    }
                    else if(caracter == ','){
                        tokens.add(new Token(TipoToken.COMA, ",", i + 1));
                    }
                    else if(caracter == '.'){
                        tokens.add(new Token(TipoToken.PUNTO, ".", i + 1));
                    }
                    else if(caracter == '>'){
                        tokens.add(new Token(TipoToken.MAYOR, ">", i + 1));
                    }
                    else if(caracter == '<'){
                        tokens.add(new Token(TipoToken.MENOR, "<", i + 1));
                    }
                    else if(caracter == '-'){
                        tokens.add(new Token(TipoToken.GUION, "-", i + 1));
                    }
                    else if(caracter == '+'){
                        tokens.add(new Token(TipoToken.CRUZ, "+", i + 1));
                    }
                    else if(caracter == '/'){
                        tokens.add(new Token(TipoToken.DIAGONAL, "/", i + 1));
                    }
                    else if(caracter == '!'){
                        tokens.add(new Token(TipoToken.ADMIRACION, "!", i + 1));
                    }
                    else if(caracter == '='){
                        tokens.add(new Token(TipoToken.IGUAL, "=", i + 1));
                    }
                    else if(caracter == '('){
                        tokens.add(new Token(TipoToken.PARENTESIS_ABIERTO, "(", i + 1));
                    }
                    else if(caracter == ')'){
                        tokens.add(new Token(TipoToken.PARENTESIS_CERRADO, ")", i + 1));
                    }
                    else if(caracter == '{'){
                        tokens.add(new Token(TipoToken.LLAVE_ABIERTA, "{", i + 1));
                    }
                    else if(caracter == '}'){
                        tokens.add(new Token(TipoToken.LLAVE_CERRADA, "}", i + 1));
                    }
                    else if(caracter == ';'){
                        tokens.add(new Token(TipoToken.PUNTO_COMA, ";", i + 1));
                    }
                    else if(caracter == '"'){
                        estado = 3;
                        inicioLexema = i;
                    }
                    else if(Character.isDigit(caracter)){
                        estado = 2;
                        lexema = lexema + caracter;
                        inicioLexema = i;
                    }
                    else if(Character.isAlphabetic(caracter)){
                        estado = 1;
                        lexema = lexema + caracter;
                        inicioLexema = i;
                    }
                    break;

                case 1:
                    if(Character.isAlphabetic(caracter) || Character.isDigit(caracter) ){
                        lexema = lexema + caracter;
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if(tt == null){
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, lexema, inicioLexema + 1));
                        }
                        else{
                            tokens.add(new Token(tt, lexema, inicioLexema + 1));
                        }

                        estado = 0;
                        i--;
                        lexema = "";
                        inicioLexema = 0;
                    }
                    break;
                case 2:
                    if(Character.isDigit(caracter) || caracter == '.'){
                        lexema = lexema + caracter;
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if(tt == null){
                            tokens.add(new Token(TipoToken.NUMBER, lexema, inicioLexema + 1));
                        }
                        else{
                            tokens.add(new Token(tt, lexema, inicioLexema + 1));
                        }

                        estado = 0;
                        i--;
                        lexema = "";
                        inicioLexema = 0;
                    }
                    break;    
                case 3:
                    if(caracter != '"'){
                        lexema = lexema + caracter;
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if(tt == null){
                            tokens.add(new Token(TipoToken.STRING, lexema, inicioLexema + 1));
                        }
                        else{
                            tokens.add(new Token(tt, lexema, inicioLexema + 1));
                        }

                        estado = 0;
                        lexema = "";
                        inicioLexema = 0;
                    }
                    break;
            }
        }
        tokens.add(new Token(TipoToken.EOF, "", source.length()));

        return tokens;
    }

}

