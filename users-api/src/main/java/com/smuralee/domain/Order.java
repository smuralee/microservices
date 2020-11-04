package com.smuralee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private Long id;
    private String name;
    private String cost;

    @JsonIgnore
    public BigDecimal getAmount() {
        return Money.parse(this.cost).getNumberStripped();
    }

    @JsonIgnore
    public String getCurrency() {
        return Money.parse(this.cost).getCurrency().getCurrencyCode();
    }
}
