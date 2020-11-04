package com.smuralee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    private Long id;
    private Long userId;
    private String name;
    private Money cost;

    public String getCost() {
        return this.cost.toString();
    }
}
