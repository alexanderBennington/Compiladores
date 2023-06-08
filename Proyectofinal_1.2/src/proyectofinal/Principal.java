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
        ejecutarPrompt();
    }
    
    

    private static void ejecutarPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        
        for(;;){
            System.out.print(">>> ");
            String linea = reader.readLine();
            
            File archivo = new File(linea);
            
            if(archivo.exists()){
                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                    StringBuilder contenido = new StringBuilder();
                    String linea2;

                    while ((linea2 = br.readLine()) != null) {
                        contenido.append(linea2);
                    }
                    
                    String tc = contenido.toString();
                    tc = tc.replaceAll("\n", "").replaceAll("\r", "");
                    System.out.println(tc);
                    ejecutar(tc);
                    existenErrores = false;
                } catch (IOException e) {
                    System.out.println("El archivo no existe.");
                }
            }
            else if(linea == null) break; // Presionar Ctrl + D
            else{
                ejecutar(linea);
                existenErrores = false;
            }
        }
    }

    private static void ejecutar(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        
        /*for(Token token : tokens){
            System.out.println(tokens);
        }*/

        Parser parser = new Parser(tokens);
        parser.parse();
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
