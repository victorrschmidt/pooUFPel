package entidades;

public class CaixaDeSuprimentos extends Entidade {
    private char conteudo;
    public static final char padraoRepresentacao         = '+';
    public static final char padraoConteudoCompsognato   = 'C';
    public static final char padraoConteudoBastao        = 'B';
    public static final char padraoConteudoKitMedico     = 'M';
    public static final char padraoConteudoMunicaoDardos = 'D';
    
    public CaixaDeSuprimentos(char conteudo) {
        super(CaixaDeSuprimentos.padraoRepresentacao);
        this.conteudo = conteudo;
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public char getConteudo() {
        return this.conteudo;
    }
    
    public void setConteudo(char conteudo) {
        this.conteudo = conteudo;
    }
}
