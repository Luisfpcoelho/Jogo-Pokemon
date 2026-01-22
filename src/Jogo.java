import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Jogo implements JogoInterface {
    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public ArrayList<Jogador> iniciarJogo() {
        try {
            System.out.println("=== Batalha Pokémon ===");
            System.out.println("Cada jogador escolherá dois Pokémon distintos.");

            for (int i = 1; i <= 2; i++) {
                System.out.println("\nJogador " + i + ", informe seu nome:");
                String nome = scanner.nextLine();

                Pokemon[] pokemonsEscolhidos = new Pokemon[2];
                ArrayList<Integer> idsEscolhidos = new ArrayList<>();

                for (int j = 0; j < 2; j++) {
                    System.out.println("\nEscolha o Pokémon " + (j+1) + ":");
                    System.out.println("1 - Charizard (Fogo)");
                    System.out.println("2 - Blastoise (Água)");
                    System.out.println("3 - Venusaur (Planta)");
                    System.out.println("4 - Pikachu (Elétrico)");

                    int escolha;
                    do {
                        try {
                            escolha = scanner.nextInt();
                            if (idsEscolhidos.contains(escolha)) {
                                System.out.println("Este Pokémon já foi escolhido! Escolha outro.");
                                escolha = -1;
                            } else if (escolha < 1 || escolha > 4) {
                                System.out.println("Opção inválida! Escolha de 1 a 4.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Digite um número válido!");
                            scanner.nextLine();
                            escolha = -1;
                        }
                    } while (escolha < 1 || escolha > 4);

                    idsEscolhidos.add(escolha);

                    switch (escolha) {
                        case 1 -> pokemonsEscolhidos[j] = new Charizard();
                        case 2 -> pokemonsEscolhidos[j] = new Blastoise();
                        case 3 -> pokemonsEscolhidos[j] = new Venusaur();
                        case 4 -> pokemonsEscolhidos[j] = new Pikachu();
                    }
                }

                jogadores.add(new Jogador(nome, pokemonsEscolhidos));
                scanner.nextLine(); // Limpar buffer
            }

        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
            return iniciarJogo(); // Reiniciar em caso de erro
        }

        return jogadores;
    }

    @Override
    public void jogarTurno(Jogador jogador) {
        Jogador oponente = getOponente(jogador);

        System.out.println("\n=== Turno de " + jogador.getNome() + " ===");
        Pokemon ativo = jogador.getPokemons()[jogador.getPokemonAtivo()];
        Pokemon oponenteAtivo = oponente.getPokemons()[oponente.getPokemonAtivo()];

        System.out.println("Seu Pokémon: " + ativo.getNome() + " (Vida: " + ativo.getVida() + ")");
        System.out.println("Pokémon Oponente: " + oponenteAtivo.getNome() + " (Vida: " + oponenteAtivo.getVida() + ")");

        // Verificar se Pokémon está desmaiado
        if (ativo.getVida() <= 0) {
            System.out.println("Seu Pokémon está desmaiado! Você deve trocar de Pokémon.");
            trocarPokemonForcado(jogador);
            return;
        }

        // Menu principal
        int acao;
        do {
            System.out.println("\nEscolha sua ação:");
            System.out.println("1 - Atacar");
            System.out.println("2 - Usar Item");
            System.out.println("3 - Trocar Pokémon");
            System.out.println("4 - Megaevoluir");

            try {
                acao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido!");
                scanner.nextLine();
                acao = -1;
            }
        } while (acao < 1 || acao > 4);

        // Processar ação escolhida
        switch (acao) {
            case 1 -> atacar(jogador, oponente);
            case 2 -> usarItem(jogador);
            case 3 -> trocarPokemon(jogador);
            case 4 -> megaEvoluir(jogador);
        }

        // Aplicar efeitos de fim de turno
        aplicarEfeitosTurno(jogador, oponente);
    }

    private void atacar(Jogador jogador, Jogador oponente) {
        Pokemon ativo = jogador.getPokemons()[jogador.getPokemonAtivo()];
        Pokemon oponenteAtivo = oponente.getPokemons()[oponente.getPokemonAtivo()];

        // Verificar status
        if (ativo.isDormindo()) {
            System.out.println(ativo.getNome() + " está dormindo e não pode atacar!");
            return;
        }

        if (ativo.isParalisado() && JogoInterface.criarNumeroAleatorio() < 25) {
            System.out.println(ativo.getNome() + " está paralisado e não conseguiu atacar!");
            return;
        }

        ResultadoAtaque resultado = ativo.escolherAtaque();

        if (resultado.getDano() > 0) {
            // Verificar se oponente está protegido
            if ("bloqueio".equals(oponenteAtivo.getEfeitoAtivo())) {
                System.out.println(oponenteAtivo.getNome() + " bloqueou o ataque!");
                oponenteAtivo.removerEfeito();
                return;
            }

            // Calcular dano considerando defesa reduzida
            int defesaOponente = oponenteAtivo.isDefesaReduzida() ?
                    (int)(oponenteAtivo.getForcaDefesa() * 0.7) :
                    oponenteAtivo.getForcaDefesa();

            int danoFinal = Math.max(resultado.getDano() - defesaOponente, 0);
            oponenteAtivo.setVida(oponenteAtivo.getVida() - danoFinal);
            System.out.println(ativo.getNome() + " causou " + danoFinal + " de dano!");

            // Aplicar efeito secundário
            if (resultado.getEfeito() != null) {
                oponenteAtivo.aplicarEfeito(resultado.getEfeito(), resultado.getDuracao());
            }
        }

        // Verificar se Pokémon oponente desmaiou
        if (oponenteAtivo.getVida() <= 0) {
            oponente.setPokemonsRestantes(oponente.getPokemonsRestantes() - 1);
        }
    }

    private void usarItem(Jogador jogador) {
        if (jogador.getInventario().isEmpty()) {
            System.out.println("Você não tem itens!");
            return;
        }

        System.out.println("\nItens disponíveis:");
        for (int i = 0; i < jogador.getInventario().size(); i++) {
            System.out.println((i+1) + " - " + jogador.getInventario().get(i).getNome());
        }

        System.out.println("0 - Cancelar");

        int itemEscolhido;
        do {
            try {
                itemEscolhido = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido!");
                scanner.nextLine();
                itemEscolhido = -1;
            }
        } while (itemEscolhido < 0 || itemEscolhido > jogador.getInventario().size());

        if (itemEscolhido == 0) {
            System.out.println("Ação cancelada.");
            jogarTurno(jogador); // Volta ao menu principal
            return;
        }

        Item item = jogador.getInventario().get(itemEscolhido - 1);
        item.usar(jogador.getPokemons()[jogador.getPokemonAtivo()]);
        jogador.getInventario().remove(itemEscolhido - 1);
    }

    private void trocarPokemon(Jogador jogador) {
        if (jogador.getTrocasRestantes() <= 0) {
            System.out.println("Você não pode mais trocar de Pokémon nesta partida!");
            return;
        }

        if (jogador.getPokemonsRestantes() <= 1) {
            System.out.println("Você só tem um Pokémon disponível!");
            return;
        }

        int novoAtivo = (jogador.getPokemonAtivo() == 0) ? 1 : 0;
        if (jogador.getPokemons()[novoAtivo].getVida() <= 0) {
            System.out.println("Esse Pokémon está desmaiado!");
            return;
        }

        jogador.setPokemonAtivo(novoAtivo);
        jogador.reduzirTrocas();
        System.out.println("Você trocou para " + jogador.getPokemons()[novoAtivo].getNome() + "!");
    }

    private void trocarPokemonForcado(Jogador jogador) {
        if (jogador.getPokemonsRestantes() <= 1) {
            // Verificar se o outro Pokémon ainda tem vida
            int outro = (jogador.getPokemonAtivo() == 0) ? 1 : 0;
            if (jogador.getPokemons()[outro].getVida() <= 0) {
                System.out.println("Todos os seus Pokémon estão desmaiados!");
                jogador.setPokemonsRestantes(0); // Marca como derrotado
                return;
            }
        }

        int novoAtivo = (jogador.getPokemonAtivo() == 0) ? 1 : 0;
        if (jogador.getPokemons()[novoAtivo].getVida() <= 0) {
            System.out.println("Seu outro Pokémon também está desmaiado!");
            jogador.setPokemonsRestantes(0); // Marca como derrotado
            return;
        }

        jogador.setPokemonAtivo(novoAtivo);
        System.out.println("Você foi forçado a trocar para " + jogador.getPokemons()[novoAtivo].getNome() + "!");
    }


    private void megaEvoluir(Jogador jogador) {
        Pokemon ativo = jogador.getPokemons()[jogador.getPokemonAtivo()];

        if (jogador.isJaMegaEvoluiu()) {
            System.out.println("Você já usou sua megaevolução nesta partida!");
            return;
        }

        if (ativo.isMegaEvoluido()) {
            System.out.println(ativo.getNome() + " já está megaevoluído!");
            return;
        }

        ativo.megaEvoluir();
        jogador.setJaMegaEvoluiu(true);
    }

    private void aplicarEfeitosTurno(Jogador jogador, Jogador oponente) {
        jogador.getPokemons()[jogador.getPokemonAtivo()].aplicarEfeitosTurno();
        oponente.getPokemons()[oponente.getPokemonAtivo()].aplicarEfeitosTurno();
    }


    @Override
    public boolean jogoAcabou() {
        for (Jogador jogador : jogadores) {
            boolean todosDesmaiados = true;
            for (Pokemon pokemon : jogador.getPokemons()) {
                if (pokemon.getVida() > 0) {
                    todosDesmaiados = false;
                    break;
                }
            }
            if (todosDesmaiados) {
                return true;
            }
        }
        return false;
    }


    public void pontuacao() {
        Jogador vencedor = null;
        for (Jogador jogador : jogadores) {
            if (jogador.getPokemonsRestantes() > 0) {
                vencedor = jogador;
                break;
            }
        }

        System.out.println("\n=== FIM DE JOGO ===");
        System.out.println("Vencedor: " + vencedor.getNome() + "!");
        System.out.println("Pokémon restantes:");
        for (Pokemon pokemon : vencedor.getPokemons()) {
            if (pokemon.getVida() > 0) {
                System.out.println("- " + pokemon.getNome() + " (Vida: " + pokemon.getVida() + ")");
            }
        }
    }

    private Jogador getOponente(Jogador jogador) {
        for (Jogador j : jogadores) {
            if (!j.equals(jogador)) {
                return j;
            }
        }
        return null;
    }
}