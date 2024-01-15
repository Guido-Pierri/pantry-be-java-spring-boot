package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Tillverkningslander{
    @JsonProperty("Land") 
    public String land;
    @JsonProperty("Kod") 
    public String kod;
}
