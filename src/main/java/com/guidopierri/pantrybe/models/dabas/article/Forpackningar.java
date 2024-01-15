package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.guidopierri.pantrybe.models.dabas.article.Forpackningsmaterial;

import java.util.ArrayList;
import java.util.List;
public class Forpackningar{
    @JsonProperty("Forpackningstyp") 
    public String forpackningstyp;
    @JsonProperty("ForpackningsmaterialVillkorKod") 
    public boolean forpackningsmaterialVillkorKod;
    @JsonProperty("Plattformstyp") 
    public Object plattformstyp;
    @JsonProperty("Plattformsvikt") 
    public int plattformsvikt;
    @JsonProperty("Plattformsvikt_Formatted") 
    public String plattformsvikt_Formatted;
    @JsonProperty("T4201_plattformshojd_mm") 
    public Object t4201_plattformshojd_mm;
    @JsonProperty("EmballageViktEnhet") 
    public String emballageViktEnhet;
    @JsonProperty("Forpakningsfunktion") 
    public Object forpakningsfunktion;
    @JsonProperty("T4372_PackagingSustainabilityFeatureText") 
    public Object t4372_PackagingSustainabilityFeatureText;
    @JsonProperty("T4372_PackagingSustainabilityFeatureCode") 
    public Object t4372_PackagingSustainabilityFeatureCode;
    @JsonProperty("ForpackningsmaterialKod") 
    public Object forpackningsmaterialKod;
    @JsonProperty("Vikt") 
    public Object vikt;
    @JsonProperty("Viktenhet") 
    public Object viktenhet;
    @JsonProperty("PantForReturnerbarForpackningKod") 
    public Object pantForReturnerbarForpackningKod;
    @JsonProperty("Antalenheter") 
    public Object antalenheter;
    @JsonProperty("AntalenhetKod") 
    public String antalenhetKod;
    @JsonProperty("Pantsystemregion") 
    public Object pantsystemregion;
    @JsonProperty("Forpackningsegenskap") 
    public Object forpackningsegenskap;
    @JsonProperty("Forpackningsmaterial") 
    public ArrayList<Forpackningsmaterial> forpackningsmaterial;
}
