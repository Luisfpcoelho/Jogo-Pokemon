import java.util.ArrayList;
import java.util.Random;

public class Jogador {
    private String nome;
    private Pokemon[] pokemons = new Pokemon[2];
    private ArrayList<Item> inventario = new ArrayList<>();
    private int pokemonsRestantes = 2;
    private int trocasRestantes = 1;
    private int pokemonAtivo = 0;
    private boolean jaMegaEvoluiu = false;

    public Jogador(String nome, Pokemon[] pokemons) {
        this.nome = nome;
        this.pokemons = pokemons;
        gerarItensAleatorios();
    }

    public void gerarItensAleatorios() {
        String[] itensDisponiveis = {"Poção", "Antídoto", "Despertador", "Desparalisador"};
        Random rand = new Random();

        for (int i = 0; i < 2; i++) {
            inventario.add(new Item(itensDisponiveis[rand.nextInt(itensDisponiveis.length)]));
        }
    }

    public void reduzirTrocas() {
        trocasRestantes--;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public Pokemon[] getPokemons() { return pokemons; }
    public ArrayList<Item> getInventario() { return inventario; }
    public int getPokemonsRestantes() { return pokemonsRestantes; }
    public void setPokemonsRestantes(int pokemonsRestantes) { this.pokemonsRestantes = pokemonsRestantes; }
    public int getTrocasRestantes() { return trocasRestantes; }
    public int getPokemonAtivo() { return pokemonAtivo; }
    public void setPokemonAtivo(int pokemonAtivo) { this.pokemonAtivo = pokemonAtivo; }
    public boolean isJaMegaEvoluiu() { return jaMegaEvoluiu; }
    public void setJaMegaEvoluiu(boolean jaMegaEvoluiu) { this.jaMegaEvoluiu = jaMegaEvoluiu; }
}