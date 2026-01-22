public class ResultadoAtaque {
    private int dano;
    private String efeito;
    private int duracao;

    public ResultadoAtaque(int dano, String efeito, int duracao) {
        this.dano = dano;
        this.efeito = efeito;
        this.duracao = duracao;
    }

    public int getDano() { return dano; }
    public String getEfeito() { return efeito; }
    public int getDuracao() { return duracao; }
}