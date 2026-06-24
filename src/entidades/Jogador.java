package entidades;

public class Jogador extends SerVivo implements Curavel {
    private int percepcao;
    private boolean possuiKitMedico;
    private boolean possuiBastao;
    private int municaoDardos;
    public static final char padraoRepresentacao = 'J';
    private static final int padraoVidaTotal      = 5;
    private static final int padraoDanoCritico    = 2;
    private static final int padraoDanoNormal     = 1;
    private static final int padraoCuraKitMedico  = 1;

    public Jogador(int percepcao) {
        super(Jogador.padraoRepresentacao, Jogador.padraoVidaTotal);
        this.percepcao = percepcao;
        this.possuiKitMedico = false;
        this.possuiBastao = false;
        this.municaoDardos = 0;
    }
    
    public Jogador(int percepcao, int vidaAtual, boolean possuiKitMedico, boolean possuiBastao, int municaoDardos) {
        super(Jogador.padraoRepresentacao, Jogador.padraoVidaTotal, vidaAtual);
        this.percepcao = percepcao;
        this.possuiKitMedico = possuiKitMedico;
        this.possuiBastao = possuiBastao;
        this.municaoDardos = municaoDardos;
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public int getPercepcao() {
        return this.percepcao;
    }
    
    public boolean getPossuiKitMedico() {
        return this.possuiKitMedico;
    }
    
    public boolean getPossuiBastao() {
        return this.possuiBastao;
    }
    
    public int getMunicaoDardos() {
        return this.municaoDardos;
    }
    
    public void setPercepcao(int percepcao) {
        this.percepcao = percepcao;
    }
    
    public void setPossuiKitMedico(boolean possuiKitMedico) {
        this.possuiKitMedico = possuiKitMedico;
    }
    
    public void setPossuiBastao(boolean possuiBastao) {
        this.possuiBastao = possuiBastao;
    }
    
    public void setMunicaoDardos(int municaoDardos) {
        this.municaoDardos = municaoDardos;
    }
    
    /* -------------------------- Metodos publicos -------------------------- */
    
    @Override
    public void usarCura() {
        this.setVidaAtual(Math.min(this.getVidaAtual() + Jogador.padraoCuraKitMedico, Jogador.padraoVidaTotal));
        this.possuiKitMedico = false;
    }
    
    public void atacarComDardos(Dinossauro dinossauro) {
        dinossauro.receberDano(Jogador.padraoDanoCritico);
        this.municaoDardos--;
    }
    
    public void atacarComArma(Dinossauro dinossauro, boolean ataqueCritico) {
        dinossauro.receberDano(ataqueCritico ? Jogador.padraoDanoCritico : Jogador.padraoDanoNormal);
    }
}
