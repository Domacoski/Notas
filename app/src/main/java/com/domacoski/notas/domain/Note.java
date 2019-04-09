package com.domacoski.notas.domain;

public class Note {

    private Long id;
    private Long timestamp;
    private String title;
    private String description;


    public Note(){
        this.id = -1l;
        this.timestamp = 0l;
        this.title = "";
        this.description = "";
    }

    public Note(final Long id, final Long timestamp, final String title, final String description){
        this.id = id;
        this.timestamp = timestamp;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInvalid(){
        return (-1l == id);
    }
}
