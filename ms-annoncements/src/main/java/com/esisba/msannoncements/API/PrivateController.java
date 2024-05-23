package com.esisba.msannoncements.API;

import com.esisba.msannoncements.DAO.AnnouncementRepository;
import com.esisba.msannoncements.DTO.AnnouncementDTO;
import com.esisba.msannoncements.Proxy.AuthProxy;
import com.esisba.msannoncements.Proxy.JwtDto;
import com.esisba.msannoncements.Proxy.Permission;
import com.esisba.msannoncements.Proxy.UserInfosDto;
import com.esisba.msannoncements.documents.Announcement;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("private")
public class PrivateController {

    @Autowired
    AnnouncementRepository announcementRepository;

    @Autowired
    AuthProxy authProxy;

    @PostMapping("create")
    private ResponseEntity<String> createAnnouncement(@RequestBody AnnouncementDTO body , @RequestHeader("Authorization") String authorizationHeader){

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.ANNOUNCEMENT)) {


                    Announcement announcement = new Announcement(null , authResponse.getCompanyId()  ,  body.getImagePath(), body.getStartDate() , body.getEndDate() , LocalDateTime.now());

                    if(body.getStartDate().before(body.getEndDate())){

                        announcementRepository.save(announcement);
                        return ResponseEntity.ok().body("Announcement Created Successfully");

                    }else{
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date should be before end date");
                    }
                }
                else{
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Need Permission");
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unvalid Token");
            }

        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing");
        }

    }

    @GetMapping("announcements")
    public ResponseEntity<Page<Announcement>> getAnnouncements(@RequestParam int page, @RequestParam int size, @RequestHeader("Authorization") String authorizationHeader){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){
                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.ANNOUNCEMENT)) {

                    Pageable pageable = PageRequest.of(page, size , Sort.by("createdAt").descending());

                    return ResponseEntity.ok().body(announcementRepository.findAnnouncementsByCompanyId(authResponse.getCompanyId() , pageable));
                }
                else{
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Page.empty());
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Page.empty());
            }
    }

    @DeleteMapping("{announcementId}")
    private ResponseEntity<String> deleteAnnouncement(@PathVariable String announcementId , @RequestHeader("Authorization") String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.ANNOUNCEMENT)) {

                    Optional<Announcement> announcement = announcementRepository.findById(announcementId);

                    if(announcement.isPresent()){

                        if(authResponse.getCompanyId() == announcement.get().getCompanyId()) {
                            announcementRepository.deleteById(announcementId);
                            return ResponseEntity.ok().body("Announcement Deleted");
                        } else
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You can't delete announcement that does not belong to your company");
                    }
                    else{
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Need Permission");
                    }
                } else
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement does not exist");

            } else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unvalid Token");

        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing");


    }
}
