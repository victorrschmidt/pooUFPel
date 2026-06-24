package utilidades;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Logger {
    private Scanner scanner;
    public static final String mensagemSairDoJogo     = "Saindo do jogo...";
    public static final String mensagemLinhaDivisoria = "-------------------------------------------";
    
    public Logger() {
        this.scanner = new Scanner(System.in);
    }
    
    /* ------------------------- Getters e setters ------------------------- */
    
    public Scanner getScanner() {
        return this.scanner;
    }
    
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /* -------------------------- Metodos privados -------------------------- */
    
    private char lerOpcao(String mensagem, Set<Character> opcoes) {
        char opcao;
        
        while (true) {
            System.out.print(String.format("%s\n\nDigite a opcao: ", mensagem));
            String leitura = this.scanner.nextLine();
            
            if (leitura.length() == 1 && opcoes.contains(leitura.charAt(0))) {
                opcao = leitura.charAt(0);
                break;
            }

            System.out.println("Erro. Digite um valor valido.");
        }
        
        return opcao;
    }
    
    /* -------------------------- Metodos publicos -------------------------- */
    
    public void mostrarEspaco() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public char lerOpcaoMenuPrincipal(boolean existeJogoSalvo) {
        final Set<Character> opcoesMenu = new HashSet<>(Set.of('1', '2'));
        String mensagem = "Menu Principal\n1 - Novo jogo\n";
        
        if (existeJogoSalvo) {
            opcoesMenu.add('3');
            mensagem += "2 - Continuar jogo\n3 - Sair";
        }
        else {
            mensagem += "2 - Sair";
        }
        
        return this.lerOpcao(mensagem, opcoesMenu);
    }
    
    public int lerOpcaoDificuldade() {
        final Set<Character> opcoesMenu = new HashSet<>(Set.of('1', '2', '3'));
        final String mensagem = "Escolha a dificuldade do jogo\n1 - Facil\n2 - Normal\n3 - Dificil";
        
        return this.lerOpcao(mensagem, opcoesMenu) - '0';
    }
    
    public char lerOpcaoTurnoPadrao() {
        final Set<Character> opcoesMenu = new HashSet<>(Set.of('w', 'a', 's', 'd', 'c', 'z', 'j', 'x'));
        final String mensagem = "Seu turno\nw - Mover para cima\na - Mover para esquerda\ns - Mover para baixo\nd - Mover para direita\nc - Usar cura\nz - Ativar/desativar modo debug\nj - Salvar progresso atual\nx - Sair do jogo";
        
        return this.lerOpcao(mensagem, opcoesMenu);
    }
    
    public char lerOpcaoTurnoCombate() {
        final Set<Character> opcoesMenu = new HashSet<>(Set.of('a', 'd', 'c', 'f', 'x'));
        final String mensagem = "Seu turno\na - Atacar\nd - Atacar com dardos\nc - Curar\nf - Tentar fugir\nx - Sair do jogo";
        
        return this.lerOpcao(mensagem, opcoesMenu);
    }
}
