package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Tillverkare{
    @JsonProperty("Namn") 
    public String namn;
    @JsonProperty("EAN") 
    public String eAN;
}
