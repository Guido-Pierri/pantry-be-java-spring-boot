package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MaskinellMarkningar{
    @JsonProperty("MaskinellMarkningKod") 
    public String maskinellMarkningKod;
    @JsonProperty("MaskinellMarkning") 
    public String maskinellMarkning;
    @JsonProperty("TypAvMarkning") 
    public Object typAvMarkning;
    @JsonProperty("DatabarartypKod") 
    public String databarartypKod;
    @JsonProperty("Databarartyp") 
    public String databarartyp;
    @JsonProperty("TypAvMaskinelltLäsbarInformation") 
    public ArrayList<TypAvMaskinelltLäsbarInformation> typAvMaskinelltLäsbarInformation;
}
