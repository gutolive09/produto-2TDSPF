package br.com.fiap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        String dia = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "fiap" : "casa";

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(dia);
        EntityManager manager = factory.createEntityManager();

        Sabor manjericao = new Sabor();
        manjericao.setNome("Manjericão");
        manjericao.setDescricao("Maravilhoso manjericão plantado e colhido pelos mais belos agricultores do Alegrete.");


        Opcional bordaDeCatupiri = new Opcional(null, "Borda de Catupiri", 9.99);

        Opcional bordaPaozinho = new Opcional(null, "Borda paozinho", 19.99);

        Opcional cocaCola = new Opcional(null, "Coca Cola", 19.99);


        Set<Opcional> opcionals = Stream
                .of(cocaCola, bordaPaozinho, bordaDeCatupiri)
                .collect(Collectors.toSet());


        Produto produto = new Produto();
        produto.setNome("Pizza");
        produto.setPreco(69.99);
        produto.setSabor(manjericao);
        produto.setOpcionais(opcionals);

        manager.getTransaction().begin();

        opcionals.forEach(manager::persist);
        manager.persist(manjericao);
        manager.persist(produto);
        manager.getTransaction().commit();

        factory.close();
        manager.close();
    }
}