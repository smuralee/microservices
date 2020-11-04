package com.smuralee.domain;

import lombok.Value;

import java.io.Serializable;

@Value
public class Name implements Serializable {

    String firstName;
    String middleName;
    String lastName;
}
