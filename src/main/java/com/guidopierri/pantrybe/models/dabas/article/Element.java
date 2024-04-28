package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
public class Element{
    @JsonProperty("ElementId")
    public String elementId;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("ValueSets")
    public ArrayList<ArrayList<ValueSet>> valueSets;
}
