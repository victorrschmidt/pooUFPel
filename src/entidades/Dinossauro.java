package entidades;

public class Dinossauro extends SerVivo {
    private int danoPorAtaque;
    public static final char padraoRepresentacaoCompsognato  = 'C';
    public static final int padraoVidaTotalCompsognato       = 1;
    public static final int padraoDanoPorAtaqueCompsognato   = 1;
    public static final char padraoRepresentacaoTroodonte    = 'T';
    public static final int padraoVidaTotalTroodonte         = 2;
    public static final int padraoDanoPorAtaqueTroodonte     = 1;
    public static final char padraoRepresentacaoVelociraptor = 'V';
    public static final int padraoVidaTotalVelociraptor      = 2;
    public static final int padraoDanoPorAtaqueVelociraptor  = 1;
    public static final char padraoRepresentacaoTrex         = 'R';
    public static final int padraoVidaTotalTrex              = 3;
    public static final int padraoDanoPorAtaqueTrex          = 2;
    
    public Dinossauro(char representacaoVisual, int vidaTotal, int danoPorAtaque) {
        super(representacaoVisual, vidaTotal);
        this.danoPorAtaque = danoPorAtaque;
    }
    
    public Dinossauro(char representacaoVisual, int vidaTotal, int vidaAtual, int danoPorAtaque) {
        super(representacaoVisual, vidaTotal, vidaAtual);
        this.danoPorAtaque = danoPorAtaque;
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public int getDanoPorAtaque() {
        return this.danoPorAtaque;
    }
    
    public void setDanoPorAtaque(int danoPorAtaque) {
        this.danoPorAtaque = danoPorAtaque;
    }
    
    /* -------------------------- Metodos publicos -------------------------- */
    
    public void atacar(Jogador jogador) {
        jogador.receberDano(this.danoPorAtaque);
    }
}
