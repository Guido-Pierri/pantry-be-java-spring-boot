package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Nettoinnehall{
    @JsonProperty("Enhet") 
    public Object enhet;
    @JsonProperty("EnhetKod")
    public String enhetKod;
    @JsonProperty("Mängd") 
    public int mängd;
    @JsonProperty("Mängd_Formatted") 
    public String mängd_Formatted;
    @JsonProperty("Typkod") 
    public int typkod;
    @JsonProperty("Typ") 
    public String typ;
}
