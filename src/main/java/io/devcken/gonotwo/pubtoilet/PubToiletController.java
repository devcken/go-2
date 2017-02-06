package io.devcken.gonotwo.pubtoilet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class PubToiletController {
  private final PubToiletService pubToiletService;

  @Inject
  public PubToiletController(PubToiletService pubToiletService) {
    this.pubToiletService = pubToiletService;
  }

  @RequestMapping("/toilet/{xLong:[\\d]+\\.[\\d]+},{xLat:[\\d]+\\.[\\d]+},{yLong:[\\d]+\\.[\\d]+},{yLat:[\\d]+\\.[\\d]+}")
  ResponseEntity<Object> toilets(@PathVariable Double xLong, @PathVariable Double xLat,
                                 @PathVariable Double yLong, @PathVariable Double yLat) {
    List<PubToilet> toilets = this.pubToiletService.findToilets(xLong, xLat, yLong, yLat);

    return ResponseEntity.ok(toilets);
  }
}
