package org.polytech.covidapi.repositories;

import org.polytech.covidapi.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMeeting extends JpaRepository<Meeting,Long> {

    Meeting findMeetingByCenter(String name);
}
