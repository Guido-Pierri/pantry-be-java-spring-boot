package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Varugrupp{
    @JsonProperty("VaruundergruppKod") 
    public int varuundergruppKod;
    @JsonProperty("VaruundergruppBenamning") 
    public String varuundergruppBenamning;
    @JsonProperty("Produktgruppskod") 
    public String produktgruppskod;
    @JsonProperty("VarugruppKod") 
    public int varugruppKod;
    @JsonProperty("VarugruppBenamning") 
    public String varugruppBenamning;
    @JsonProperty("HuvudgruppKod") 
    public int huvudgruppKod;
    @JsonProperty("HuvudgruppBenamning") 
    public String huvudgruppBenamning;
    @JsonProperty("VaruomradeKod") 
    public int varuomradeKod;
    @JsonProperty("VaruomradeBenamning") 
    public String varuomradeBenamning;
}
