package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Allergener{
    @JsonProperty("Allergen") 
    public String allergen;
    @JsonProperty("Allergenkod") 
    public String allergenkod;
    @JsonProperty("Niva") 
    public String niva;
    @JsonProperty("Nivakod") 
    public String nivakod;
    @JsonProperty("NivakodText") 
    public String nivakodText;
}
