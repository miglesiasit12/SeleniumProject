package com.miglesias.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Marker {

    @NotNull
    @NotBlank(message = "Color is required, marker color names allowed blue|red|green|purple|black|yellow")
    @Pattern(regexp = "blue|red|green|purple|black|yellow|brown", message = "marker color names allowed blue|red|green|purple|black|yellow")
    private String color;

    @NotNull
    @NotBlank(message = "Longitude and Latitude is required ")
    @Pattern(regexp = "^((-?|\\+?)?\\d+(\\.\\d+)?),\\s*((-?|\\+?)?\\d+(\\.\\d+)?)$", message = "Longitude and Latitude Separated by comma")
    private String lonlat;

    @NotNull
    @NotBlank(message = "Icon is required. choose from https://fontawesome.com/v5.15/icons?d=gallery&p=2&s=solid&m=free")
    private String icon;

    @NotNull
    @Pattern(regexp = "small|medium|large|x-large|xx-large", message = "small|medium|large|x-large|xx-large")
    @NotBlank(message = "Size is required. choose from small|medium|large|x-large|xx-large")
    private String size;

    @NotNull
    @Pattern(regexp = "material|awesome|circle", message = "marker type, one of material, awesome, circle (e.g. circle)")
    @NotBlank(message = "marker type is required. choose from one of material, awesome, circle (e.g. circle)")
    private String type;
}
