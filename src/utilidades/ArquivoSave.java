package utilidades;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ArquivoSave {
    private boolean possuiConteudo;
    private int dificuldade;
    private int vidaJogador;
    private boolean kitMedicoJogador;
    private boolean bastaoJogador;
    private int dardosJogador;
    private int[] vidaInimigos;
    private char[] conteudoCaixas;
    private char[][] mapa;
    private static final String nomeArquivo = "save.bin";
    
    public ArquivoSave() throws FileNotFoundException {
        final File arquivo = new File(ArquivoSave.nomeArquivo);
        
        if (!arquivo.exists()) {
            throw new FileNotFoundException("O arquivo save.bin foi removido. Reinstale o programa.");
        }
        
        this.possuiConteudo = arquivo.length() > 0;
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public boolean getPossuiConteudo() {
        return this.possuiConteudo;
    }
    
    public int getDificuldade() {
        return this.dificuldade;
    }
    
    public int getVidaJogador() {
        return this.vidaJogador;
    }
    
    public boolean getKitMedicoJogador() {
        return this.kitMedicoJogador;
    }
    
    public boolean getBastaoJogador() {
        return this.bastaoJogador;
    }
    
    public int getDardosJogador() {
        return this.dardosJogador;
    }
    
    public int[] getVidaInimigos() {
        return this.vidaInimigos;
    }
    
    public char[] getConteudoCaixas() {
        return this.conteudoCaixas;
    }
    
    public char[][] getMapa() {
        return this.mapa;
    }

    public void setPossuiConteudo(boolean possuiConteudo) {
        this.possuiConteudo = possuiConteudo;
    }
    
    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }
    
    public void setVidaJogador(int vidaJogador) {
        this.vidaJogador = vidaJogador;
    }
    
    public void setKitMedicoJogador(boolean kitMedicoJogador) {
        this.kitMedicoJogador = kitMedicoJogador;
    }
    
    public void setBastaoJogador(boolean bastaoJogador) {
        this.bastaoJogador = bastaoJogador;
    }
    
    public void setDardosJogador(int dardosJogador) {
        this.dardosJogador = dardosJogador;
    }
    
    public void setVidaInimigos(int[] vidaInimigos) {
        this.vidaInimigos = vidaInimigos;
    }
    
    public void setConteudoCaixas(char[] conteudoCaixas) {
        this.conteudoCaixas = conteudoCaixas;
    }
    
    public void setMapa(char[][] mapa) {
        this.mapa = mapa;
    }
    
    /* -------------------------- Metodos publicos -------------------------- */
    
    public void resgatarDados() throws IOException {
        final DataInputStream arquivo = new DataInputStream(new FileInputStream(ArquivoSave.nomeArquivo));

        this.dificuldade      = arquivo.readInt();
        this.vidaJogador      = arquivo.readInt();
        this.kitMedicoJogador = arquivo.readBoolean();
        this.bastaoJogador    = arquivo.readBoolean();
        this.dardosJogador    = arquivo.readInt();
        
        final int quantidadeInimigos = arquivo.readInt();
        this.vidaInimigos = new int[quantidadeInimigos];
        
        for (int i = 0; i < quantidadeInimigos; i++) {
            this.vidaInimigos[i] = arquivo.readInt();
        }
        
        final int quantidadeCaixas = arquivo.readInt();
        this.conteudoCaixas = new char[quantidadeCaixas];
        
        for (int i = 0; i < quantidadeCaixas; i++) {
            this.conteudoCaixas[i] = arquivo.readChar();
        }
        
        this.mapa = new char[Mapa.padraoMedida][Mapa.padraoMedida];
        
        for (int y = 0; y < Mapa.padraoMedida; y++) {
            for (int x = 0; x < Mapa.padraoMedida; x++) {
                this.mapa[y][x] = arquivo.readChar();
            }
        }

        arquivo.close();
    }
    
    public void escreverDados() throws IOException {
        final DataOutputStream arquivo = new DataOutputStream(new FileOutputStream(ArquivoSave.nomeArquivo));
        
        arquivo.writeInt(this.dificuldade);
        arquivo.writeInt(this.vidaJogador);
        arquivo.writeBoolean(this.kitMedicoJogador);
        arquivo.writeBoolean(this.bastaoJogador);
        arquivo.writeInt(this.dardosJogador);
        arquivo.writeInt(this.vidaInimigos.length);
        
        for (int i = 0; i < this.vidaInimigos.length; i++) {
            arquivo.writeInt(this.vidaInimigos[i]);
        }
        
        arquivo.writeInt(this.conteudoCaixas.length);
        
        for (int i = 0; i < this.conteudoCaixas.length; i++) {
            arquivo.writeChar(this.conteudoCaixas[i]);
        }
        
        for (int y = 0; y < Mapa.padraoMedida; y++) {
            for (int x = 0; x < Mapa.padraoMedida; x++) {
                arquivo.writeChar(this.mapa[y][x]);
            }
        }
        
        arquivo.close();
    }
}
