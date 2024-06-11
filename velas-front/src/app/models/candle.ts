export interface CandleResponseDto {
    id:             number;
    name:           string;
    description:    string;
    principalImage: string;
    stock:          number;
    category:       Category;
    images:         string[];
    price:          number;
}

export enum Category {
    AROMATIC = 'AROMATIC',
    CANNABIS = 'CANNABIS',
    HERBAL = 'HERBAL',
    OIL_LAMP = 'OIL_LAMP',
    CLASSIC = 'CLASSIC'
}