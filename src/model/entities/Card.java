package model.entities;

import java.util.Date;

public class Card {
   
    private Integer id;
    private Date date;
    private Double value;

    public Card(Integer id, Date date, Double value) {
        this.id = id;
        this.date = date;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
