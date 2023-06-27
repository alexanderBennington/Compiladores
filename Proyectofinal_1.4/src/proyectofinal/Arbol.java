package proyectofinal;

public class Arbol {
    private final Nodo raiz;
    private final TablaSimbolos tb;
    

    public Arbol(Nodo raiz, TablaSimbolos tb){
        this.raiz = raiz;
        this.tb = tb;
    }

    public void recorrer(){
        
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case CRUZ:
                case GUION:
                case ASTERISCO:
                case DIAGONAL:
                    SolverAritmetico solver;
                    Object res;
                    //System.out.println(res);
                     break;

                case VAR:
                    String ident = n.getHijos().get(0).getValue().lexema;
                    Object valor;
                    
                    if(n.getHijos().get(1).getValue().tipo == TipoToken.CRUZ || n.getHijos().get(1).getValue().tipo == TipoToken.GUION || 
                        n.getHijos().get(1).getValue().tipo == TipoToken.ASTERISCO || n.getHijos().get(1).getValue().tipo == TipoToken.DIAGONAL){
                        
                        solver = new SolverAritmetico(n.getHijos().get(1),tb);
                        res = solver.resolver();
                        tb.asignar(ident, res);
                        
                    }else if(n.getHijos().get(1).getValue().tipo == TipoToken.NUMBER){
                        
                        valor = n.getHijos().get(1).getValue().literal;
                        tb.asignar(ident, valor);
                        
                    }else{
                        
                        valor = n.getHijos().get(1).getValue().lexema;
                        tb.asignar(ident, valor);
                        
                    }
                    
                    
                    
                    break;
                    
                case PRINT:
                    if(tb.existeIdentificador(n.getHijos().get(0).getValue().lexema)){
                        
                        System.out.println( tb.obtener(n.getHijos().get(0).getValue().lexema));
                        
                    }else if(n.getHijos().get(0).getValue().tipo == TipoToken.STRING){
                        
                        System.out.println( n.getHijos().get(0).getValue().lexema);
                        
                    }else if(n.getHijos().get(0).getValue().tipo == TipoToken.NUMBER){
                        
                        System.out.println( n.getHijos().get(0).getValue().literal);
                        
                    }else if(n.getHijos().get(0).getValue().tipo == TipoToken.CRUZ || n.getHijos().get(0).getValue().tipo == TipoToken.GUION || 
                            n.getHijos().get(0).getValue().tipo == TipoToken.ASTERISCO || n.getHijos().get(0).getValue().tipo == TipoToken.DIAGONAL){
                        
                        solver = new SolverAritmetico(n.getHijos().get(0),tb);
                        res = solver.resolver();
                        if(res != null) System.out.println(res);
                    }
                    break;
                case IF:
                    break;

            }
        }
    }

}

