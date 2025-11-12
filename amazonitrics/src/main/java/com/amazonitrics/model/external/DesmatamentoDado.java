package com.amazonitrics.model.external;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "desmatamento_dado")
public class DesmatamentoDado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;
    private int month;
    private double area;
    private String uf;
    private String className;
    private int numPol;

    public DesmatamentoDado() {
    }

    public DesmatamentoDado(String year, int month, double area, String uf, String className, int numPol) {
        this.year = year;
        this.month = month;
        this.area = area;
        this.uf = uf;
        this.className = className;
        this.numPol = numPol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getNumPol() {
        return numPol;
    }

    public void setNumPol(int numPol) {
        this.numPol = numPol;
    }
}