package entidades;

public abstract class SerVivo extends Entidade {
    private int vidaTotal;
    private int vidaAtual;
    
    public SerVivo(char representacaoVisual, int vidaTotal) {
        super(representacaoVisual);
        this.vidaTotal = vidaTotal;
        this.vidaAtual = vidaTotal;
    }
    
    public SerVivo(char representacaoVisual, int vidaTotal, int vidaAtual) {
        super(representacaoVisual);
        this.vidaTotal = vidaTotal;
        this.vidaAtual = vidaAtual;
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public int getVidaTotal() {
        return this.vidaTotal;
    }
    
    public int getVidaAtual() {
        return this.vidaAtual;
    }
    
    public void setVidaTotal(int vidaTotal) {
        this.vidaTotal = vidaTotal;
    }
    
    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }
    
    /* -------------------------- Metodos publicos -------------------------- */
    
    public void receberDano(int dano) {
        this.vidaAtual = Math.max(this.vidaAtual - dano, 0);
    }
    
    public boolean estaVivo() {
        return this.vidaAtual > 0;
    }
}
