package utilidades;
import entidades.Entidade;
import entidades.Jogador;
import entidades.Dinossauro;
import entidades.CaixaDeSuprimentos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Jogo {
    private int dificuldade;
    private Jogador jogador;
    private Sorteador sorteador;
    private Mapa mapa;
    private Logger logger;
    private ArquivoSave save;
    
    public Jogo() {
        this.sorteador = new Sorteador();
        this.logger = new Logger();
        try {
            this.save = new ArquivoSave();
        } catch (FileNotFoundException excecao) {
            this.logger.mostrarMensagem("ERRO: " + excecao.getMessage());
            System.exit(1);
        }
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public int getDificuldade() {
        return this.dificuldade;
    }
    
    public Jogador getJogador() {
        return this.jogador;
    }
    
    public Sorteador getSorteador() {
        return this.sorteador;
    }
    
    public Mapa getMapa() {
        return this.mapa;
    }
    
    public Logger getLogger() {
        return this.logger;
    }
    
    public ArquivoSave getSave() {
        return this.save;
    }
    
    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }
    
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
    
    public void setSorteador(Sorteador sorteador) {
        this.sorteador = sorteador;
    }
    
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }
    
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    public void setSave(ArquivoSave save) {
        this.save = save;
    }
    
    /* -------------------------- Metodos privados -------------------------- */
    
    private void gerarMapa() {
        final ArrayList<Coordenada> listaDeCoordenadas = new ArrayList<>();
        
        for (int y = 0; y < Mapa.padraoMedida; y++) {
            for (int x = 0; x < Mapa.padraoMedida; x++) {
                listaDeCoordenadas.add(new Coordenada(x, y));
            }
        }
        
        Collections.shuffle(listaDeCoordenadas);
        
        int numeroParedes             = this.sorteador.rolarDado(Mapa.padraoMedida * Mapa.padraoMedida / 5);
        final int numeroCompsognatos  = 2;
        final int numeroTroodontes    = 5;
        final int numeroVelociraptors = 2;
        final int numeroTrexes        = 1;
       
        int ponteiroListaDeCoordenadas = 0;
        
        while (numeroParedes > 0) {
            Coordenada coordenadaAtual = listaDeCoordenadas.get(ponteiroListaDeCoordenadas++);
            this.mapa.adicionarCaracter(coordenadaAtual, Entidade.padraoRepresentacaoParede);
            numeroParedes--;
        }
        
        final char[] representacoesDinossauros = new char[]{
            Dinossauro.padraoRepresentacaoCompsognato,
            Dinossauro.padraoRepresentacaoTroodonte,
            Dinossauro.padraoRepresentacaoVelociraptor,
            Dinossauro.padraoRepresentacaoTrex
        };
        final int[] vidaTotalDinossauros = new int[]{
            Dinossauro.padraoVidaTotalCompsognato,
            Dinossauro.padraoVidaTotalTroodonte,
            Dinossauro.padraoVidaTotalVelociraptor,
            Dinossauro.padraoVidaTotalTrex
        };
        final int[] danoPorAtaqueDinossauros = new int[]{
            Dinossauro.padraoDanoPorAtaqueCompsognato,
            Dinossauro.padraoDanoPorAtaqueTroodonte,
            Dinossauro.padraoDanoPorAtaqueVelociraptor,
            Dinossauro.padraoDanoPorAtaqueTrex
        };
        final int[] numeroDinossauros = new int[] {
            numeroCompsognatos,
            numeroTroodontes,
            numeroVelociraptors,
            numeroTrexes
        };
        
        for (int i = 0; i < 4; i++) {
            for (; numeroDinossauros[i] > 0; numeroDinossauros[i]--) {
                Coordenada coordenadaAtual = listaDeCoordenadas.get(ponteiroListaDeCoordenadas++);
                Dinossauro dinossauro = new Dinossauro(
                    representacoesDinossauros[i],
                    vidaTotalDinossauros[i],
                    danoPorAtaqueDinossauros[i]
                );
                this.mapa.adicionarEntidade(coordenadaAtual, dinossauro);
            }
        }
        
        final char[] conteudoCaixas = new char[]{'C', 'B', 'M', 'D', 'D'};
        
        for (int i = 0; i < conteudoCaixas.length; i++) {
            Coordenada coordenadaAtual = listaDeCoordenadas.get(ponteiroListaDeCoordenadas++);
            this.mapa.adicionarEntidade(coordenadaAtual, new CaixaDeSuprimentos(conteudoCaixas[i]));
        }
        
        Coordenada posicaoJogador = listaDeCoordenadas.get(ponteiroListaDeCoordenadas++);
        
        this.mapa.setPosicaoJogador(posicaoJogador);
        this.mapa.adicionarCaracter(posicaoJogador, Jogador.padraoRepresentacao);
        
        while (ponteiroListaDeCoordenadas < Mapa.padraoMedida * Mapa.padraoMedida) {
            Coordenada coordenadaAtual = listaDeCoordenadas.get(ponteiroListaDeCoordenadas++);
            this.mapa.adicionarCaracter(coordenadaAtual, Entidade.padraoRepresentacaoEspacoVazio);
        }
    }
    
    private void mostrarStatusJogador() {
        this.logger.mostrarMensagem(String.format(
            "HP: %d/%d\nPercepcao: %d\nPossui Kit Medico: %s\nPossui Bastao: %s\nDardos: %d",
            this.jogador.getVidaAtual(),
            this.jogador.getVidaTotal(),
            this.jogador.getPercepcao(),
            this.jogador.getPossuiKitMedico() ? "Sim" : "Nao",
            this.jogador.getPossuiBastao() ? "Sim" : "Nao",
            this.jogador.getMunicaoDardos()
        ));
    }
    
    private void mostrarTelaTurnoPadrao(String mensagemTurnoAtual) {
        this.logger.mostrarEspaco();
        this.logger.mostrarMensagem(this.mapa.retornarMapa());
        this.logger.mostrarMensagem(Logger.mensagemLinhaDivisoria);
        
        if (!mensagemTurnoAtual.isEmpty()) {
            this.logger.mostrarMensagem(mensagemTurnoAtual);
            this.logger.mostrarMensagem(Logger.mensagemLinhaDivisoria);
        }
        
        this.mostrarStatusJogador();
        this.logger.mostrarMensagem(Logger.mensagemLinhaDivisoria);
    }
    
    private char lerTurnoPadrao(String mensagemTurnoAtual) {
        this.mostrarTelaTurnoPadrao(mensagemTurnoAtual);
        return this.logger.lerOpcaoTurnoPadrao();
    }
    
    private String processarCura() {
        String mensagem;
        
        if (this.jogador.getPossuiKitMedico() && this.jogador.getVidaTotal() > this.jogador.getVidaAtual()) {
            this.jogador.usarCura();
            mensagem = "Utilizou o kit medico!";
        }
        else if (this.jogador.getPossuiKitMedico()) {
            mensagem = "Vida cheia, nao eh necessario usar o kit medico.";
        }
        else {
            mensagem = "Eh necessario um kit medico para usar a cura.";
        }
        
        return mensagem;
    }
    
    private String processarCaixa() {
        final Entidade entidadeAlvo = this.mapa.retornarEntidadeAlvo();        
        final char conteudoCaixa = ((CaixaDeSuprimentos) (entidadeAlvo)).getConteudo();
        String mensagem = "";
        
        switch (conteudoCaixa) {
            case CaixaDeSuprimentos.padraoConteudoCompsognato -> {
                mensagem += "Encontrou um compsognato surpresa!\n";
                final int resultadoDado = this.sorteador.rolarDado(4);
                
                if (this.jogador.getPercepcao() < resultadoDado) {
                    this.jogador.receberDano(Dinossauro.padraoDanoPorAtaqueCompsognato);
                    mensagem += "Recebeu dano do compsognato surpresa.";
                    
                    if (!this.jogador.estaVivo()) {
                        mensagem += "\nVoce morreu.";
                    }
                }
                else {
                    mensagem += "Escapou do compsognato surpresa!";
                }
            }
            case CaixaDeSuprimentos.padraoConteudoKitMedico -> {
                mensagem += "Encontrou um kit medico!";
                this.jogador.setPossuiKitMedico(true);
            }
            case CaixaDeSuprimentos.padraoConteudoBastao -> {
                mensagem += "Encontrou um bastao!";
                this.jogador.setPossuiBastao(true);
            }
            case CaixaDeSuprimentos.padraoConteudoMunicaoDardos -> {
                mensagem += "Encontrou municao de dardo!";
                this.jogador.setMunicaoDardos(this.jogador.getMunicaoDardos() + 1);
            }
        }
        
        return mensagem;
    }
    
    private String processarCombate(char tipoInimigo) {
        Dinossauro inimigo = (Dinossauro) this.mapa.retornarEntidadeAlvo();
        String nomeInimigo = "";
        int contagemTurno = 1;
        
        switch (tipoInimigo) {
            case Dinossauro.padraoRepresentacaoCompsognato -> nomeInimigo = "Compsognato";
            case Dinossauro.padraoRepresentacaoTroodonte -> nomeInimigo = "Troodonte";
            case Dinossauro.padraoRepresentacaoVelociraptor -> nomeInimigo = "Velociraptor";
            case Dinossauro.padraoRepresentacaoTrex -> nomeInimigo = "T-Rex";
        }
        
        this.logger.mostrarEspaco();
        this.logger.mostrarMensagem(String.format("Encontrou um %s!", nomeInimigo));
        this.logger.mostrarMensagem(Logger.mensagemLinhaDivisoria);
        
        while (true) {
            this.logger.mostrarMensagem("Jogador");
            this.mostrarStatusJogador();
            this.logger.mostrarMensagem(Logger.mensagemLinhaDivisoria);
            this.logger.mostrarMensagem(String.format(
                "%s\nHP: %d/%d\nDano por Ataque: %d",
                nomeInimigo,
                inimigo.getVidaAtual(),
                inimigo.getVidaTotal(),
                inimigo.getDanoPorAtaque()
            ));
            this.logger.mostrarMensagem(Logger.mensagemLinhaDivisoria);
            
            this.logger.mostrarMensagem(String.format("Turno %d", contagemTurno++));
            char opcaoCombate = this.logger.lerOpcaoTurnoCombate();
            this.logger.mostrarEspaco();
            
            switch (opcaoCombate) {
                case 'a' -> {
                    if (tipoInimigo == Dinossauro.padraoRepresentacaoTrex && !this.jogador.getPossuiBastao()) {
                        this.logger.mostrarMensagem("Nao eh possivel atacar o T-Rex sem um bastao.");
                        break;
                    }
                    
                    final int resultadoDado = this.sorteador.rolarDado(6);
                    
                    if (!this.jogador.getPossuiBastao() && resultadoDado < 6) {
                        this.logger.mostrarMensagem("O Troodonte so recebe ataques de maos livres se estes forem criticos.");
                        break;
                    }         
                                     
                    if (resultadoDado == 6 || (this.jogador.getPossuiBastao() && resultadoDado == 5)) {
                        this.jogador.atacarComArma(inimigo, true);
                        this.logger.mostrarMensagem(String.format("Acerto critico em %s!", nomeInimigo));
                    }
                    else if (resultadoDado > 2 || (this.jogador.getPossuiBastao() && resultadoDado > 1)) {                      
                        this.jogador.atacarComArma(inimigo, false);
                        this.logger.mostrarMensagem(String.format("Acerto normal em %s!", nomeInimigo));
                    }
                    else {
                        this.logger.mostrarMensagem(String.format("%s desviou do seu ataque.", nomeInimigo));
                    }              
                }
                case 'd' -> {
                    if (this.jogador.getMunicaoDardos() == 0) {
                        this.logger.mostrarMensagem("Nao possui municao de dardos.");
                        break;
                    }
                    
                    if (tipoInimigo == Dinossauro.padraoRepresentacaoVelociraptor) {
                        this.logger.mostrarMensagem("O velociraptor desviou do dardo.");
                        this.jogador.setMunicaoDardos(this.jogador.getMunicaoDardos() - 1);
                        break;
                    }
                    
                    this.jogador.atacarComDardos(inimigo);
                    this.logger.mostrarMensagem(String.format("Acerto com dardos em %s!", nomeInimigo));
                }
                case 'c' -> {
                    if (!this.jogador.getPossuiKitMedico()) {
                        this.logger.mostrarMensagem("Eh necessario um kit medico para se curar.");
                        break;
                    }
                    
                    if (this.jogador.getVidaAtual() == this.jogador.getVidaTotal()) {
                        this.logger.mostrarMensagem("Vida cheia, nao eh necessario se curar.");
                        break;
                    }
                    
                    this.jogador.usarCura();
                    this.logger.mostrarMensagem("Kit medico utilizado!");
                }
                case 'f' -> {
                    final int resultadoDado = this.sorteador.rolarDado(5);
                
                    if (this.jogador.getPercepcao() < resultadoDado) {
                        this.logger.mostrarMensagem("Nao conseguiu escapar.");
                    }
                    else {
                        return String.format("Escapou do %s!", nomeInimigo);
                    }
                }
                case 'x' -> {
                    this.logger.mostrarMensagem(Logger.mensagemSairDoJogo);
                    System.exit(0);
                }
            }
            
            if (inimigo.getVidaAtual() == 0) {
                this.mapa.movimentarJogador();
                return String.format("Voce derrotou o %s!", nomeInimigo);
            }
            
            final int resultadoDadoInimigo = this.sorteador.rolarDado(4);
            
            if (this.jogador.getPercepcao() < resultadoDadoInimigo) {
                this.logger.mostrarMensagem(String.format("%s acertou o ataque.", nomeInimigo));
                inimigo.atacar(jogador);
                
                if (!this.jogador.estaVivo()) {
                    return String.format("Voce foi morto pelo %s.", nomeInimigo);
                }
            }
            else {
                this.logger.mostrarMensagem(String.format("Voce desviou do ataque do %s!", nomeInimigo));
            }
        }
    }
    
    private String processarMovimento() {
        final char caracterAlvo = this.mapa.retornarCaracterAlvo();
        String mensagem = "";
        
        switch (caracterAlvo) {
            case Entidade.padraoRepresentacaoEspacoVazio -> {
                this.mapa.movimentarJogador();
            }
            case CaixaDeSuprimentos.padraoRepresentacao -> {
                mensagem = this.processarCaixa();
                this.mapa.movimentarJogador();
            }
            default -> {
                mensagem = this.processarCombate(caracterAlvo);
            }
        }
        
        return mensagem;
    }
    
    private void salvarJogo() throws IOException {
        this.save.setPossuiConteudo(true);
        this.save.setDificuldade(this.dificuldade);
        this.save.setVidaJogador(this.jogador.getVidaAtual());
        this.save.setKitMedicoJogador(this.jogador.getPossuiKitMedico());
        this.save.setBastaoJogador(this.jogador.getPossuiBastao());
        this.save.setDardosJogador(this.jogador.getMunicaoDardos());
        this.save.setMapa(this.mapa.getVersaoVisual());
        
        final char[][] mapaVisualAtual = this.mapa.getVersaoVisual();
        final Entidade[][] mapaEntidadeAtual = this.mapa.getVersaoEntidade();
        
        final ArrayList<Integer> vidaInimigosLista = new ArrayList<>();
        final ArrayList<Character> conteudoCaixasLista = new ArrayList<>();
        final Set<Character> simbolosDinossauros = new HashSet<>(Set.of(
            Dinossauro.padraoRepresentacaoCompsognato,
            Dinossauro.padraoRepresentacaoTroodonte,
            Dinossauro.padraoRepresentacaoVelociraptor,
            Dinossauro.padraoRepresentacaoTrex
        ));
        
        for (int y = 0; y < Mapa.padraoMedida; y++) {
            for (int x = 0; x < Mapa.padraoMedida; x++) {
                if (simbolosDinossauros.contains(mapaVisualAtual[y][x])) {
                    vidaInimigosLista.add(((Dinossauro) (mapaEntidadeAtual[y][x])).getVidaAtual());
                }
                else if (mapaVisualAtual[y][x] == CaixaDeSuprimentos.padraoRepresentacao) {
                    conteudoCaixasLista.add(((CaixaDeSuprimentos) (mapaEntidadeAtual[y][x])).getConteudo());
                }
            }
        }
        
        final int[] vidaInimigos = new int[vidaInimigosLista.size()];
        final char[] conteudoCaixas = new char[conteudoCaixasLista.size()];
        
        for (int i = 0; i < vidaInimigos.length; i++) {
            vidaInimigos[i] = vidaInimigosLista.get(i);
        }
        
        for (int i = 0; i < conteudoCaixas.length; i++) {
            conteudoCaixas[i] = conteudoCaixasLista.get(i);
        }
        
        this.save.setVidaInimigos(vidaInimigos);
        this.save.setConteudoCaixas(conteudoCaixas);
        
        this.save.escreverDados();
    }
    
    private void criarNovoJogo() {
        this.dificuldade = this.logger.lerOpcaoDificuldade();
        this.jogador = new Jogador(4 - this.dificuldade);
        this.mapa = new Mapa();   
        this.gerarMapa();
    }
    
    private void criarJogoSalvo() throws IOException {
        this.save.resgatarDados();
        
        this.dificuldade               = this.save.getDificuldade();
        final int vidaJogador          = this.save.getVidaJogador();
        final boolean kitMedicoJogador = this.save.getKitMedicoJogador();
        final boolean bastaoJogador    = this.save.getBastaoJogador();
        final int dardosJogador        = this.save.getDardosJogador();
        final int[] vidaInimigos       = this.save.getVidaInimigos();
        final char[] conteudoCaixas    = this.save.getConteudoCaixas();
        final char[][] mapaVisual      = this.save.getMapa();
        
        this.jogador = new Jogador(4 - this.dificuldade, vidaJogador, kitMedicoJogador, bastaoJogador, dardosJogador);
        
        int ponteiroVidaInimigos = 0;
        int ponteiroConteudoCaixas = 0;
        final Entidade[][] mapaEntidade = new Entidade[Mapa.padraoMedida][Mapa.padraoMedida];
        Coordenada posicaoJogador = null;

        for (int y = 0; y < Mapa.padraoMedida; y++) {
            for (int x = 0; x < Mapa.padraoMedida; x++) {
                switch (mapaVisual[y][x]) {
                    case Jogador.padraoRepresentacao -> {
                        mapaEntidade[y][x] = this.jogador;
                        posicaoJogador = new Coordenada(x, y);
                    }
                    case CaixaDeSuprimentos.padraoRepresentacao -> {
                        final char conteudoCaixa = conteudoCaixas[ponteiroConteudoCaixas++];
                        mapaEntidade[y][x] = new CaixaDeSuprimentos(conteudoCaixa);
                    }
                    case Dinossauro.padraoRepresentacaoCompsognato -> {
                        final int vidaTotal = Dinossauro.padraoVidaTotalCompsognato;
                        final int vidaAtual = vidaInimigos[ponteiroVidaInimigos++];
                        final int danoPorAtaque = Dinossauro.padraoDanoPorAtaqueCompsognato;
                        mapaEntidade[y][x] = new Dinossauro(Dinossauro.padraoRepresentacaoCompsognato, vidaTotal, vidaAtual, danoPorAtaque);
                    }
                    case Dinossauro.padraoRepresentacaoTroodonte -> {
                        final int vidaTotal = Dinossauro.padraoVidaTotalTroodonte;
                        final int vidaAtual = vidaInimigos[ponteiroVidaInimigos++];
                        final int danoPorAtaque = Dinossauro.padraoDanoPorAtaqueTroodonte;
                        mapaEntidade[y][x] = new Dinossauro(Dinossauro.padraoRepresentacaoTroodonte, vidaTotal, vidaAtual, danoPorAtaque);
                    }
                    case Dinossauro.padraoRepresentacaoVelociraptor -> {
                        final int vidaTotal = Dinossauro.padraoVidaTotalVelociraptor;
                        final int vidaAtual = vidaInimigos[ponteiroVidaInimigos++];
                        final int danoPorAtaque = Dinossauro.padraoDanoPorAtaqueVelociraptor;
                        mapaEntidade[y][x] = new Dinossauro(Dinossauro.padraoRepresentacaoVelociraptor, vidaTotal, vidaAtual, danoPorAtaque);
                    }
                    case Dinossauro.padraoRepresentacaoTrex -> {
                        final int vidaTotal = Dinossauro.padraoVidaTotalTrex;
                        final int vidaAtual = vidaInimigos[ponteiroVidaInimigos++];
                        final int danoPorAtaque = Dinossauro.padraoDanoPorAtaqueTrex;
                        mapaEntidade[y][x] = new Dinossauro(Dinossauro.padraoRepresentacaoTrex, vidaTotal, vidaAtual, danoPorAtaque);
                    }
                }
            }
        }
        
        this.mapa = new Mapa(mapaVisual, mapaEntidade, posicaoJogador);
    }

    /* -------------------------- Metodos publicos -------------------------- */
    
    public void rodar() throws IOException {
        boolean existeJogoSalvo = this.save.getPossuiConteudo();
        char opcaoMenuPrincipal = this.logger.lerOpcaoMenuPrincipal(existeJogoSalvo);
        
        if ((existeJogoSalvo && opcaoMenuPrincipal == '3') || (!existeJogoSalvo && opcaoMenuPrincipal == '2')) {
            this.logger.mostrarMensagem(Logger.mensagemSairDoJogo);
            return;
        }
        
        if (opcaoMenuPrincipal == '1') {
            this.criarNovoJogo();
        }
        else {
            this.criarJogoSalvo();
        }
        
        String mensagemTurnoAtual = "";
        
        while (true) {            
            char opcao = this.lerTurnoPadrao(mensagemTurnoAtual);
            boolean continuarCiclo = true;
            boolean fezMovimento = false;
            boolean fezMovimentoInvalido = false;
            mensagemTurnoAtual = "";

            switch (opcao) {
                case 'c' -> {
                    mensagemTurnoAtual = this.processarCura();
                }
                case 'z' -> {
                    this.mapa.setModoDebug(!this.mapa.getModoDebug());
                }
                case 'j' -> {
                    this.salvarJogo();
                }
                case 'x' -> {
                    this.logger.mostrarMensagem(Logger.mensagemSairDoJogo);
                    System.exit(0);
                }
                default -> {
                    fezMovimento = true;
                    fezMovimentoInvalido = !this.mapa.movimentoEhValido(opcao);
                }
            }
            
            if (fezMovimentoInvalido) {
                mensagemTurnoAtual = "Movimento invalido.";
                continue;
            }
            
            if (fezMovimento) {
                mensagemTurnoAtual = this.processarMovimento();
                continuarCiclo = this.jogador.estaVivo();
            }
            
            if (!continuarCiclo) {
                this.mostrarTelaTurnoPadrao(mensagemTurnoAtual);
                existeJogoSalvo = this.save.getPossuiConteudo();
                opcaoMenuPrincipal = this.logger.lerOpcaoMenuPrincipal(existeJogoSalvo);

                if ((existeJogoSalvo && opcaoMenuPrincipal == '3') || (!existeJogoSalvo && opcaoMenuPrincipal == '2')) {
                    this.logger.mostrarMensagem(Logger.mensagemSairDoJogo);
                    System.exit(0);
                }

                if (opcaoMenuPrincipal == '1') {
                    this.criarNovoJogo();
                }
                else {
                    this.criarJogoSalvo();
                }
                
                mensagemTurnoAtual = "";
            }
        }
    }
}