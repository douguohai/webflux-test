package com.study.webfluxtest.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.function.Supplier;

/**
 * @version : 1.0
 * @description: java类作用描述
 * @author: tianwen
 * @create: 2021/1/10 10:15
 **/
@Data
@Builder
@ToString
public class Person implements Supplier<Person> {

    private String name;

    private Integer age;

    @Override
    public Person get() {
        int a=1/0;
        return this;
    }
}
