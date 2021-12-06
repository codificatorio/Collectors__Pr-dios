
import java.util.*;
import java.util.stream.*;

public class Collectors__Prédios__v03 {

    public static void main(String[] args) {
        Map<Cidade, List<Prédio>> porCidade;
        Map<Cidade, List<Prédio>> baratos;
        Map<Boolean, List<Prédio>> carosEmFaro;

        porCidade = DB.registos().collect(Collectors.groupingBy(Prédio::getCidade));
        System.out.println("Por cidade: " + porCidade);
        baratos = DB.registos().collect(Collectors.filtering(prédio -> prédio.preço.€() < 100000, Collectors.groupingBy(Prédio::getCidade)));
        System.out.println("< €100000: " + baratos);
        carosEmFaro = DB.registos().collect(Collectors.filtering(prédio -> prédio.preço.€() >= 100000, Collectors.partitioningBy(prédio -> prédio.cidade == Cidade.Faro))); // se pretender agrupar for true e false:
        System.out.println("Caros em Faro? " + carosEmFaro);
    }
}
