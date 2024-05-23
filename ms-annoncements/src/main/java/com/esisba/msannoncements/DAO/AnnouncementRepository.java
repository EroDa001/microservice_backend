package com.esisba.msannoncements.DAO;

import com.esisba.msannoncements.documents.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface AnnouncementRepository extends MongoRepository<Announcement , String> {

    @Query(value = "{ 'startDate' : { $lte: ?0 }, 'endDate' : { $gte: ?0 } }", sort = "{ 'startDate' : 1 }")
    List<Announcement> findActiveAnnouncements(Date currentDate);

    Page<Announcement> findAnnouncementsByCompanyId(int id, Pageable pageable);

}
