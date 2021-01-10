package com.study.webfluxtest.controller;

import com.study.webfluxtest.vo.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Period;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

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

        CompletableFuture<Person> completableFuture = CompletableFuture.supplyAsync(getData());

        completableFuture.whenCompleteAsync((o,e)->{
            e.printStackTrace();
        });

        completableFuture.thenAccept(o->{
            o.toString();
        });

        completableFuture.thenAcceptAsync(e->{
           e.toString();
        });

        completableFuture.exceptionally(m -> {
            System.out.println(m);
            return Person.builder().name("exception").age(100).build();
        });

//        return Mono.fromFuture(CompletableFuture.<Person>supplyAsync(getData())
//                .thenApply(o -> {
//                    System.out.println(o);
//                    return o;
//                }).exceptionally((m) -> {
//                    m.printStackTrace();
//                    return Person.builder().name("exception").age(100).build();
//                }));

        return null;
    }

    static Person getData() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        int a = 1 / 0;
        return Person.builder().name("窦国海").age(100).build();
    }

}
