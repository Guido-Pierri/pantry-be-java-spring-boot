package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Ursprung{
    @JsonProperty("UrsprungslandKod") 
    public String ursprungslandKod;
    @JsonProperty("Ursprungsland") 
    public String ursprungsland;
    @JsonProperty("Ursprungstyp") 
    public Ursprungstyp ursprungstyp;
}
