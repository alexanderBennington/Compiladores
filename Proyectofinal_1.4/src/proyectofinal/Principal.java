package proyectofinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.io.File;
import java.io.FileReader;


public class Principal {

    static boolean existenErrores = false;

    public static void main(String[] args) throws IOException {
        TablaSimbolos tb = new TablaSimbolos();
        
        if(args.length == 0){
            ejecutarPrompt(tb);
        }
        else if(args.length == 1){
            ejecutarArchivo(args[0], tb);
        }
        
        
    }
    
    private static void ejecutarArchivo(String pathFile, TablaSimbolos tb) {
         File archivo = new File(pathFile);
         if(archivo.exists()){
                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                    StringBuilder contenido = new StringBuilder();
                    String linea2;

                    while ((linea2 = br.readLine()) != null) {
                        contenido.append(linea2);
                    }
                    
                    String tc = contenido.toString();
                    System.out.println(tc);
                    ejecutar(tc, tb);
                    existenErrores = false;
                } catch (IOException e) {
                    System.out.println("El archivo no existe.");
                }
            }
    }

    private static void ejecutarPrompt(TablaSimbolos tb) throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        
        for(;;){
            System.out.print(">>> ");
            String linea = reader.readLine();
            
            if(linea == null) break; // Presionar Ctrl + D
            else{
                ejecutar(linea, tb);
                existenErrores = false;
            }
        }
    }

    private static void ejecutar(String source, TablaSimbolos tb){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        /*
        for(Token token : tokens){
            System.out.println(tokens);
        }
        */
        /*
        Parser parser = new Parser(tokens);
        parser.parse();
        */
        
        GeneradorPostfija gpf = new GeneradorPostfija(tokens);
        List<Token> postfija = gpf.convertir();

        for(Token token : postfija){
            System.out.println(postfija);
        }

        GeneradorAST gast = new GeneradorAST(postfija, tb);
        Arbol programa = gast.generarAST();
        programa.recorrer();
    }

    /*
    El método error se puede usar desde las distintas clases
    para reportar los errores:
    Interprete.error(....);
     */
    static void error(int linea, String mensaje){
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje){
        System.err.println(
                "[linea " + linea + "] Error " + donde + ": " + mensaje
        );
        existenErrores = true;
    }

}
