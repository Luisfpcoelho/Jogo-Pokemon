import java.util.Scanner;

public class Charizard extends Pokemon {
    private int usosFlamethrower = 5;
    private int usosWingAttack = 10;
    private int usosDragonClaw = 10;
    private int usosFireSpin = 5;

    public Charizard() {
        super("Charizard", "Fogo", 100, 50, 40);
    }

    @Override
    public ResultadoAtaque escolherAtaque() {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\nEscolha o ataque:");
            System.out.println("1 - Flamethrower (Dano: 70, Usos: " + usosFlamethrower + ")");
            System.out.println("2 - Wing Attack (Dano: 40, Usos: " + usosWingAttack + ")");
            System.out.println("3 - Dragon Claw (Dano: 50, Usos: " + usosDragonClaw + ")");
            System.out.println("4 - Fire Spin (Dano: 30 + Queimadura, Usos: " + usosFireSpin + ")");

            escolha = scanner.nextInt();
        } while (escolha < 1 || escolha > 4);

        switch (escolha) {
            case 1:
                if (usosFlamethrower > 0) {
                    usosFlamethrower--;
                    System.out.println("Charizard usou Flamethrower!");
                    return new ResultadoAtaque(
                            (int)(70 * (1 + forcaAtaque / 100.0)),
                            null,
                            0
                    );
                }
                break;
            case 2:
                if (usosWingAttack > 0) {
                    usosWingAttack--;
                    System.out.println("Charizard usou Wing Attack!");
                    return new ResultadoAtaque(
                            (int)(40 * (1 + forcaAtaque / 100.0)),
                            null,
                            0
                    );
                }
                break;
            case 3:
                if (usosDragonClaw > 0) {
                    usosDragonClaw--;
                    System.out.println("Charizard usou Dragon Claw!");
                    return new ResultadoAtaque(
                            (int)(50 * (1 + forcaAtaque / 100.0)),
                            null,
                            0
                    );
                }
                break;
            case 4:
                if (usosFireSpin > 0) {
                    usosFireSpin--;
                    System.out.println("Charizard usou Fire Spin!");
                    return new ResultadoAtaque(
                            (int)(30 * (1 + forcaAtaque / 100.0)),
                            "queimadura",
                            2
                    );
                }
                break;
        }

        System.out.println("Esse ataque não está mais disponível!");
        return escolherAtaque(); // Recursão para escolher novamente
    }
}