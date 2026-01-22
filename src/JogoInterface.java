import java.util.ArrayList;

public interface JogoInterface {
    ArrayList<Jogador> iniciarJogo();
    void jogarTurno(Jogador jogador);
    boolean jogoAcabou();

    static int criarNumeroAleatorio() {
        return (int)(Math.random() * 100);
    }
}