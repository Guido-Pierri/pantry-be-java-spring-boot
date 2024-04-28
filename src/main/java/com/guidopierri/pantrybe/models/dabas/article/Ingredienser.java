package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Ingredienser{
    @JsonProperty("Beskrivning") 
    public String beskrivning;
    @JsonProperty("Sekvens") 
    public int sekvens;
    @JsonProperty("Ursprungsland") 
    public Object ursprungsland;
    @JsonProperty("Andel") 
    public int andel;
    @JsonProperty("Fangstzoner") 
    public ArrayList<Object> fangstzoner;
    @JsonProperty("AnimaliskUrsprungslander") 
    public ArrayList<Object> animaliskUrsprungslander;
    @JsonProperty("Ravarutyper") 
    public Object ravarutyper;
    @JsonProperty("Ursprung") 
    public ArrayList<Ursprung> ursprung;
}
