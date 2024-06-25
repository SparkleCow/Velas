import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GaleriaComponent } from './galeria/galeria.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BannerComponent } from './banner/banner.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { RegisterComponent } from './register/register.component';
import { ProductComponent } from './product/product.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { CarritoComponent } from './carrito/carrito.component';
import { CandleFormComponent } from './candle-form/candle-form.component';
import { ActivateAccountComponent } from './activate-account/activate-account.component';

@NgModule({
  declarations: [
    AppComponent,
    GaleriaComponent,
    BannerComponent,
    LoginComponent,
    RegisterComponent,
    ProductComponent,
    CarritoComponent,
    CandleFormComponent,
    ActivateAccountComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, 
    FormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule {}

