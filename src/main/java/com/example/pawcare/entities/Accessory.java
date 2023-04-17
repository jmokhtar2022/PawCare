package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Accessory implements Serializable {

    @ManyToMany(mappedBy = "accessories",fetch = FetchType.LAZY)
    @JsonIgnoreProperties("accessories")
    private List<Cart> carts;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccessory;
    @Size(min = 4, max = 10, message = "Name should be between 4 and 10 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name should contain only alphabets")
    @NotBlank(message = "Name is required")

    private String name;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "20.00", message = "Price must be greater than or equal to 20.00")

   private float price;
    @Size(min = 10, max = 15, message = "Description should be between 10 and 500 characters")
    @NotBlank(message = "Description is required")

    private String description;
    @NotBlank(message = "Image is required")

    private String image;


}
