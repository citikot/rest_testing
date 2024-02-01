package it.discovery.dto;

import it.discovery.validator.TitleConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookDTO {
    private int id;

    @NotEmpty
    private String author;

    @TitleConstraint
    private String title;

    @Min(1970)
    private int year;

    @Min(1)
    private int amount;
}
