package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Marknadsbudskap{
    @JsonProperty("MarknadsbudskapText") 
    public String marknadsbudskapText;
    @JsonProperty("Språk") 
    public String språk;
    @JsonProperty("Sekvensnummer") 
    public int sekvensnummer;
}
