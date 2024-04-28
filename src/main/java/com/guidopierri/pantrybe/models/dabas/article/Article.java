package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article{
    @JsonProperty("Artikelbeskrivning") 
    public String artikelbeskrivning;
    @JsonProperty("Artikelegenskap") 
    public Object artikelegenskap;
    @JsonProperty("Produktkod") 
    public String produktkod;
    @JsonProperty("OvrigRegulatoriskMarkning") 
    public String ovrigRegulatoriskMarkning;
    @JsonProperty("MaximaltAntalEnheterIforpackningen") 
    public int maximaltAntalEnheterIforpackningen;
    @JsonProperty("Hyllkantstext") 
    public String hyllkantstext;
    @JsonProperty("Storlek") 
    public String storlek;
    @JsonProperty("TullstatistisktNummer") 
    public Object tullstatistisktNummer;
    @JsonProperty("Datumstandard") 
    public String datumstandard;
    @JsonProperty("Importklassificeringstyp") 
    public Object importklassificeringstyp;
    @JsonProperty("ImportklassificeringstypText") 
    public Object importklassificeringstypText;
    @JsonProperty("MangdFardigVara") 
    public int mangdFardigVara;
    @JsonProperty("MangdFardigVara_Formatted") 
    public String mangdFardigVara_Formatted;
    @JsonProperty("MangdFardigVaraEnhet") 
    public String mangdFardigVaraEnhet;
    @JsonProperty("MangdFardigVaraEnhetKod")
    public String mangdFardigVaraEnhetKod;
    @JsonProperty("MangdFardigVaraAvserKod")
    public String mangdFardigVaraAvserKod;
    @JsonProperty("Bruttovikt_Formatted")
    public String bruttovikt_Formatted;
    @JsonProperty("MangdPris") 
    public int mangdPris;
    @JsonProperty("MangdFardigVaraAvser") 
    public String mangdFardigVaraAvser;
    @JsonProperty("MinstaAntalEnheterIforpackningen") 
    public int minstaAntalEnheterIforpackningen;
    @JsonProperty("MinstaEnhetViktVolym") 
    public int minstaEnhetViktVolym;
    @JsonProperty("MinstaEnhetSort") 
    public Object minstaEnhetSort;
    @JsonProperty("Hemsida") 
    public Object hemsida;
    @JsonProperty("Nettovikt") 
    public int nettovikt;
    @JsonProperty("Nettovikt_Formatted") 
    public String nettovikt_Formatted;
    @JsonProperty("Nettovolym") 
    public int nettovolym;
    @JsonProperty("Nettovolym_Formatted") 
    public String nettovolym_Formatted;
    @JsonProperty("AvgiftErlagdForForpackningsmaterial") 
    public boolean avgiftErlagdForForpackningsmaterial;
    @JsonProperty("Skattesats") 
    public int skattesats;
    @JsonProperty("Skattesats_Formated") 
    public String skattesats_Formated;
    @JsonProperty("SkattetypKod") 
    public String skattetypKod;
    @JsonProperty("SkattetypText") 
    public String skattetypText;
    @JsonProperty("MinstaEnhetCirkaViktVolym") 
    public boolean minstaEnhetCirkaViktVolym;
    @JsonProperty("Alkoholhalt") 
    public int alkoholhalt;
    @JsonProperty("T4203_Argang") 
    public int t4203_Argang;
    @JsonProperty("T4271_SockerhaltAlkohol") 
    public Object t4271_SockerhaltAlkohol;
    @JsonProperty("Staplingshojd") 
    public int staplingshojd;
    @JsonProperty("NordisktVarunummerLakemedel") 
    public int nordisktVarunummerLakemedel;
    @JsonProperty("KravObrutenKylkedja") 
    public boolean kravObrutenKylkedja;
    @JsonProperty("HanteringsInstruktionText") 
    public String hanteringsInstruktionText;
    @JsonProperty("HanteringsInstruktionKod") 
    public String hanteringsInstruktionKod;
    @JsonProperty("Faktortyp") 
    public int faktortyp;
    @JsonProperty("FaktortypText") 
    public String faktortypText;
    @JsonProperty("Faktor") 
    public int faktor;
    @JsonProperty("PLUNummer") 
    public int pLUNummer;
    @JsonProperty("T4202_LokaltUrsprung") 
    public String t4202_LokaltUrsprung;
    @JsonProperty("Ursprungsland") 
    public Object ursprungsland;
    @JsonProperty("RelativLuftfuktighetMin") 
    public Object relativLuftfuktighetMin;
    @JsonProperty("TemperaturMax") 
    public int temperaturMax;
    @JsonProperty("TemperaturMax_Formatted") 
    public String temperaturMax_Formatted;
    @JsonProperty("TemperaturMin") 
    public int temperaturMin;
    @JsonProperty("TemperaturMin_Formatted") 
    public String temperaturMin_Formatted;
    @JsonProperty("RelativLuftfuktighetMax") 
    public Object relativLuftfuktighetMax;
    @JsonProperty("TotalHallbarhetAntalDagar") 
    public int totalHallbarhetAntalDagar;
    @JsonProperty("OpenJar") 
    public int openJar;
    @JsonProperty("OpenJar_Formatted") 
    public String openJar_Formatted;
    @JsonProperty("OpenJarTidKod") 
    public int openJarTidKod;
    @JsonProperty("InformationOvrig") 
    public Object informationOvrig;
    @JsonProperty("Emballagevikt") 
    public int emballagevikt;
    @JsonProperty("Allergenpastaende") 
    public Object allergenpastaende;
    @JsonProperty("Kubikmeter") 
    public int kubikmeter;
    @JsonProperty("Kubikmeter_Formatted") 
    public String kubikmeter_Formatted;
    @JsonProperty("Kvadratmeter") 
    public int kvadratmeter;
    @JsonProperty("Kvadratmeter_Formatted") 
    public String kvadratmeter_Formatted;
    @JsonProperty("Langd") 
    public int langd;
    @JsonProperty("Langd_Formatted") 
    public String langd_Formatted;
    @JsonProperty("AntalPortioner") 
    public String antalPortioner;
    @JsonProperty("Ingrediensforteckning") 
    public String ingrediensforteckning;
    @JsonProperty("ArtikelEJAvseddForFoodservicemarknaden") 
    public boolean artikelEJAvseddForFoodservicemarknaden;
    @JsonProperty("Druvsort") 
    public int druvsort;
    @JsonProperty("FarligarDelarBorttagbaraKod") 
    public Object farligarDelarBorttagbaraKod;
    @JsonProperty("FarligarDelarBorttagbara") 
    public Object farligarDelarBorttagbara;
    @JsonProperty("UppfyllerRoHS_Kod") 
    public Object uppfyllerRoHS_Kod;
    @JsonProperty("UppfyllerRoHS") 
    public Object uppfyllerRoHS;
    @JsonProperty("Plattformstyp") 
    public Object plattformstyp;
    @JsonProperty("T0157_Staplingsbarhet") 
    public int t0157_Staplingsbarhet;
    @JsonProperty("Pris") 
    public int pris;
    @JsonProperty("ValutaPaPriset") 
    public Object valutaPaPriset;
    @JsonProperty("InnehallerEjAllergener") 
    public boolean innehallerEjAllergener;
    @JsonProperty("IngrediensforteckningIckeLivsmedel") 
    public String ingrediensforteckningIckeLivsmedel;
    @JsonProperty("OvrigMarkning") 
    public Object ovrigMarkning;
    @JsonProperty("Halsopastaende") 
    public Object halsopastaende;
    @JsonProperty("Malmarknadsomrade") 
    public int malmarknadsomrade;
    @JsonProperty("MalmarknadsomradeText") 
    public String malmarknadsomradeText;
    @JsonProperty("Sillvikt") 
    public int sillvikt;
    @JsonProperty("BatterierIngar") 
    public boolean batterierIngar;
    @JsonProperty("BatterierKravs") 
    public boolean batterierKravs;
    @JsonProperty("FinnsInbyggdaBatterierKod") 
    public Object finnsInbyggdaBatterierKod;
    @JsonProperty("FinnsInbyggdaBatterier") 
    public Object finnsInbyggdaBatterier;
    @JsonProperty("BatteriteknikKod") 
    public Object batteriteknikKod;
    @JsonProperty("Batteriteknik") 
    public Object batteriteknik;
    @JsonProperty("BatteritypKod") 
    public Object batteritypKod;
    @JsonProperty("Batterityp") 
    public Object batterityp;
    @JsonProperty("BatterierKravAntal") 
    public Object batterierKravAntal;
    @JsonProperty("T4750_AntalInbyggdaBatterier") 
    public Object t4750_AntalInbyggdaBatterier;
    @JsonProperty("Batterivikt") 
    public Object batterivikt;
    @JsonProperty("Batterivikt_Formatted") 
    public String batterivikt_Formatted;
    @JsonProperty("T4273_InnehallerLatex") 
    public Object t4273_InnehallerLatex;
    @JsonProperty("T4373_TypAvRecept_Kod") 
    public Object t4373_TypAvRecept_Kod;
    @JsonProperty("T4373_TypAvRecept") 
    public Object t4373_TypAvRecept;
    @JsonProperty("T4274_MriKompatibilitet") 
    public Object t4274_MriKompatibilitet;
    @JsonProperty("T4275_ManufacturerSterilisation") 
    public Object t4275_ManufacturerSterilisation;
    @JsonProperty("T4275_ManufacturerSterilisation_Text") 
    public Object t4275_ManufacturerSterilisation_Text;
    @JsonProperty("T4204_SökbegreppEhandel") 
    public String t4204_SökbegreppEhandel;
    @JsonProperty("T4228_ArtensVetenskapligaNamnKod") 
    public String t4228_ArtensVetenskapligaNamnKod;
    @JsonProperty("T4229_ArtensVetenskapligaNamn") 
    public Object t4229_ArtensVetenskapligaNamn;
    @JsonProperty("T4231_Produktionsmetod") 
    public Object t4231_Produktionsmetod;
    @JsonProperty("T4232_Forvaringsstatus") 
    public Object t4232_Forvaringsstatus;
    @JsonProperty("T4242_Temperaturstatus") 
    public Object t4242_Temperaturstatus;
    @JsonProperty("T4245_PresentationsformKod") 
    public Object t4245_PresentationsformKod;
    @JsonProperty("T4246_BeredningsformKod") 
    public Object t4246_BeredningsformKod;
    @JsonProperty("T3816_DimensionTypeCode")
    public Object t3816_DimensionTypeCode;
    @JsonProperty("Hyllbredd") 
    public Object hyllbredd;
    @JsonProperty("Hylldjup") 
    public Object hylldjup;
    @JsonProperty("Hyllhojd") 
    public Object hyllhojd;
    @JsonProperty("Varugrupp") 
    public Varugrupp varugrupp;
    @JsonProperty("Diameter") 
    public int diameter;
    @JsonProperty("Diameter_Formatted") 
    public String diameter_Formatted;
    @JsonProperty("Storleksmått") 
    public Object storleksmått;
    @JsonProperty("Produktegenskaper") 
    public Object produktegenskaper;
    @JsonProperty("Kravspecifikationer") 
    public Object kravspecifikationer;
    @JsonProperty("T4305_BeskrivningNettoinnehall") 
    public Object t4305_BeskrivningNettoinnehall;
    @JsonProperty("T4307_SockelTyp") 
    public String t4307_SockelTyp;
    @JsonProperty("T4291_TypAvLampaText") 
    public Object t4291_TypAvLampaText;
    @JsonProperty("T4291_TypAvLampaKod") 
    public Object t4291_TypAvLampaKod;
    @JsonProperty("T4311_PastaendeMarkningPaForpackning") 
    public Object t4311_PastaendeMarkningPaForpackning;
    @JsonProperty("T4311_PastaendeMarkningPaForpackningText") 
    public Object t4311_PastaendeMarkningPaForpackningText;
    @JsonProperty("T4311_PastaendeMarkningPaForpackningKod") 
    public Object t4311_PastaendeMarkningPaForpackningKod;
    @JsonProperty("T4313_TypAvPastaendeText") 
    public Object t4313_TypAvPastaendeText;
    @JsonProperty("T4313_TypAvPastaendeKod") 
    public Object t4313_TypAvPastaendeKod;
    @JsonProperty("T4314_AmnePastaendeText") 
    public Object t4314_AmnePastaendeText;
    @JsonProperty("T4314_AmnePastaendeKod") 
    public Object t4314_AmnePastaendeKod;
    @JsonProperty("T4292_FargTemperatur") 
    public Object t4292_FargTemperatur;
    @JsonProperty("T4292_FargTemperatur_Formatted") 
    public String t4292_FargTemperatur_Formatted;
    @JsonProperty("T4292_FargTemperaturEnhetKod") 
    public Object t4292_FargTemperaturEnhetKod;
    @JsonProperty("T4292_FargTemperaturEnhetText") 
    public Object t4292_FargTemperaturEnhetText;
    @JsonProperty("T4293_LjusFlode") 
    public Object t4293_LjusFlode;
    @JsonProperty("T4293_LjusFlode_Formatted") 
    public String t4293_LjusFlode_Formatted;
    @JsonProperty("T4293_LjusFlodeEnhet") 
    public Object t4293_LjusFlodeEnhet;
    @JsonProperty("T4293_LjusFlodeEnhetKod") 
    public Object t4293_LjusFlodeEnhetKod;
    @JsonProperty("Batchnummer") 
    public boolean batchnummer;
    @JsonProperty("ARIDENT") 
    public int aRIDENT;
    @JsonProperty("ULIDENT") 
    public int uLIDENT;
    @JsonProperty("GTIN") 
    public String gTIN;
    @JsonProperty("TillverkarensArtikelnummer") 
    public Object tillverkarensArtikelnummer;
    @JsonProperty("Artikelbenamning") 
    public String artikelbenamning;
    @JsonProperty("Artikelkategori") 
    public String artikelkategori;
    @JsonProperty("Produktnamn") 
    public String produktnamn;
    @JsonProperty("RegleratProduktnamn") 
    public String regleratProduktnamn;
    @JsonProperty("Forvaringsinstruktion") 
    public String forvaringsinstruktion;
    @JsonProperty("Variabelmattsindikator") 
    public boolean variabelmattsindikator;
    @JsonProperty("Bruttovikt") 
    public int bruttovikt;
    @JsonProperty("Bredd") 
    public double bredd;
    @JsonProperty("Djup") 
    public int djup;
    @JsonProperty("Hojd") 
    public int hojd;
    @JsonProperty("Returemballage") 
    public boolean returemballage;
    @JsonProperty("FarligtGodsKod") 
    public Object farligtGodsKod;
    @JsonProperty("FarligtGodsKlassKod") 
    public Object farligtGodsKlassKod;
    @JsonProperty("FarligtGodsKlass") 
    public Object farligtGodsKlass;
    @JsonProperty("FarligtGodsForpackningsgruppKod") 
    public Object farligtGodsForpackningsgruppKod;
    @JsonProperty("FarligtGodsForpackningsgrupp") 
    public Object farligtGodsForpackningsgrupp;
    @JsonProperty("GPCKod") 
    public String gPCKod;
    @JsonProperty("GiltigFROM") 
    public Date giltigFROM;
    @JsonProperty("FakturerbarEnhet") 
    public boolean fakturerbarEnhet;
    @JsonProperty("LogiskEnhet") 
    public boolean logiskEnhet;
    @JsonProperty("Slutdatum") 
    public Object slutdatum;
    @JsonProperty("GiltighetsdatumPris") 
    public Object giltighetsdatumPris;
    @JsonProperty("Tillganglighetstidpunkt") 
    public Date tillganglighetstidpunkt;
    @JsonProperty("SistaTillganglighetstidpunkt") 
    public Object sistaTillganglighetstidpunkt;
    @JsonProperty("SkapadDatum") 
    public Date skapadDatum;
    @JsonProperty("SenastAndradDatum") 
    public Date senastAndradDatum;
    @JsonProperty("Flampunkt") 
    public Object flampunkt;
    @JsonProperty("Flampunkt_Formatted") 
    public String flampunkt_Formatted;
    @JsonProperty("KodBegransadMangd") 
    public Object kodBegransadMangd;
    @JsonProperty("OfficiellTransportbenamning") 
    public String officiellTransportbenamning;
    @JsonProperty("OspecificeradTransportbenamning") 
    public String ospecificeradTransportbenamning;
    @JsonProperty("T4303_NettoviktFarligtGods") 
    public Object t4303_NettoviktFarligtGods;
    @JsonProperty("T4303_NettoviktFarligtGods_Formatted") 
    public String t4303_NettoviktFarligtGods_Formatted;
    @JsonProperty("T4303_NettoviktFarligtGodsEnhetKod") 
    public Object t4303_NettoviktFarligtGodsEnhetKod;
    @JsonProperty("T4303_NettoviktFarligtGodsEnhetText") 
    public Object t4303_NettoviktFarligtGodsEnhetText;
    @JsonProperty("T4306_Atervinningsinstruktioner") 
    public Object t4306_Atervinningsinstruktioner;
    @JsonProperty("TunnelrestriktionADR") 
    public String tunnelrestriktionADR;
    @JsonProperty("KlassificeringskodFarligtgods") 
    public String klassificeringskodFarligtgods;
    @JsonProperty("Transportkategori") 
    public Object transportkategori;
    @JsonProperty("Konsumentartikel") 
    public boolean konsumentartikel;
    @JsonProperty("BestallningsbarForpackning") 
    public boolean bestallningsbarForpackning;
    @JsonProperty("Garantiloptid") 
    public Object garantiloptid;
    @JsonProperty("GarantiloptidEnhet") 
    public Object garantiloptidEnhet;
    @JsonProperty("Lanseringstidpunkt") 
    public Object lanseringstidpunkt;
    @JsonProperty("Sasongsindikator") 
    public Object sasongsindikator;
    @JsonProperty("AntalReturnerbaraEnheter") 
    public int antalReturnerbaraEnheter;
    @JsonProperty("Staplingsriktning") 
    public Object staplingsriktning;
    @JsonProperty("MaxTransportTemperatur") 
    public Object maxTransportTemperatur;
    @JsonProperty("MaxTransportTemperatur_Formatted") 
    public String maxTransportTemperatur_Formatted;
    @JsonProperty("MinTransportTemperatur") 
    public Object minTransportTemperatur;
    @JsonProperty("MinTransportTemperatur_Formatted") 
    public String minTransportTemperatur_Formatted;
    @JsonProperty("Anvandningsinstruktioner") 
    public Object anvandningsinstruktioner;
    @JsonProperty("HallbarhetEfterOppning") 
    public int hallbarhetEfterOppning;
    @JsonProperty("FarligtGodsBegransadMangd") 
    public Object farligtGodsBegransadMangd;
    @JsonProperty("FarligtGodsOvrigInfo") 
    public String farligtGodsOvrigInfo;
    @JsonProperty("FarligtGodsSarbestammelser") 
    public String farligtGodsSarbestammelser;
    @JsonProperty("T3495_Artikelavisering") 
    public Object t3495_Artikelavisering;
    @JsonProperty("T4032_TypAvDatumPaForpackningen") 
    public String t4032_TypAvDatumPaForpackningen;
    @JsonProperty("T4032_TypAvDatumPaForpackningen_Text") 
    public String t4032_TypAvDatumPaForpackningen_Text;
    @JsonProperty("T4311_PastaendeMarkningPaForpackning_Text") 
    public Object t4311_PastaendeMarkningPaForpackning_Text;
    @JsonProperty("T3742_ForstaLeveransdatum") 
    public Object t3742_ForstaLeveransdatum;
    @JsonProperty("Undervarumarke") 
    public String undervarumarke;
    @JsonProperty("Niva") 
    public String niva;
    @JsonProperty("Produktbladslank") 
    public String produktbladslank;
    @JsonProperty("KompletterandeProduktklass") 
    public Object kompletterandeProduktklass;
    @JsonProperty("T4200_AllmänPubliceringstidpunkt") 
    public Object t4200_AllmänPubliceringstidpunkt;
    @JsonProperty("T3848_TypAvStaplingsbarhet") 
    public Object t3848_TypAvStaplingsbarhet;
    @JsonProperty("Varningsetiketter") 
    public Object varningsetiketter;
    @JsonProperty("Sasongskoder") 
    public ArrayList<Object> sasongskoder;
    @JsonProperty("MaskinellMarkningar") 
    public ArrayList<MaskinellMarkningar> maskinellMarkningar;
    @JsonProperty("MediaFiler") 
    public ArrayList<MediaFiler> mediaFiler;
    @JsonProperty("Bilder") 
    public ArrayList<Bilder> bilder;
    @JsonProperty("ReferenserTillAndraArtiklar") 
    public ArrayList<Object> referenserTillAndraArtiklar;
    @JsonProperty("MSRKritierier") 
    public ArrayList<MSRKritierier> mSRKritierier;
    @JsonProperty("Receptlinks") 
    public ArrayList<Object> receptlinks;
    @JsonProperty("Allergener") 
    public ArrayList<Allergener> allergener;
    @JsonProperty("Markningar") 
    public Object markningar;
    @JsonProperty("Ingredienser") 
    public ArrayList<Ingredienser> ingredienser;
    @JsonProperty("Tillagningsinformation") 
    public Object tillagningsinformation;
    @JsonProperty("Tillverkningslander") 
    public ArrayList<Tillverkningslander> tillverkningslander;
    @JsonProperty("Naringsinfo") 
    public ArrayList<Object> naringsinfo;
    @JsonProperty("Serveringsforslag") 
    public Object serveringsforslag;
    @JsonProperty("Diettyper") 
    public Object diettyper;
    @JsonProperty("KompletterandeArtikelId") 
    public ArrayList<Object> kompletterandeArtikelId;
    @JsonProperty("Parter") 
    public ArrayList<Object> parter;
    @JsonProperty("Malgruppskriterium") 
    public ArrayList<Object> malgruppskriterium;
    @JsonProperty("Tillagningsmetoder") 
    public ArrayList<Object> tillagningsmetoder;
    @JsonProperty("Farger") 
    public Object farger;
    @JsonProperty("VillkorForsaljning") 
    public Object villkorForsaljning;
    @JsonProperty("Varumarke") 
    public Varumarke varumarke;
    @JsonProperty("Nettoinnehall") 
    public ArrayList<Nettoinnehall> nettoinnehall;
    @JsonProperty("Kontakter") 
    public ArrayList<Object> kontakter;
    @JsonProperty("Faroangivelser") 
    public Object faroangivelser;
    @JsonProperty("Sakerhet") 
    public ArrayList<Object> sakerhet;
    @JsonProperty("Forpackningar") 
    public ArrayList<Forpackningar> forpackningar;
    @JsonProperty("Substanser") 
    public ArrayList<Object> substanser;
    @JsonProperty("Fangstzoner") 
    public ArrayList<Object> fangstzoner;
    @JsonProperty("Uppgiftslamnare") 
    public Uppgiftslamnare uppgiftslamnare;
    @JsonProperty("Skyddsangivelse") 
    public Object skyddsangivelse;
    @JsonProperty("Fangstredskap") 
    public ArrayList<Object> fangstredskap;
    @JsonProperty("Marknadsbudskap") 
    public ArrayList<Marknadsbudskap> marknadsbudskap;
    @JsonProperty("KortMarknadsbudskap") 
    public Object kortMarknadsbudskap;
    @JsonProperty("Variantbeskrivning") 
    public Object variantbeskrivning;
    @JsonProperty("ProduktInformation") 
    public Object produktInformation;
    @JsonProperty("Komponenter") 
    public ArrayList<Object> komponenter;
    @JsonProperty("Ölbeska") 
    public Object ölbeska;
    @JsonProperty("AlkoholLagringstid") 
    public Object alkoholLagringstid;
    @JsonProperty("AlkoholhaltigDryckDestilleradFrån") 
    public Object alkoholhaltigDryckDestilleradFrån;
    @JsonProperty("TypAvFiltreringsMetodFörAlkoholhaltigDryck") 
    public Object typAvFiltreringsMetodFörAlkoholhaltigDryck;
    @JsonProperty("TypAvProduktionsmetodFörAlkoholhaltigDryck") 
    public Object typAvProduktionsmetodFörAlkoholhaltigDryck;
    @JsonProperty("PrimärTillsattArom") 
    public Object primärTillsattArom;
    @JsonProperty("SekundärTillsattArom") 
    public Object sekundärTillsattArom;
    @JsonProperty("SötmanivåPåAlkoholhaltigDryck") 
    public Object sötmanivåPåAlkoholhaltigDryck;
    @JsonProperty("VinetsUrsprung") 
    public Object vinetsUrsprung;
    @JsonProperty("KlassificeringAvOrganismer") 
    public Object klassificeringAvOrganismer;
    @JsonProperty("TypAvKvalitet") 
    public Object typAvKvalitet;
    @JsonProperty("T4330_Nettovikt") 
    public Object t4330_Nettovikt;
    @JsonProperty("T4331_Kön") 
    public Object t4331_Kön;
    @JsonProperty("Engångsköp") 
    public Object engångsköp;
    @JsonProperty("Påstående") 
    public Object påstående;
    @JsonProperty("Modules") 
    public ArrayList<Module> modules;
    @JsonProperty("MinstaEnhetKod")
    public String minstaEnhetKod;
    @JsonProperty("ForvaringTemperaturMin")
    public int forvaringTemperaturMin;
    @JsonProperty("ForvaringTemperaturMin_Formatted")
    public String forvaringTemperaturMin_Formatted;
    @JsonProperty("ForvaringTemperaturMax")
    public double forvaringTemperaturMax;
    @JsonProperty("ForvaringTemperaturMax_Formatted")
    public String forvaringTemperaturMax_Formatted;
    @JsonProperty("T4242_TemperaturstatusKod")
    public String t4242_TemperaturstatusKod;
    @JsonProperty("Bredd_Formatted")
    public String bredd_Formatted;
    @JsonProperty("Djup_Formatted")
    public String djup_Formatted;
    @JsonProperty("Hojd_Formatted")
    public String hojd_Formatted;
    @JsonProperty("Temperaturinformation")
    public ArrayList<Temperaturinformation> temperaturinformation;
    @JsonProperty("OpenJarTidText")
    public String openJarTidText;
    @JsonProperty("T3816_DimensionTypeCode_Text")
    public String t3816_DimensionTypeCode_Text;
    @JsonProperty("Hyllbredd_Formatted")
    public String hyllbredd_Formatted;
    @JsonProperty("Hylldjup_Formatted")
    public String hylldjup_Formatted;
    @JsonProperty("Hyllhojd_Formatted")
    public String hyllhojd_Formatted;
    @JsonProperty("T3848_TypAvStaplingsbarhet_Kod")
    public String t3848_TypAvStaplingsbarhet_Kod;
    @JsonProperty("Hanteringsinstruktioner")
    public List<String> Hanteringsinstruktioner;
/*
    public ArrayList<Object> hanteringsinstruktioner;
*/
    @JsonProperty("Stacking")
    public ArrayList<Object> stacking;
    @JsonProperty("Fuktighetsangivelser")
    public ArrayList<Object> fuktighetsangivelser;
}
