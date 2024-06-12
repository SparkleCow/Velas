export interface Carrito {
    id:         number;
    candles:    CandleElement[];
    totalPrice: number;
}

export interface CandleElement {
    id:          number;
    candle:      CandleCandle;
    quantity:    number;
    shoppingCar: null;
}

export interface CandleCandle {
    id:             number;
    name:           string;
    description:    string;
    principalImage: string;
    stock:          number;
    category:       string;
    images:         string[];
    ingredients:    Ingredient[];
    price:          number;
}

export interface Ingredient {
    id:           number;
    name:         string;
    amount:       number;
    pricePerUnit: number;
    price:        number;
}