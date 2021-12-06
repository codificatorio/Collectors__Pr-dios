
import java.text.MessageFormat;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Collectors__Prédios__v02 {

    public static void main(String[] args) {
        Map<Cidade, List<Prédio>> porCidade;
        Supplier<Map<Cidade, List<Prédio>>> supplier; // Supplier<A> supplier: A function that creates and returns a new mutable result container."
        BiConsumer<Map<Cidade, List<Prédio>>, Prédio> acumulador; // BiConsumer<A,T> accumulator: A function that folds a value into a mutable result container.
        BinaryOperator<Map<Cidade, List<Prédio>>> combinador;
        supplier = HashMap::new;
        acumulador = new BiConsumer<Map<Cidade, List<Prédio>>, Prédio>() {
            @Override
            public void accept(Map<Cidade, List<Prédio>> prédios, Prédio prédio) {
                System.out.println("Acumulando por " + Thread.currentThread());
                prédios.putIfAbsent(prédio.cidade, new ArrayList()); // criar lista se não existir ainda
                prédios.get(prédio.cidade).add(prédio);
            }
        };
        combinador = (existentes, novos) -> {
            System.out.println("Combinando por " + Thread.currentThread());
            novos.forEach((Cidade cidade, List<Prédio> prédios) -> {
                if (null != existentes.putIfAbsent(cidade, prédios)) {
                    existentes.get(cidade).addAll(prédios);
                }
            });
            return existentes;
        };
        Collector<Prédio, BiConsumer<Map<Cidade, List<Prédio>>, Prédio>, Map<Cidade, List<Prédio>>> colecionador = new Collector() {
            @Override
            public Supplier supplier() {
                return supplier;
            }

            @Override
            public BiConsumer accumulator() {
                return acumulador;
            }

            @Override
            public BinaryOperator combiner() {
                return combinador;
            }

            @Override
            public Function finisher() {
                throw new UnsupportedOperationException("Finisher: not supported yet.");
            }

            @Override
            public Set characteristics() {
                Set<Collector.Characteristics> caraterísticas = new HashSet();
                caraterísticas.add(Collector.Characteristics.IDENTITY_FINISH);
                caraterísticas.add(Collector.Characteristics.CONCURRENT);
                return caraterísticas;
            }

        };
        porCidade = DB.registos().collect(colecionador);
        System.out.println(porCidade);
    }
}

