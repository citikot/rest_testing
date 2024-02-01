package it.discovery.web;

import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageCriteria {

    @Max((1000))
    private int page;

    @Max(25)
    private int size;
}
