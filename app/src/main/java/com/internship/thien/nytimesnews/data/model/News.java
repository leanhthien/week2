package com.internship.thien.nytimesnews.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class News {

    @SerializedName("web_url")
    @Expose
    String webUrl;
    @SerializedName("snippet")
    @Expose
    String snippet;
    @SerializedName("print_page")
    @Expose
    String printPage;
    @SerializedName("source")
    @Expose
    String source;
    @SerializedName("multimedia")
    @Expose
    List<Multimedium> multimedia = null;
    @SerializedName("headline")
    @Expose
    Headline headline;
    @SerializedName("keywords")
    @Expose
    List<Keyword> keywords = null;
    @SerializedName("pub_date")
    @Expose
    String pubDate;
    @SerializedName("document_type")
    @Expose
    String documentType;
    @SerializedName("news_desk")
    @Expose
    String newsDesk;
    @SerializedName("type_of_material")
    @Expose
    String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    String id;
    @SerializedName("word_count")
    @Expose
    Integer wordCount;
    @SerializedName("score")
    @Expose
    Double score;
    @SerializedName("section_name")
    @Expose
    String sectionName;

    public News() {}

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getPrintPage() {
        return printPage;
    }

    public void setPrintPage(String printPage) {
        this.printPage = printPage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

}