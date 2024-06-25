export interface CandleResponseDto {
    id:             number;
    name:           string;
    description:    string;
    principalImage: string;
    stock:          number;
    category:       Category|null;
    images:         string[];
    price:          number;
}

export interface CandleRequestDto {
    name: string;
    description: string;
    principalImage: string;
    stock: number;
    category: Category|null;
    images: string[];
    ingredients?: IngredientRequestDto[];
  }

export enum Category {
    AROMATIC = 'AROMATIC',
    CANNABIS = 'CANNABIS',
    HERBAL = 'HERBAL',
    OIL_LAMP = 'OIL_LAMP',
    CLASSIC = 'CLASSIC'
}

export interface IngredientRequestDto {
    name: string;
    amount: number;
}

  
