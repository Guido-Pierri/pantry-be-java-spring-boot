package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Bilder{
    @JsonProperty("Informationstyp") 
    public String informationstyp;
    @JsonProperty("Innehallsbeskrivning") 
    public String innehallsbeskrivning;
    @JsonProperty("Filformat") 
    public String filformat;
    @JsonProperty("Filnamn") 
    public String filnamn;
    @JsonProperty("Lank") 
    public String lank;
    @JsonProperty("Sekvensnummer") 
    public int sekvensnummer;
}
