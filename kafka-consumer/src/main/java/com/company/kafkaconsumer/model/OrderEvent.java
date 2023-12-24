package com.company.kafkaconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEvent {

    private String id;
    private String username;
    private String price;
    private String createdTime;
}
