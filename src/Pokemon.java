public abstract class Pokemon {
    protected String nome;
    protected String tipo;
    protected Integer vida;
    protected Integer forcaAtaque;
    protected Integer forcaDefesa;
    protected boolean paralisado = false;
    protected boolean envenenado = false;
    protected boolean dormindo = false;
    protected boolean queimando = false;
    protected int turnosDormindo = 0;
    protected int turnosParalisado = 0;
    protected int turnosEnvenenado = 0;
    protected int turnosQueimando = 0;
    protected boolean megaEvoluido = false;
    protected int turnosMegaEvoluido = 0;
    protected boolean defesaReduzida = false;
    protected int turnosDefesaReduzida = 0;
    protected String efeitoAtivo = null;
    protected int turnosEfeito = 0;


    public Pokemon(String nome, String tipo, Integer vida, Integer forcaAtaque, Integer forcaDefesa) {
        this.nome = nome;
        this.tipo = tipo;
        this.vida = vida;
        this.forcaAtaque = forcaAtaque;
        this.forcaDefesa = forcaDefesa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setForcaAtaque(Integer forcaAtaque) {
        this.forcaAtaque = forcaAtaque;
    }

    public void setForcaDefesa(Integer forcaDefesa) {
        this.forcaDefesa = forcaDefesa;
    }

    public int getTurnosDormindo() {
        return turnosDormindo;
    }

    public void setTurnosDormindo(int turnosDormindo) {
        this.turnosDormindo = turnosDormindo;
    }

    public int getTurnosParalisado() {
        return turnosParalisado;
    }

    public void setTurnosParalisado(int turnosParalisado) {
        this.turnosParalisado = turnosParalisado;
    }

    public int getTurnosEnvenenado() {
        return turnosEnvenenado;
    }

    public void setTurnosEnvenenado(int turnosEnvenenado) {
        this.turnosEnvenenado = turnosEnvenenado;
    }

    public int getTurnosQueimando() {
        return turnosQueimando;
    }

    public void setTurnosQueimando(int turnosQueimando) {
        this.turnosQueimando = turnosQueimando;
    }

    public void setMegaEvoluido(boolean megaEvoluido) {
        this.megaEvoluido = megaEvoluido;
    }

    public int getTurnosMegaEvoluido() {
        return turnosMegaEvoluido;
    }

    public void setTurnosMegaEvoluido(int turnosMegaEvoluido) {
        this.turnosMegaEvoluido = turnosMegaEvoluido;
    }

    public int getTurnosDefesaReduzida() {
        return turnosDefesaReduzida;
    }

    public void setTurnosDefesaReduzida(int turnosDefesaReduzida) {
        this.turnosDefesaReduzida = turnosDefesaReduzida;
    }

    public String getEfeitoAtivo() {
        return efeitoAtivo;
    }

    public void setEfeitoAtivo(String efeitoAtivo) {
        this.efeitoAtivo = efeitoAtivo;
    }

    public int getTurnosEfeito() {
        return turnosEfeito;
    }

    public void setTurnosEfeito(int turnosEfeito) {
        this.turnosEfeito = turnosEfeito;
    }

    public abstract ResultadoAtaque escolherAtaque();

    public void megaEvoluir() {
        if (!megaEvoluido) {
            megaEvoluido = true;
            turnosMegaEvoluido = 3;
            forcaAtaque = (int)(forcaAtaque * 2.5);
            forcaDefesa = (int)(forcaDefesa * 2.5);
            System.out.println(nome + " megaevoluiu! Atributos aumentados em 50%!");
        }
    }

    public void verificarMegaEvolucao() {
        if (megaEvoluido) {
            turnosMegaEvoluido--;
            if (turnosMegaEvoluido <= 0) {
                megaEvoluido = false;
                forcaAtaque = (int)(forcaAtaque / 2.5);
                forcaDefesa = (int)(forcaDefesa / 2.5);
                System.out.println(nome + " voltou ao normal!");
            }
        }
    }

    public void aplicarEfeitosTurno() {
        verificarMegaEvolucao();

        // Veneno
        if (envenenado) {
            vida -= 5;
            System.out.println(nome + " sofreu 5 de dano por envenenamento!");
            turnosEnvenenado--;
            if (turnosEnvenenado <= 0) {
                envenenado = false;
                System.out.println(nome + " foi curado do veneno!");
            }
        }

        // Queimadura
        if (queimando) {
            vida -= 8;
            System.out.println(nome + " sofreu 8 de dano por queimadura!");
            turnosQueimando--;
            if (turnosQueimando <= 0) {
                queimando = false;
                System.out.println(nome + " se recuperou da queimadura!");
            }
        }

        // Sono
        if (dormindo) {
            System.out.println(nome + " está dormindo...");
            turnosDormindo--;
            if (turnosDormindo <= 0) {
                dormindo = false;
                System.out.println(nome + " acordou!");
            }
        }

        // Defesa reduzida
        if (defesaReduzida) {
            turnosDefesaReduzida--;
            if (turnosDefesaReduzida <= 0) {
                defesaReduzida = false;
                System.out.println(nome + " recuperou sua defesa normal!");
            }
        }

        // Efeito ativo (como Leech Seed)
        if (efeitoAtivo != null) {
            switch (efeitoAtivo) {
                case "semente_suga":
                    int dano = (int)(vidaMaxima() * 0.1);
                    vida -= dano;
                    System.out.println(nome + " perdeu " + dano + " de vida por Leech Seed!");
                    break;
            }

            turnosEfeito--;
            if (turnosEfeito <= 0) {
                System.out.println("O efeito " + efeitoAtivo + " acabou!");
                efeitoAtivo = null;
            }
        }

        // Fotossíntese (Venusaur)
        if (this instanceof Venusaur && vida > 0 && !dormindo) {
            int cura = (int)(vidaMaxima() * 0.1);
            vida = Math.min(vida + cura, vidaMaxima());
            System.out.println(nome + " recuperou " + cura + " de vida com fotossíntese!");
        }


        // Garantir que vida não fique negativa
        vida = Math.max(0, vida);
    }

    public void aplicarEfeito(String efeito, int duracao) {
        this.efeitoAtivo = efeito;
        this.turnosEfeito = duracao;
        System.out.println(nome + " foi afetado por " + efeito + " por " + duracao + " turnos!");
    }

    public int vidaMaxima() {
        return 100;
    }

    public void removerEfeito() {
        this.efeitoAtivo = null;      // Remove o efeito ativo
        this.turnosEfeito = 0;        // Zera os turnos restantes
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public Integer getVida() { return vida; }
    public void setVida(Integer vida) {
        this.vida = Math.max(0, Math.min(vida, vidaMaxima()));
        if (this.vida <= 0) {
            System.out.println(this.nome + " desmaiou!");
        }
    }
    public Integer getForcaAtaque() { return forcaAtaque; }
    public Integer getForcaDefesa() { return forcaDefesa; }
    public boolean isParalisado() { return paralisado; }
    public boolean isEnvenenado() { return envenenado; }
    public boolean isDormindo() { return dormindo; }
    public boolean isQueimando() { return queimando; }
    public boolean isMegaEvoluido() { return megaEvoluido; }
    public boolean isDefesaReduzida() { return defesaReduzida; }

    public void setParalisado(boolean paralisado) {
        this.paralisado = paralisado;
        if (paralisado) this.turnosParalisado = 2;
    }

    public void setEnvenenado(boolean envenenado) {
        this.envenenado = envenenado;
        if (envenenado) this.turnosEnvenenado = 3;
    }

    public void setDormindo(boolean dormindo) {
        this.dormindo = dormindo;
        if (dormindo) this.turnosDormindo = 2;
    }

    public void setQueimando(boolean queimando) {
        this.queimando = queimando;
        if (queimando) this.turnosQueimando = 2;
    }

    public void setDefesaReduzida(boolean reduzida) {
        this.defesaReduzida = reduzida;
        if (reduzida) this.turnosDefesaReduzida = 2;
    }
}