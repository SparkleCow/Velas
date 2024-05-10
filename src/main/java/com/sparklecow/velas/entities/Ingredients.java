package com.sparklecow.velas.entities;

public enum Ingredients {
    BEESWAX(10000.0),
    SOY_WAX(15000.0),
    PARAFFIN_WAX(15000.0),
    ESSENTIAL_OIL(6000.0),
    CANDLE_DYE(4000.0),
    WICK(300.0),
    LABEL(500.0),
    STEARIN(7000.0),
    VYBAR(7000.0);

    public final Double pricePerUnit;

    Ingredients(Double pricePerUnit){
        this.pricePerUnit = pricePerUnit;
    }
}
