package Controller;

import JPA.ICenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CenterController {
    @Autowired
    private  ICenter iCentrer;


    @GetMapping("/center")
    public ResponseEntity getAllCenters(){
        return ResponseEntity.ok(this.iCentrer.findAll());  //reponse 200
    }



}
