package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class MediaFiler{
    @JsonProperty("Informationstyp") 
    public Object informationstyp;
    @JsonProperty("Innehallsbeskrivning") 
    public Object innehallsbeskrivning;
    @JsonProperty("Filformat") 
    public String filformat;
    @JsonProperty("Filnamn") 
    public String filnamn;
    @JsonProperty("Lank") 
    public Object lank;
    @JsonProperty("Sekvensnummer") 
    public int sekvensnummer;
}
