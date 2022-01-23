package com.io.ReadingIsGood.vo;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewBookItem {

    private UUID id;

    @NotBlank
    @Size(min=3, max = 60)
    private String name;

    @NotBlank
    @Size(min=3, max = 60)
    private String description;

    @Min(value=10, message="Page count: positive number, min 10 is required")
    @Range(min=10, max=1000, message = "Page count must be in range of 10-1000")
    private int pageCount;

    @NotBlank
    @Size(min=3, max = 60)
    private String authorName;

    @Min(value=0, message="Available book stock count: positive number, min 0 is required")
    @Range(min=0, max=10000, message = "Stock count must be in range of 0-10000")
    private int availableCount;

    @Min(value=5, message="Book Price: positive number, min 5 is required")
    @Range(min=5, max=10000, message = "Price must be in range of 5-10000")
    private double price;

}
