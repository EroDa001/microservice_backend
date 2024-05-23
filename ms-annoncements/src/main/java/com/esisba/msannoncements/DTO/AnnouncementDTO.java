package com.esisba.msannoncements.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AnnouncementDTO {

    private String imagePath;
    private Date startDate;
    private Date endDate;

}
