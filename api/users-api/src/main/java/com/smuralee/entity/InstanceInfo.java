package com.smuralee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstanceInfo implements Serializable {

    private String hostname;
    private String hostIpAddress;

}
