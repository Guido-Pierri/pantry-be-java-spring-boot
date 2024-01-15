package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Ursprungstyp{
    @JsonProperty("Beskrivning") 
    public String beskrivning;
    @JsonProperty("Ravarotyp") 
    public Ravarotyp ravarotyp;
}
