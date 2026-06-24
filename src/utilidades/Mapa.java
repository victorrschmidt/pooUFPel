package utilidades;
import entidades.Entidade;
import entidades.Jogador;

public class Mapa {
    private boolean modoDebug;
    private char[][] versaoVisual;
    private Entidade[][] versaoEntidade;
    private Coordenada posicaoJogador;
    private Coordenada posicaoNova;
    public static final char padraoRepresentacaoNevoa = '*';
    public static final int padraoMedida = 20;
    
    public Mapa() {
        this.modoDebug = false;
        this.versaoVisual = new char[Mapa.padraoMedida][Mapa.padraoMedida];
        this.versaoEntidade = new Entidade[Mapa.padraoMedida][Mapa.padraoMedida];
    }
    
    public Mapa(char[][] versaoVisual, Entidade[][] versaoEntidade, Coordenada posicaoJogador) {
        this.modoDebug = false;
        this.versaoVisual = versaoVisual;
        this.versaoEntidade = versaoEntidade;
        this.posicaoJogador = posicaoJogador;
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public boolean getModoDebug() {
        return this.modoDebug;
    }
    
    public char[][] getVersaoVisual() {
        return this.versaoVisual;
    }
    
    public Entidade[][] getVersaoEntidade() {
        return this.versaoEntidade;
    }
    
    public Coordenada getPosicaoJogador() {
        return this.posicaoJogador;
    }
    
    public Coordenada getPosicaoNova() {
        return this.posicaoNova;
    }
    
    public void setModoDebug(boolean modoDebug) {
        this.modoDebug = modoDebug;
    }
    
    public void setVersaoVisual(char[][] versaoVisual) {
        this.versaoVisual = versaoVisual;
    }
    
    public void setVersaoEntidade(Entidade[][] versaoEntidade) {
        this.versaoEntidade = versaoEntidade;
    }
    
    public void setPosicaoJogador(Coordenada posicaoJogador) {
        this.posicaoJogador = posicaoJogador;
    }
    
    public void setPosicaoNova(Coordenada posicaoNova) {
        this.posicaoNova = posicaoNova;
    }
    
    /* -------------------------- Metodos privados -------------------------- */
    
    private String construirMapa(char[][] versaoMapa) {
        String conteudo = "";
        
        for (int y = 0; y < Mapa.padraoMedida; y++) {
            conteudo += "| ";
            for (int x = 0; x < Mapa.padraoMedida; x++) {
                conteudo += versaoMapa[y][x] + " ";
            }
            conteudo += "|\n";
        }
        
        return conteudo;
    }
    
    /* -------------------------- Metodos publicos -------------------------- */
    
    public boolean movimentoEhValido(char movimento) {
        int posicaoX = this.posicaoJogador.getX();
        int posicaoY = this.posicaoJogador.getY();
        
        switch (movimento) {
            case 'w' -> {
                posicaoY--;
            }
            case 'a' -> {
                posicaoX--;
            }
            case 's' -> {
                posicaoY++;
            }
            case 'd' -> {
                posicaoX++;
            }
        }
        
        if (Math.min(posicaoX, posicaoY) < 0 || Math.max(posicaoX, posicaoY) == Mapa.padraoMedida) {
            return false;
        }
        
        final Coordenada possivelPosicaoNova = new Coordenada(posicaoX, posicaoY);
        this.posicaoNova = possivelPosicaoNova;
        
        return this.versaoVisual[posicaoY][posicaoX] != Entidade.padraoRepresentacaoParede;
    }
    
    public void movimentarJogador() {
        final int jogadorX = this.posicaoJogador.getX();
        final int jogadorY = this.posicaoJogador.getY();
        final int novaX = this.posicaoNova.getX();
        final int novaY = this.posicaoNova.getY();
        
        this.versaoVisual[novaY][novaX] = this.versaoVisual[jogadorY][jogadorX];
        this.versaoEntidade[novaY][novaX] = this.versaoEntidade[jogadorY][jogadorX];
        this.versaoVisual[jogadorY][jogadorX] = Entidade.padraoRepresentacaoEspacoVazio;
        this.versaoEntidade[jogadorY][jogadorX] = null;
        
        this.setPosicaoJogador(this.posicaoNova);
    }
    
    public char retornarCaracterAlvo() {
        final int x = this.posicaoNova.getX();
        final int y = this.posicaoNova.getY();
        return this.versaoVisual[y][x];
    }
    
    public Entidade retornarEntidadeAlvo() {
        final int x = this.posicaoNova.getX();
        final int y = this.posicaoNova.getY();        
        return this.versaoEntidade[y][x];
    }
    
    public void adicionarCaracter(Coordenada coordenada, char caracter) {
        final int x = coordenada.getX();
        final int y = coordenada.getY();
        this.versaoVisual[y][x] = caracter;
    }
    
    public void adicionarEntidade(Coordenada coordenada, Entidade entidade) {
        final int x = coordenada.getX();
        final int y = coordenada.getY();
        this.versaoEntidade[y][x] = entidade;
        this.versaoVisual[y][x] = entidade.getRepresentacaoVisual();
    }
    
    public void removerEntidade(Coordenada coordenada) {
        final int x = coordenada.getX();
        final int y = coordenada.getY();
        this.versaoEntidade[y][x] = null;
        this.versaoVisual[y][x] = Entidade.padraoRepresentacaoEspacoVazio;
    }
    
    public String retornarMapa() {
        if (this.modoDebug) {
            return this.construirMapa(this.versaoVisual);
        }
        
        final char[][] mapa = new char[Mapa.padraoMedida][Mapa.padraoMedida];
        
        for (int y = 0; y < Mapa.padraoMedida; y++) {
            for (int x = 0; x < Mapa.padraoMedida; x++) {
                mapa[y][x] = Mapa.padraoRepresentacaoNevoa;
            }
        }
        
        final int posicaoX = this.posicaoJogador.getX();
        final int posicaoY = this.posicaoJogador.getY();
        mapa[posicaoY][posicaoX] = Jogador.padraoRepresentacao;
        
        for (int y = posicaoY - 1; y >= 0; y--) {
            if (this.versaoVisual[y][posicaoX] == Entidade.padraoRepresentacaoEspacoVazio) {
                mapa[y][posicaoX] = Entidade.padraoRepresentacaoEspacoVazio;
            }
            else {
                mapa[y][posicaoX] = this.versaoVisual[y][posicaoX];
                break;
            }
        }
        
        for (int y = posicaoY + 1; y < Mapa.padraoMedida; y++) {
            if (this.versaoVisual[y][posicaoX] == Entidade.padraoRepresentacaoEspacoVazio) {
                mapa[y][posicaoX] = Entidade.padraoRepresentacaoEspacoVazio;
            }
            else {
                mapa[y][posicaoX] = this.versaoVisual[y][posicaoX];
                break;
            }
        }
        
        for (int x = posicaoX - 1; x >= 0; x--) {
            if (this.versaoVisual[posicaoY][x] == Entidade.padraoRepresentacaoEspacoVazio) {
                mapa[posicaoY][x] = Entidade.padraoRepresentacaoEspacoVazio;
            }
            else {
                mapa[posicaoY][x] = this.versaoVisual[posicaoY][x];
                break;
            }
        }
        
        for (int x = posicaoX + 1; x < Mapa.padraoMedida; x++) {
            if (this.versaoVisual[posicaoY][x] == Entidade.padraoRepresentacaoEspacoVazio) {
                mapa[posicaoY][x] = Entidade.padraoRepresentacaoEspacoVazio;
            }
            else {
                mapa[posicaoY][x] = this.versaoVisual[posicaoY][x];
                break;
            }
        }    
        
        return this.construirMapa(mapa);
    }
}
