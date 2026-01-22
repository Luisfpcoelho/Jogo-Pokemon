public class Item {
    private String nome;

    public Item(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void usar(Pokemon pokemon) {
        switch (nome) {
            case "Poção":
                int cura = (int)(pokemon.vidaMaxima() * 0.3);
                pokemon.setVida(pokemon.getVida() + cura);
                System.out.println(pokemon.getNome() + " recuperou " + cura + " de vida!");
                break;
            case "Antídoto":
                if (pokemon.isEnvenenado()) {
                    pokemon.setEnvenenado(false);
                    System.out.println(pokemon.getNome() + " foi curado do veneno!");
                } else {
                    System.out.println(pokemon.getNome() + " não está envenenado!");
                }
                break;
            case "Despertador":
                if (pokemon.isDormindo()) {
                    pokemon.setDormindo(false);
                    System.out.println(pokemon.getNome() + " acordou!");
                } else {
                    System.out.println(pokemon.getNome() + " não está dormindo!");
                }
                break;
            case "Desparalisador":
                if (pokemon.isParalisado()) {
                    pokemon.setParalisado(false);
                    System.out.println(pokemon.getNome() + " foi desparalisado!");
                } else {
                    System.out.println(pokemon.getNome() + " não está paralisado!");
                }
                break;
            default:
                System.out.println("Item desconhecido!");
        }
    }
}