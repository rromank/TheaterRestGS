package com.epam.theater.common;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;

import java.io.Serializable;

@SpaceClass
public class Ticket implements Serializable {

    private String id;
    private Movie movie;
    private int seatNumber;

    public Ticket() {}

    public Ticket(Movie movie, int seatNumber) {
        this.movie = movie;
        this.seatNumber = seatNumber;
    }

    @SpaceId(autoGenerate = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @SpaceProperty(nullValue = "0")
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "movie=" + movie +
                ", seatNumber=" + seatNumber +
                '}';
    }

}