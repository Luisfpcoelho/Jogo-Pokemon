import java.util.Scanner;

public class Venusaur extends Pokemon {
    private int usosSolarBeam = 5;
    private int usosRazorLeaf = 15;
    private int usosSleepPowder = 3;
    private int usosLeechSeed = 3;

    public Venusaur() {
        super("Venusaur", "Planta", 100, 55, 50);
    }

    @Override
    public ResultadoAtaque escolherAtaque() {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\nEscolha o ataque:");
            System.out.println("1 - Solar Beam (Dano: 80, Usos: " + usosSolarBeam + ")");
            System.out.println("2 - Razor Leaf (Dano: 50, Usos: " + usosRazorLeaf + ")");
            System.out.println("3 - Sleep Powder (Causa Sono, Usos: " + usosSleepPowder + ")");
            System.out.println("4 - Leech Seed (Drena Vida, Usos: " + usosLeechSeed + ")");

            escolha = scanner.nextInt();
        } while (escolha < 1 || escolha > 4);

        switch (escolha) {
            case 1:
                if (usosSolarBeam > 0) {
                    usosSolarBeam--;
                    System.out.println("Venusaur usou Solar Beam!");
                    return new ResultadoAtaque(
                            (int)(1 + 80 * (forcaAtaque / 100.0)),
                            null,
                            0
                    );
                }
                break;
            case 2:
                if (usosRazorLeaf > 0) {
                    usosRazorLeaf--;
                    System.out.println("Venusaur usou Razor Leaf!");
                    int dano = (int)(1 + 50 * (forcaAtaque / 100.0));
                    if (JogoInterface.criarNumeroAleatorio() < 40) {
                        System.out.println("Acerto crítico!");
                        dano *= 1.5;
                    }
                    return new ResultadoAtaque(dano, null, 0);
                }
                break;
            case 3:
                if (usosSleepPowder > 0) {
                    usosSleepPowder--;
                    System.out.println("Venusaur usou Sleep Powder!");
                    if (JogoInterface.criarNumeroAleatorio() < 75) {
                        return new ResultadoAtaque(0, "sono", 3);
                    }
                    System.out.println("Mas falhou!");
                    return new ResultadoAtaque(0, null, 0);
                }
                break;
            case 4:
                if (usosLeechSeed > 0) {
                    usosLeechSeed--;
                    System.out.println("Venusaur usou Leech Seed!");
                    return new ResultadoAtaque(0, "semente_suga", 5);
                }
                break;
        }

        System.out.println("Esse ataque não está mais disponível!");
        return escolherAtaque(); // Recursão para escolher novamente
    }
}