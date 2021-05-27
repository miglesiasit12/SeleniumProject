package api.controller;

import api.model.GeoapifyMap;
import api.service.GeoapifyMapService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MapController {

    final GeoapifyMapService geoapifyMapService;
    final static String secretDeleteKey = "ab@sk#!21";

    public MapController(GeoapifyMapService geoapifyMapService) {
        this.geoapifyMapService = geoapifyMapService;
    }

    @PostMapping("/map")
    @ApiOperation("Creates a map")
    public ResponseEntity<String> createMap(@RequestBody GeoapifyMap geoapifyMap) {
        geoapifyMapService.addMap(geoapifyMap);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/map")
    @ApiOperation("Gets a list of maps")
    public ResponseEntity<List<GeoapifyMap>> getMaps(@RequestParam(value = "mapNames") List<String> mapNames) {
        List<GeoapifyMap> maps = geoapifyMapService.getMaps(mapNames);
        return ResponseEntity.status(HttpStatus.OK).body(maps);
    }


    @DeleteMapping("/map")
    @ApiOperation("Deletes maps")
    public ResponseEntity<String> deleteMaps(@RequestParam(value = "mapNames") List<String> mapNames,
                                             @RequestParam(value = "secretKey") String secretKey) {
        long numberDeleted = geoapifyMapService.deleteMaps(mapNames);

        if (secretKey.equals(secretDeleteKey)){
            return ResponseEntity.status(HttpStatus.OK).body("Deleted " + numberDeleted + " maps");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }
    }

    @PatchMapping("/map")
    @ApiOperation("Update a map")
    public ResponseEntity<GeoapifyMap> updateMap(@RequestParam(value = "mapName") String mapName, @RequestBody GeoapifyMap geoapifyMap) {
        GeoapifyMap updatedMap = geoapifyMapService.updateMap(mapName, geoapifyMap);

        if (updatedMap != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedMap);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new GeoapifyMap());
        }
    }

}
