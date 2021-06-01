package com.miglesias.api.service;

import com.miglesias.api.model.GeoapifyMap;
import com.miglesias.api.model.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GeoapifyMapService {

    private final MongoTemplate mongoTemplate;

    @Value("${geoapifyMapBaseUrl}")
    private String geoapifyMapBaseUrl;

    @Value("${geoapifyApiKey}")
    private String geoapifyApiKey;

    @Autowired
    public GeoapifyMapService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public GeoapifyMap addMap(GeoapifyMap geoapifyMap)  {
        geoapifyMap.setUrl(generateMapUrl(geoapifyMap));
        mongoTemplate.save(geoapifyMap, "Maps");
        return geoapifyMap;
    }

    public List<GeoapifyMap> getMaps(List<String> mapNames) {
        Criteria mongoDb = Criteria.where("mapName").in(mapNames);
        return mongoTemplate.find(new Query().addCriteria(mongoDb), GeoapifyMap.class, "Maps");
    }

    public long deleteMaps(List<String> mapName) {
        Criteria mongoDb = Criteria.where("mapName").in(mapName);
        return mongoTemplate.remove(new Query().addCriteria(mongoDb), GeoapifyMap.class, "Maps").getDeletedCount();
    }

    public GeoapifyMap updateMap(String mapName, String area, String style, Integer height, Integer width) {
        Criteria mongoDb = Criteria.where("mapName").is(mapName);
        GeoapifyMap geoapifyMap;

        geoapifyMap = mongoTemplate.findOne(new Query().addCriteria(mongoDb), GeoapifyMap.class, "Maps");
        if (geoapifyMap != null) {
            if (area != null) {
                geoapifyMap.setArea(area);
            }
            if (style != null) {
                geoapifyMap.setStyle(style);
            }
            if (height != null) {
                geoapifyMap.setHeight(height);
            }
            if (width != null) {
                geoapifyMap.setWidth(width);
            }
            geoapifyMap.setUrl(generateMapUrl(geoapifyMap));
            mongoTemplate.save(geoapifyMap, "maps");
        }
        return geoapifyMap;
    }

    private String generateMapUrl(GeoapifyMap geoapifyMap) {
        StringBuilder url = new StringBuilder(geoapifyMapBaseUrl + "?");
        String style = geoapifyMap.getStyle();
        Integer width = geoapifyMap.getWidth();
        Integer height = geoapifyMap.getHeight();
        String area = geoapifyMap.getArea();
        Marker marker = geoapifyMap.getMarker();

        url.append("style=").append(style).append("&");
        if (width != null) {
            url.append("width=").append(geoapifyMap.getWidth()).append("&");
        }
        if (height != null) {
            url.append("height=").append(geoapifyMap.getHeight()).append("&");
        }
        url.append("center=lonlat:").append(area).append("&");

        if (marker != null) {
            url.append("marker=lonlat:").append(marker.getLonlat()).append(";");
            url.append("color:").append(marker.getColor()).append(";");
            url.append("size:").append(marker.getSize()).append(";");
            url.append("type:").append(marker.getType()).append(";");
            url.append("icon:").append(marker.getIcon()).append("&");
        }

        url.append("apiKey=").append(geoapifyApiKey);
        return url.toString();
    }

}
