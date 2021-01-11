package com.study.webfluxtest.controller;

import com.study.webfluxtest.vo.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @version : 1.0
 * @description: java类作用描述
 * @author: tianwen
 * @create: 2021/1/10 10:12
 **/
@RestController
public class TestController {

    @GetMapping("/mono")
    public Mono<String> hello() {
        return Mono.just("hello world");
    }

    @GetMapping("/getPerson")
    public Mono<Person> getPerson() {
        return Mono.just(Person.builder().name("窦国海").age(12).build());
    }

    @GetMapping("/testFlux")
    public Flux<Person> testFlux() {
        ArrayList<Person> arrayList = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            arrayList.add(Person.builder().name("窦国海").age(i).build());
        }
        return Flux.fromIterable(arrayList).doOnNext(person -> getPerson());
    }

    @GetMapping("/fromCallable")
    public Mono<Person> tesMono() {
        return Mono.fromCallable(() -> Person.builder().name("窦国海").age(100).build());
    }

    @GetMapping("/fromFuture")
    public Mono<Person> fromFuture() {
        return Mono.fromFuture(CompletableFuture.<Person>supplyAsync(new Supplier<Person>() {

            @Override
            public Person get() {
//                int a = 1 / 0;
                return Person.builder().name("窦国海").age(100).build();
            }
        })
                .thenApply(o -> {
                    System.out.println(o);
                    return o;
                }).exceptionally((m) -> {
                    m.printStackTrace();
                    return Person.builder().name("exception").age(100).build();
                }));

    }


    @GetMapping("/delay")
    public Mono<Integer> delay() {
        Flux<Integer> flux = Flux.range(1, 100).map(integer -> {
            if (integer > 100) {
                throw new RuntimeException("sha diao");
            }
            return integer;
        });
        flux.subscribe(System.out::println, System.out::println, () -> System.out.println("Done"));
        return Mono.empty();
    }

    @GetMapping("/generate")
    public Mono<Integer> generate() {
        Flux<String> flux = Flux.generate(() -> 0, (state, sink) -> {
            sink.next("3 x " + state + " = " + 3 * state);
            if (state == 100) {
                sink.complete();
            }
            return state + 1;
        }, (state) -> System.out.println("state: " + state));
        flux.subscribe(System.out::println);
        return null;
    }


}
