package com.guidopierri.pantrybe.models.dabas.article;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Naringsinfo{
    @JsonProperty("Tillagningsstatus")
    public String tillagningsstatus;
    @JsonProperty("Intagningsrekommendationstyp")
    public Object intagningsrekommendationstyp;
    @JsonProperty("Portionsstorlek")
    public Object portionsstorlek;
    @JsonProperty("Basmangdsdeklaration")
    public double basmangdsdeklaration;
    @JsonProperty("Basmangdsdeklaration_Formatted")
    public String basmangdsdeklaration_Formatted;
    @JsonProperty("Mattkvalificerarebasmangd")
    public String mattkvalificerarebasmangd;
    @JsonProperty("Naringsvarden")
    public ArrayList<Naringsvarden> naringsvarden;
}
