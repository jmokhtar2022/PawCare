package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
public class Accessory implements Serializable {
    public static final List<String> BAD_WORDS = Arrays.asList("badword", "sobadword", "verybadword");


    @ManyToMany(mappedBy = "accessories",fetch = FetchType.LAZY)
    @JsonIgnoreProperties("accessories")
    private List<Cart> carts;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccessory;
    @Size(min = 4, max = 40, message = "Name should be between 4 and 40 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name should contain only alphabets and spaces")
    @NotBlank(message = "Name is required")
    @Badword
    private String name;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "10.00", message = "Price must be greater than or equal to 10.00")

   private float price;
    @Size(min = 10, max = 50, message = "Description should be between 10 and 50 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9\\s]+$", message = "Description should contain alphabets, spaces, and numbers but not only numbers")
    @NotBlank(message = "Description is required")

    private String description;
    @NotBlank(message = "Image is required")

    private String image;
    // @Lob
    // private byte[] image;

}
