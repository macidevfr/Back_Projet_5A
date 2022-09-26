package Controllers;

import JPA.ICentrer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/center")
public class CenterController {

    private final ICentrer iCentrer;

    public CenterController(ICentrer iCentrer) {
        this.iCentrer = iCentrer;
    }

    @GetMapping
    public ResponseEntity getAllCenters(){
        return ResponseEntity.ok(this.iCentrer.findAll());  //reponse 200
    }

    

}
