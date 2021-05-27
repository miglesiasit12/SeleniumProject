package api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class Marker {

    @NotBlank(message = "Color is mandatory, marker color, named or in hex form (e.g #ff0000. Note, # should be URL encoded)")
    @Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
    private String color;

    @NotBlank(message = "Icon is required. choose from https://fontawesome.com/v5.15/icons?d=gallery&p=2&s=solid&m=free")
    private String icon;

    @NotBlank(message = "Longitude and Latitude is required ")
    @Pattern(regexp = "^((-?|\\+?)?\\d+(\\.\\d+)?),\\s*((-?|\\+?)?\\d+(\\.\\d+)?)$", message = "Separated by comma")
    private String lonlat;

    @Pattern(regexp = "small|medium|large|x-large|xx-large", message = "small|medium|large|x-large|xx-large")
    @NotBlank(message = "Size is required. choose from small|medium|large|x-large|xx-large")
    private String size;

    @Pattern(regexp = "material|awesome|circle", message = "marker type, one of material, awesome, circle (e.g. circle)")
    @NotBlank(message = "marker type is required. choose from one of material, awesome, circle (e.g. circle)")
    private String type;



}
