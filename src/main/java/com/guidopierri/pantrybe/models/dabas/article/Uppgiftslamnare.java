package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Uppgiftslamnare{
    @JsonProperty("ULIDENT") 
    public int uLIDENT;
    @JsonProperty("Foretagsnamn") 
    public String foretagsnamn;
    @JsonProperty("Orgnr") 
    public String orgnr;
    @JsonProperty("GLN") 
    public String gLN;
    @JsonProperty("Tel") 
    public String tel;
    @JsonProperty("Fax") 
    public String fax;
    @JsonProperty("PostAdr") 
    public String postAdr;
    @JsonProperty("PostNr") 
    public String postNr;
    @JsonProperty("PostOrt") 
    public String postOrt;
    @JsonProperty("Email") 
    public String email;
    @JsonProperty("WebPage") 
    public String webPage;
    @JsonProperty("LogoStor") 
    public String logoStor;
    @JsonProperty("LogoLiten") 
    public String logoLiten;
    @JsonProperty("MediaPath") 
    public String mediaPath;
    @JsonProperty("NewsroomAktiv") 
    public boolean newsroomAktiv;
}
