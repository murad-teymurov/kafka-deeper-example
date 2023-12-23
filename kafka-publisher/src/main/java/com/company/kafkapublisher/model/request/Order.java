package com.company.kafkapublisher.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Order {

    private String id;
    private String username;
    private String price;
    private String createdTime;
}
