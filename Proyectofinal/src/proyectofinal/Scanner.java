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
        palabrasReservadas.put("", TipoToken.FOR);
        palabrasReservadas.put("", TipoToken.IF);
        palabrasReservadas.put("", TipoToken.PRINT);
        palabrasReservadas.put("", TipoToken.RETURN);
        palabrasReservadas.put("", TipoToken.WHILE);
        palabrasReservadas.put("", TipoToken.ELSE);
        palabrasReservadas.put("", TipoToken.OR);
        palabrasReservadas.put("", TipoToken.AND);
        palabrasReservadas.put("", TipoToken.TRUE);
        palabrasReservadas.put("", TipoToken.FALSE);
        palabrasReservadas.put("", TipoToken.NULL);
        palabrasReservadas.put("", TipoToken.THIS);
        palabrasReservadas.put("", TipoToken.NUMBER);
        palabrasReservadas.put("", TipoToken.STRING);
        palabrasReservadas.put("", TipoToken.SUPER);
        
    }

    Scanner(String source){
        this.source = source + " ";
    }

    List<Token> scanTokens(){
        int estado = 0;
        char caracter = 0;
        String lexema = "";
        int inicioLexema = 0;

        for(int i=0; i<source.length(); i++){
            caracter = source.charAt(i);

            switch (estado){
                case 0:
                    if(caracter == '*'){
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
            }
        }
        tokens.add(new Token(TipoToken.EOF, "", source.length()));

        return tokens;
    }

}

