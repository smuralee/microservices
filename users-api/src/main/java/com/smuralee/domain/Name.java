package com.smuralee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Name implements Serializable {

    private static final String SPACE = " ";

    private String firstName;
    private String middleName;
    private String lastName;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.appendValue(sb, this.firstName);
        this.appendValue(sb, this.middleName);
        this.appendValue(sb, this.lastName);
        return sb.toString().strip();
    }

    private void appendValue(StringBuilder sb, String value) {
        if (isValid(value)) {
            sb.append(value.concat(SPACE));
        }
    }

    private boolean isValid(String value) {
        return value != null && !value.isBlank() && !value.isEmpty();
    }
}
