package org.polytech.covidapi.resources;

import org.polytech.covidapi.entities.Meeting;
import org.polytech.covidapi.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.polytech.covidapi.services.MeetingService;

@RestController
@RequestMapping()
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMeetings() {
        return ResponseEntity.ok(this.meetingService.getAllMeetings());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
        return ResponseEntity.ok(this.meetingService.addMeeting(meeting));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMeeting(@RequestBody Meeting meeting) {
        return ResponseEntity.ok(this.meetingService.updateMeeting(meeting));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMeeting(@RequestParam Long id) {
        this.meetingService.deleteMeetingById(id);
        return ResponseEntity.ok().build();
    }
}
