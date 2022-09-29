package com.demo.nbr;

import com.demo.nbr.entity.Human;
import com.demo.nbr.entity.Link;
import com.demo.nbr.service.HumanService;
import com.demo.nbr.service.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    //    @Bean
    CommandLineRunner initData(PersonRepository personRepository) {
        return args -> {

            List<Human> humans = genEmployee(4);
            Iterable<Human> humanListSave = personRepository.saveAll(humans);

            Human boss = new Human();
            boss.setBirth(LocalDateTime.now());
            boss.setName("boss");

            for (int i = 0; i < 2; i++) {
                for (Human human : humanListSave) {
                    boss.getCallList().add(genLinkByHuman(human));
                    personRepository.save(boss);
                }
            }

        };
    }

    @Bean
    CommandLineRunner queryPerson(PersonRepository personRepository) {
        return args -> {

            for (Human human : personRepository.findAll()) {
                System.out.println(human);
            }

            System.out.println("----------- custom query ---------");
            for (Human human : personRepository.customQuery()) {
                System.out.println(human);
            }

            System.out.println("----------- find by id ---------");

            personRepository.findById("boss")
                    .ifPresent(System.out::println);
        };
    }

    @Bean
    CommandLineRunner queryService(HumanService humanService) {
        return args -> {
            for (Human human : humanService.correctMapping()) {
                System.out.println(human);
            }

            System.out.println("---------------------");
            for (Human human : humanService.errorMapping()) {
                System.out.println(human);
            }
        };

    }


    List<Human> genEmployee(int num) {
        return IntStream
                .rangeClosed(0, num)
                .boxed()
                .map(i -> {
                    Human human = new Human();
                    human.setName("employee-" + i);
                    human.setBirth(LocalDateTime.now().minusYears(i));
                    return human;
                }).collect(Collectors.toList());
    }

    Link genLinkByHuman(Human human) {
        Link link = new Link();
        int i = new Random().nextInt(10);
        link.setCallTime(LocalDateTime.now().plusWeeks(i));
        link.setPerson(human);
        return link;
    }
}
