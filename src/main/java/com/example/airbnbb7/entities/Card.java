package com.example.airbnbb7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cards")

public class Card {
    @Id
    @SequenceGenerator(name = "cards_gen", sequenceName = "cards_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cards_gen")
    private Long id;

    private BigInteger cardNumber;

    private String cardExpiryMonth;
    private String cardExpiryYear;

    private int cvc;


}
