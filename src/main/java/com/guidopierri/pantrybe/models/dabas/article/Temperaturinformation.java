package com.guidopierri.pantrybe.models.dabas.article;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperaturinformation{
    @JsonProperty("T3822_Kod")
    public String t3822_Kod;
    @JsonProperty("T3822_Namn")
    public String t3822_Namn;
    @JsonProperty("T3796_MaxTemperatur_Varde")
    public double t3796_MaxTemperatur_Varde;
    @JsonProperty("T3797_MinTemperatur_Varde")
    public double t3797_MinTemperatur_Varde;
}
