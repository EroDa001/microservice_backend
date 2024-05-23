package com.esisba.productscoreapi.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.common.value.qual.IntRange;

import java.util.Date;

@Data @AllArgsConstructor
public class Discount {
    private Integer value;
    private Date startDate;
    private Date endDate;
}
