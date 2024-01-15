
package com.guidopierri.pantrybe.models.dabas.search;

import com.fasterxml.jackson.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Forpackningsstorlek",
        "Varumarke",
        "GTIN",
        "Artikelbenamning",
        "TillverkarensArtikelnummer",
        "Hyllkantstext",
        "Artikeltyp",
        "SkapadDatum",
        "SenastAndradDatum"
})
@Generated("jsonschema2pojo")
public class Search {

    @JsonProperty("Forpackningsstorlek")
    private String forpackningsstorlek;
    @JsonProperty("Varumarke")
    private String varumarke;
    @JsonProperty("GTIN")
    private String gtin;
    @JsonProperty("Artikelbenamning")
    private String artikelbenamning;
    @JsonProperty("TillverkarensArtikelnummer")
    private String tillverkarensArtikelnummer;
    @JsonProperty("Hyllkantstext")
    private String hyllkantstext;
    @JsonProperty("Artikeltyp")
    private String artikeltyp;
    @JsonProperty("SkapadDatum")
    private String skapadDatum;
    @JsonProperty("SenastAndradDatum")
    private String senastAndradDatum;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("Forpackningsstorlek")
    public String getForpackningsstorlek() {
        return forpackningsstorlek;
    }

    @JsonProperty("Forpackningsstorlek")
    public void setForpackningsstorlek(String forpackningsstorlek) {
        this.forpackningsstorlek = forpackningsstorlek;
    }

    @JsonProperty("Varumarke")
    public String getVarumarke() {
        return varumarke;
    }

    @JsonProperty("Varumarke")
    public void setVarumarke(String varumarke) {
        this.varumarke = varumarke;
    }

    @JsonProperty("GTIN")
    public String getGtin() {
        return gtin;
    }

    @JsonProperty("GTIN")
    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    @JsonProperty("Artikelbenamning")
    public String getArtikelbenamning() {
        return artikelbenamning;
    }

    @JsonProperty("Artikelbenamning")
    public void setArtikelbenamning(String artikelbenamning) {
        this.artikelbenamning = artikelbenamning;
    }

    @JsonProperty("TillverkarensArtikelnummer")
    public String getTillverkarensArtikelnummer() {
        return tillverkarensArtikelnummer;
    }

    @JsonProperty("TillverkarensArtikelnummer")
    public void setTillverkarensArtikelnummer(String tillverkarensArtikelnummer) {
        this.tillverkarensArtikelnummer = tillverkarensArtikelnummer;
    }

    @JsonProperty("Hyllkantstext")
    public String getHyllkantstext() {
        return hyllkantstext;
    }

    @JsonProperty("Hyllkantstext")
    public void setHyllkantstext(String hyllkantstext) {
        this.hyllkantstext = hyllkantstext;
    }

    @JsonProperty("Artikeltyp")
    public String getArtikeltyp() {
        return artikeltyp;
    }

    @JsonProperty("Artikeltyp")
    public void setArtikeltyp(String artikeltyp) {
        this.artikeltyp = artikeltyp;
    }

    @JsonProperty("SkapadDatum")
    public String getSkapadDatum() {
        return skapadDatum;
    }

    @JsonProperty("SkapadDatum")
    public void setSkapadDatum(String skapadDatum) {
        this.skapadDatum = skapadDatum;
    }

    @JsonProperty("SenastAndradDatum")
    public String getSenastAndradDatum() {
        return senastAndradDatum;
    }

    @JsonProperty("SenastAndradDatum")
    public void setSenastAndradDatum(String senastAndradDatum) {
        this.senastAndradDatum = senastAndradDatum;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
