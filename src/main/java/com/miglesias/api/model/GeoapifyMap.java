package com.miglesias.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class GeoapifyMap {

    @NotNull
    @NotBlank(message = "MapName is mandatory")
    private String mapName;

    @NotBlank(message = "area to display, currently supports only rectangular regions defined by 2 comma-separated pairs of longitude,latitude coordinates (e.g \"rect:12.024,42.226,13.001,41.542\")")
    @Pattern(regexp = "^((-?|\\+?)?\\d+(\\.\\d+)?),\\s*((-?|\\+?)?\\d+(\\.\\d+)?)$", message = "Longitude and Latitude Separated by comma")
    private String area;

    @Pattern(regexp = "osm-carto|osm-bright|osm-bright-grey|osm-bright-smooth|klokantech-basic|osm-liberty|maptiler-3d|toner|toner-grey|positron|positron-blue|positron-red|dark-matter|dark-matter-brown|dark-matter-dark-grey|dark-matter-dark-purple|dark-matter-purple-roads|dark-matter-yellow-roads",
    message = "Styles accepted osm-carto,osm-bright,osm-bright-grey,osm-bright-smooth,klokantech-basic,osm-liberty,maptiler-3d,toner,toner-grey,positron,positron-blue,positron-red,dark-matter,dark-matter-brown,dark-matter-dark-grey,dark-matter-dark-purple,dark-matter-purple-roads,dark-matter-yellow-roads")
    @NotBlank(message = "Styles accepted osm-carto,osm-bright,osm-bright-grey,osm-bright-smooth,klokantech-basic,osm-liberty,maptiler-3d,toner,toner-grey,positron,positron-blue,positron-red,dark-matter,dark-matter-brown,dark-matter-dark-grey,dark-matter-dark-purple,dark-matter-purple-roads,dark-matter-yellow-roads")
    private String style;

    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    @NotNull
    @Valid
    private Marker marker;

    private String url;

    @Builder(toBuilder = true)
    public GeoapifyMap(String mapName, String area, String style, int width, int height, Marker marker) {
        this.mapName = mapName;
        this.area = area;
        this.style = style;
        this.width = width;
        this.height = height;
        this.marker = marker;
    }
}
