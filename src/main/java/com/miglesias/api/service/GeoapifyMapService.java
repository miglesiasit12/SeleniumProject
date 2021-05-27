package com.miglesias.api.service;

import com.miglesias.api.model.GeoapifyMap;
import com.miglesias.api.model.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    public GeoapifyMap addMap(GeoapifyMap geoapifyMap) throws UnsupportedEncodingException {
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

    public GeoapifyMap updateMap(String mapName, GeoapifyMap geoapifyMap) {
        Criteria mongoDb = Criteria.where("mapName").is(mapName);
        Update update = new Update();

        update.set("$set", geoapifyMap);
        return mongoTemplate.findAndModify(new Query().addCriteria(mongoDb), update, new FindAndModifyOptions().returnNew(true), GeoapifyMap.class, "Maps");
    }

    private String generateMapUrl(GeoapifyMap geoapifyMap) throws UnsupportedEncodingException {
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
            url.append("color:").append(marker.getColor().replace("#", "%23")).append(";");
            url.append("size:").append(marker.getSize()).append(";");
            url.append("type:").append(marker.getType()).append(";");
            url.append("icon:").append(marker.getIcon()).append("&");
        }

        url.append("apiKey=").append(geoapifyApiKey);
        return url.toString();
    }

}
