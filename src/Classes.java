
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

class Prédio {

    Cidade cidade;
    String rua;
    Preço preço;

    public Prédio(Cidade cidade, String rua, Preço preço) {
        this.cidade = cidade;
        this.rua = rua;
        this.preço = preço;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1}", rua, preço);
    }

    public Cidade getCidade() {
        return cidade;
    }
    
    

}

enum Cidade {
    Lisboa, Porto, Faro;
}

class Preço {

    Moeda moeda;
    int valor;

    public Preço(Moeda moeda, int valor) {
        this.moeda = moeda;
        this.valor = valor;
    }

    double €() {
        return valor / moeda.conversão;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}{1}", moeda, valor);
    }

}

enum Moeda {

    DKK("dk", 7.44), EUR, GBP("£", 0.84);

    String símbolo = "€";
    double conversão = 1.0; // permite construtor EUR sem arg

    private Moeda() { // apenas o EUR utilizará este construtor
    }

    private Moeda(String símbolo, double conversão) {
        this.símbolo = símbolo;
        this.conversão = conversão;
    }

    @Override
    public String toString() {
        return símbolo;
    }

}

class DB {

    private static Collection<Prédio> prédios;

    static {
        prédios = Arrays.asList(
                new Prédio[]{
                    new Prédio(Cidade.Lisboa, "Rua do Olival", new Preço(Moeda.EUR, 120000)),
                    new Prédio(Cidade.Lisboa, "Rua da Laranx", new Preço(Moeda.EUR, 250000)),
                    new Prédio(Cidade.Lisboa, "Rua do Teclor", new Preço(Moeda.EUR, 250000)),
                    new Prédio(Cidade.Porto, "Rua da Manhã", new Preço(Moeda.EUR, 75000)),
                    new Prédio(Cidade.Porto, "Rua do Picar", new Preço(Moeda.EUR, 50000)),
                    new Prédio(Cidade.Porto, "Rua da Quota", new Preço(Moeda.EUR, 180000)),
                    new Prédio(Cidade.Faro, "Rua do Dinok", new Preço(Moeda.DKK, 896000)),
                    new Prédio(Cidade.Faro, "Rua da Dinas", new Preço(Moeda.GBP, 99000)),
                    new Prédio(Cidade.Faro, "Rua do Xóxoju", new Preço(Moeda.DKK, 744000)),
                    new Prédio(Cidade.Faro, "Rua da Moneys", new Preço(Moeda.EUR, 1640000))
                }
        );

    }

    static Stream<Prédio> registos() {
        return prédios.parallelStream();
    }

}
