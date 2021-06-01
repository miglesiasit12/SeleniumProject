package com.miglesias.api.controllers;

import com.miglesias.api.model.DeleteResponse;
import com.miglesias.api.model.GeoapifyMap;
import com.miglesias.api.model.GetMapResponse;
import com.miglesias.api.service.GeoapifyMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Map creation service")
@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class MapController {

    final GeoapifyMapService geoapifyMapService;
    final static String secretDeleteKey = "ab@sk#!21";

    public MapController(GeoapifyMapService geoapifyMapService) {
        this.geoapifyMapService = geoapifyMapService;
    }

    @PostMapping("/map")
    @ApiOperation("Creates a map")
    public ResponseEntity<GeoapifyMap> createMap(@Valid @RequestBody GeoapifyMap geoapifyMap) {
        GeoapifyMap geoapifyMapCreated = geoapifyMapService.addMap(geoapifyMap);

        return ResponseEntity.status(HttpStatus.CREATED).body(geoapifyMapCreated);
    }

    @GetMapping("/map")
    @ApiOperation("Gets a list of maps")
    public ResponseEntity<GetMapResponse> getMaps(@RequestParam(value = "mapNames") List<String> mapNames) {
        List<GeoapifyMap> maps = geoapifyMapService.getMaps(mapNames);
        GetMapResponse getMapResponse = new GetMapResponse(new ArrayList<>());

        if (maps.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getMapResponse);
        }
        getMapResponse.getMaps().addAll(maps);
        return ResponseEntity.status(HttpStatus.OK).body(getMapResponse);
    }

    @DeleteMapping("/map")
    @ApiOperation("Deletes maps")
    public ResponseEntity<DeleteResponse> deleteMaps(@RequestParam(value = "mapNames") List<String> mapNames,
                                                     @RequestParam(value = "secretKey") String secretKey) {
        long numberDeleted = geoapifyMapService.deleteMaps(mapNames);

        if (secretKey.equals(secretDeleteKey)) {
            return ResponseEntity.status(HttpStatus.OK).body(new DeleteResponse("Deleted " + numberDeleted + " maps"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DeleteResponse(""));
        }
    }

    @PatchMapping("/map/{mapName}")
    @ApiOperation("Update a map")
    public ResponseEntity<GeoapifyMap> updateMap(@PathVariable(value = "mapName") String mapName,
                                                 @RequestParam(required = false) String area,
                                                 @RequestParam(required = false) String style,
                                                 @RequestParam(required = false) Integer height,
                                                 @RequestParam(required = false) Integer width) {
        GeoapifyMap updatedMap = geoapifyMapService.updateMap(mapName, area, style, height, width);

        if (updatedMap != null) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedMap);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new GeoapifyMap());
        }
    }
}
