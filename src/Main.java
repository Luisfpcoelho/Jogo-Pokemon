import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Jogo jogo = new Jogo();

        try {
            System.out.println("=== POKÉMON BATALHA MULTIPLAYER ===");

            // Inicialização do jogo
            ArrayList<Jogador> jogadores = jogo.iniciarJogo();

            System.out.println("\n=== A BATALHA COMEÇA! ===");
            System.out.println("Jogador 1: " + jogadores.get(0).getNome());
            System.out.println("Jogador 2: " + jogadores.get(1).getNome() + "\n");

            int turno = 1;
            while (!jogo.jogoAcabou()) {
                System.out.println("\n--- Turno " + turno + " ---");

                Jogador jogadorAtual = jogadores.get((turno - 1) % 2);
                System.out.println("Vez de: " + jogadorAtual.getNome());

                jogo.jogarTurno(jogadorAtual);

                // Pausa para melhor legibilidade
                if (!jogo.jogoAcabou()) {
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();
                }

                turno++;
            }

            // Resultado final
            System.out.println("\n=== FIM DA BATALHA ===");
            jogo.pontuacao();

        } catch (Exception e) {
            System.err.println("\nErro inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("\nObrigado por jogar!");
        }
    }
}