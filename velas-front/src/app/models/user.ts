export interface UserLoginDto{
    username:string;
    password:string;
}

export interface UserRequestDto{
    firstName:string;
    lastName:string;
    username:string;
    email:string;
    password:string;
}

export interface Token{
    token:string;
}