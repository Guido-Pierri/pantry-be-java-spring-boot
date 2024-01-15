package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MSRKritierier{
    @JsonProperty("Beskrivning") 
    public String beskrivning;
    @JsonProperty("Lank") 
    public String lank;
    @JsonProperty("LankNamn") 
    public String lankNamn;
    @JsonProperty("Verifikat") 
    public Object verifikat;
    @JsonProperty("Ursprungsland") 
    public Object ursprungsland;
    @JsonProperty("Utfardandedatum") 
    public Date utfardandedatum;
    @JsonProperty("SenastUppdateratDatum") 
    public Date senastUppdateratDatum;
}
