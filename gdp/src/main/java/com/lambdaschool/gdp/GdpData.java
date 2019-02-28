package com.lambdaschool.gdp;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity

public class GdpData {

    private @Id @GeneratedValue Long id;
    private String country;
    private int gdp;

    public GdpData() {
    }

    public GdpData(String country, int gdp) {
        this.country = country;
        this.gdp = gdp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getGdp() {
        return gdp;
    }

    public void setGdp(int gdp) {
        this.gdp = gdp;
    }
}
