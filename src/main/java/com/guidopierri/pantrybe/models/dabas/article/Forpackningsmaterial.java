package com.guidopierri.pantrybe.models.dabas.article;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class Forpackningsmaterial{
    @JsonProperty("T1188_ForpackningsmaterialKod") 
    public String t1188_ForpackningsmaterialKod;
    @JsonProperty("T1189_Vikt") 
    public int t1189_Vikt;
    @JsonProperty("T1189_Vikt_Formatted") 
    public String t1189_Vikt_Formatted;
    @JsonProperty("T4371_IsPackagingMaterialRecoverableText") 
    public Object t4371_IsPackagingMaterialRecoverableText;
    @JsonProperty("T4371_IsPackagingMaterialRecoverableCode") 
    public Object t4371_IsPackagingMaterialRecoverableCode;
    @JsonProperty("T3780_ViktEnhet") 
    public String t3780_ViktEnhet;
    @JsonProperty("T4243_ForpackningsmaterialetsFargKod") 
    public Object t4243_ForpackningsmaterialetsFargKod;
    public Object t4283_PackagingLabellingCoveragePercentage;
    public String t4283_PackagingLabellingCoveragePercentage_Formatted;
    @JsonProperty("T4284_PackagingLabellingTypeCode") 
    public Object t4284_PackagingLabellingTypeCode;
    public Object t4284_PackagingLabellingTypeCode_AATEXT;
    @JsonProperty("T4354_PackagingMaterialElementCode") 
    public Object t4354_PackagingMaterialElementCode;
    @JsonProperty("T4354_PackagingMaterialElementCodeText") 
    public Object t4354_PackagingMaterialElementCodeText;
    @JsonProperty("T4355_PackagingMaterialRecyclingSchemeCode") 
    public Object t4355_PackagingMaterialRecyclingSchemeCode;
    @JsonProperty("T4355_PackagingMaterialRecyclingSchemeCodeText") 
    public Object t4355_PackagingMaterialRecyclingSchemeCodeText;
    @JsonProperty("T4285_PackagingRawMaterialCode") 
    public Object t4285_PackagingRawMaterialCode;
    public Object t4285_PackagingRawMaterialCode__AATEXT;
    public Object t4286_PackagingRawMaterialContentPercentage;
    public String t4286_PackagingRawMaterialContentPercentage_Formatted;
}
