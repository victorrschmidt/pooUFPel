package entidades;

public abstract class Entidade {
    private char representacaoVisual;
    public static final char padraoRepresentacaoParede      = '#';
    public static final char padraoRepresentacaoEspacoVazio = ' ';
    
    public Entidade(char representacaoVisual) {
        this.representacaoVisual = representacaoVisual;
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public char getRepresentacaoVisual() {
        return this.representacaoVisual;
    }
    
    public void setRepresentacaoVisual(char representacaoVisual) {
        this.representacaoVisual = representacaoVisual;
    }
}
