package com.example.batch7.ch3.optional.sample1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailProduct {
    private String description;
    private Optional<Category> category;
}
