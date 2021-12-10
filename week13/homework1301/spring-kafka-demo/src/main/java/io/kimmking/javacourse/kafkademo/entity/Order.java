package io.kimmking.javacourse.kafkademo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order implements Serializable {

    private Long id;
    private Long ts;
    private String symbol;
    private Double price;

}
