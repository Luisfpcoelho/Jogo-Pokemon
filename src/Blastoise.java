import java.util.Scanner;

public class Blastoise extends Pokemon {
    private int usosHydroPump = 5;
    private int usosBubbleBeam = 15;
    private int usosSkullBash = 5;
    private int usosProtect = 3;

    public Blastoise() {
        super("Blastoise", "Água", 100, 45, 55);
    }

    @Override
    public ResultadoAtaque escolherAtaque() {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\nEscolha o ataque:");
            System.out.println("1 - Hydro Pump (Dano: 100, Usos: " + usosHydroPump + ")");
            System.out.println("2 - Bubble Beam (Dano: 70, Usos: " + usosBubbleBeam + ")");
            System.out.println("3 - Skull Bash (Dano: 80 + Aumenta Defesa, Usos: " + usosSkullBash + ")");
            System.out.println("4 - Protect (Bloqueia próximo ataque, Usos: " + usosProtect + ")");

            escolha = scanner.nextInt();
        } while (escolha < 1 || escolha > 4);

        switch (escolha) {
            case 1:
                if (usosHydroPump > 0) {
                    usosHydroPump--;
                    System.out.println("Blastoise usou Hydro Pump!");
                    return new ResultadoAtaque(
                            (int)(80 * (1 + forcaAtaque / 100.0)),
                            null,
                            0
                    );
                }
                break;
            case 2:
                if (usosBubbleBeam > 0) {
                    usosBubbleBeam--;
                    System.out.println("Blastoise usou Bubble Beam!");
                    if (JogoInterface.criarNumeroAleatorio() < 30) {
                        return new ResultadoAtaque(
                                (int)(1 + 40 * (forcaAtaque / 100.0)),
                                "reducao_defesa",
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
            case 3:
                if (usosSkullBash > 0) {
                    usosSkullBash--;
                    System.out.println("Blastoise usou Skull Bash!");
                    this.forcaDefesa += 15;
                    return new ResultadoAtaque(
                            (int)(80 * (1 + forcaAtaque / 100.0)),
                            null,
                            0
                    );
                }
                break;
            case 4:
                if (usosProtect > 0) {
                    usosProtect--;
                    System.out.println("Blastoise usou Protect!");
                    return new ResultadoAtaque(
                            0,
                            "bloqueio",
                            1
                    );
                }
                break;
        }

        System.out.println("Esse ataque não está mais disponível!");
        return escolherAtaque(); // Recursão para escolher novamente
    }
}