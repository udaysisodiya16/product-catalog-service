package com.example.capstoneproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Category extends BaseModel {
    private String name;

    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    //@Fetch(FetchMode.SELECT)
    //@BatchSize(size=10)
    private List<Product> products;
}
//N+1 Problem  => 1 + 6 ,
//if(n == N) -> 1 + 1 n=6 -> 1 + 1
//if(n < N) -> 1 + N/n , n = 3 ,  1+2
//if(n>N) -> 1+1


//Hypothesis
//Fetch Mode JOIN has dominance over any fetch type
//In case of FetchMode select and subselect, FetchType is getting obeyed/honored
