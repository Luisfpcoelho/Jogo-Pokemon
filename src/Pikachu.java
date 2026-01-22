import java.util.Scanner;

public class Pikachu extends Pokemon {
    private int usosThunderbolt = 10;
    private int usosQuickAttack = 20;
    private int usosIronTail = 10;
    private int usosThunderWave = 5;

    public Pikachu() {
        super("Pikachu", "Elétrico", 80, 60, 40);
    }

    @Override
    public ResultadoAtaque escolherAtaque() {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\nEscolha o ataque:");
            System.out.println("1 - Thunderbolt (Dano: 70, Usos: " + usosThunderbolt + ")");
            System.out.println("2 - Quick Attack (Dano: 30, Usos: " + usosQuickAttack + ")");
            System.out.println("3 - Iron Tail (Dano: 50, Usos: " + usosIronTail + ")");
            System.out.println("4 - Thunder Wave (Paralisa, Usos: " + usosThunderWave + ")");

            escolha = scanner.nextInt();
        } while (escolha < 1 || escolha > 4);

        switch (escolha) {
            case 1:
                if (usosThunderbolt > 0) {
                    usosThunderbolt--;
                    System.out.println("Pikachu usou Thunderbolt!");
                    if (JogoInterface.criarNumeroAleatorio() < 10) {
                        return new ResultadoAtaque(
                                (int)(70 * (1 + forcaAtaque / 100.0)),
                                "paralisia",
                                2
                        );
                    }
                    return new ResultadoAtaque(
                            (int)(70 * (1 + forcaAtaque / 100.0)),
                            null,
                            0
                    );
                }
                break;
            case 2:
                if (usosQuickAttack > 0) {
                    usosQuickAttack--;
                    System.out.println("Pikachu usou Quick Attack!");
                    return new ResultadoAtaque(
                            (int)(30 * (1 + forcaAtaque / 100.0)),
                            null,
                            0
                    );
                }
                break;
            case 3:
                if (usosIronTail > 0) {
                    usosIronTail--;
                    System.out.println("Pikachu usou Iron Tail!");
                    if (JogoInterface.criarNumeroAleatorio() < 30) {
                        return new ResultadoAtaque(
                                (int)(50 * (1 + forcaAtaque / 100.0)),
                                "reducao_defesa",
                                2
                        );
                    }
                    return new ResultadoAtaque(
                            (int)(50 * (1 + forcaAtaque / 100.0)),
                            null,
                            0
                    );
                }
                break;
            case 4:
                if (usosThunderWave > 0) {
                    usosThunderWave--;
                    System.out.println("Pikachu usou Thunder Wave!");
                    if (JogoInterface.criarNumeroAleatorio() < 90) {
                        return new ResultadoAtaque(0, "paralisia", 3);
                    }
                    System.out.println("Mas falhou!");
                    return new ResultadoAtaque(0, null, 0);
                }
                break;
        }

        System.out.println("Esse ataque não está mais disponível!");
        return escolherAtaque(); // Recursão para escolher novamente
    }
}