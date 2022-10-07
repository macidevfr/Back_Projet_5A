package org.polytech.covidapi.services;

import org.polytech.covidapi.entities.Meeting;
import org.polytech.covidapi.repositories.IMeeting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {

    private final IMeeting iMeeting;

    public MeetingService(IMeeting iMeeting) {
        this.iMeeting = iMeeting;
    }

    public List<Meeting> getAllMeetings() {
        return this.iMeeting.findAll();
    }

    public Meeting addMeeting(Meeting meeting) {
        return this.iMeeting.save(meeting);
    }

    public Meeting updateMeeting(Meeting meeting) {
        return this.iMeeting.save(meeting);
    }

    public void deleteMeetingById(Long id) {
        this.iMeeting.deleteById(id);
    }
}
