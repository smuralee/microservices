package com.smuralee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private Long id;
    private Name name;
    private Integer age;
    private List<Order> orders;

    public String getName() {
        return this.name.toString();
    }
}
