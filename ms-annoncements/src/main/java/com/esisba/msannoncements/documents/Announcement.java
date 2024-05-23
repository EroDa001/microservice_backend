package com.esisba.msannoncements.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document
@Data @AllArgsConstructor @NoArgsConstructor
public class Announcement {

    @Id
    private String announcementId;

    private Integer companyId;

    private String imagePath;

    private Date startDate;
    private Date endDate;

    private LocalDateTime createdAt;

}
