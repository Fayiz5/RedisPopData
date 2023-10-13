package com.RedisQueuRead.RedisRead.Entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyData {
    //private static final long serialVersionUID = 12L;

    private int id;
    private String name;
    private long phone;
    private String city;


    public MyData(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MyData myData = objectMapper.readValue(json, MyData.class);
            this.id = myData.getId();
            this.name = myData.getName();
            this.phone = myData.getPhone();
            this.city = myData.getCity();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}