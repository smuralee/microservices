package com.smuralee.entity;

import lombok.Value;
import org.javamoney.moneta.Money;

import java.io.Serializable;

@Value
public class Order implements Serializable {
    Long id;
    String name;
    Money cost;
}
