package com.guidopierri.pantrybe.models.dabas.article;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Naringsvarden {
    @JsonProperty("Benamning")
    public String benamning;
    @JsonProperty("Kod")
    public String kod;
    @JsonProperty("Mangd")
    public double mangd;
    @JsonProperty("Mangd_Formatted")
    public String mangd_Formatted;
    @JsonProperty("Enhet")
    public String enhet;
    @JsonProperty("Enhetskod")
    public String enhetskod;
    @JsonProperty("Precision")
    public String precision;
    @JsonProperty("Dagsintag")
    public double dagsintag;
    @JsonProperty("Dagsintag_Formatted")
    public String dagsintag_Formatted;
}
