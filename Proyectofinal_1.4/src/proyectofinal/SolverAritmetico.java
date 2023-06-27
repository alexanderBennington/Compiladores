package proyectofinal;

public class SolverAritmetico {

    private final Nodo nodo;
    private final TablaSimbolos tb;

    public SolverAritmetico(Nodo nodo, TablaSimbolos tb) {
        this.nodo = nodo;
        this.tb = tb;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.NUMBER){
                return n.getValue().literal;
            }else if(n.getValue().tipo == TipoToken.STRING){
                return n.getValue().lexema;
            }
            else if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                if(tb.existeIdentificador(n.getValue().lexema)){
                    return tb.obtener(n.getValue().lexema);
                }else{
                    System.out.println("No existe identificador: " + n.getValue().lexema);
                    return null;
                }
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);
        
        //System.out.println(resultadoIzquierdo);
        //System.out.println(resultadoDerecho);
        
        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch (n.getValue().tipo){
                case CRUZ:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case GUION:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case ASTERISCO:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case DIAGONAL:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            if (n.getValue().tipo == TipoToken.CRUZ){
                return ((String)resultadoIzquierdo + (String) resultadoDerecho);
            }
        }
        
        else{
            if(resultadoIzquierdo != null && resultadoDerecho != null){
                System.out.println("Error por diferencia de tipo de datos");
            }
        }

        return null;
    }
}
