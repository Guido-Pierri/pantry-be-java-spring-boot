package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Module{
    @JsonProperty("ModuleId")
    public String moduleId;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("CountryCode")
    public String countryCode;
    @JsonProperty("Elements")
    public ArrayList<Element> elements;
}
