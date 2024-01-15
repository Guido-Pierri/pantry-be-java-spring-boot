package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Varumarke{
    @JsonProperty("Varumarke") 
    public String varumarke;
    @JsonProperty("AgareGLN") 
    public Object agareGLN;
    @JsonProperty("AgareNamn") 
    public String agareNamn;
    @JsonProperty("Tillverkare") 
    public Tillverkare tillverkare;
}
