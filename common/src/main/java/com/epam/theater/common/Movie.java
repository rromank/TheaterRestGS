package com.epam.theater.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import org.openspaces.remoting.Routing;

import java.io.Serializable;
import java.util.Date;

@SpaceClass
public class Movie implements Serializable {

    private int id;
    private String title;
    private int totalSeats;
    private int freeSeats;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="dd.MM.yyyy HH:mm:ss")
    private Date date;

    public Movie() {}

    public Movie(String title, int totalSeats, int freeSeats, Date date) {
        this.title = title;
        this.totalSeats = totalSeats;
        this.freeSeats = freeSeats;
        this.date = date;
    }

    @SpaceId
    @SpaceProperty(nullValue = "0")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SpaceProperty(nullValue = "0")
    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @SpaceProperty(nullValue = "0")
    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", totalSeats=" + totalSeats +
                ", freeSeats=" + freeSeats +
                ", date=" + date +
                '}';
    }

}