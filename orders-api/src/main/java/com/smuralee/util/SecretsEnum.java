package com.smuralee.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecretsEnum {

    postgres("jdbc:postgresql://", "org.postgresql.Driver"),
    mariadb("jdbc:mariadb://", "org.mariadb.jdbc.Driver");

    private final String jdbcPrefix;
    private final String jdbcDriverClassName;
}
