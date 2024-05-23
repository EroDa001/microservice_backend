package com.esisba.msannoncements.API;

import com.esisba.msannoncements.DAO.AnnouncementRepository;
import com.esisba.msannoncements.documents.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    AnnouncementRepository announcementRepository;

    @GetMapping("announcements")
    public List<Announcement> getAnnouncements(){

        Date currentDate = new Date();
        List<Announcement> activeAnnouncements = announcementRepository.findActiveAnnouncements(currentDate);
        return activeAnnouncements;

    }

}
