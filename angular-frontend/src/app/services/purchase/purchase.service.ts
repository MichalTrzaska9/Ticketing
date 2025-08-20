import { Injectable } from '@angular/core';
import {Event} from "../../model/event";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Purchase} from "../../model/purchase";

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  constructor(private httpClient: HttpClient) {
  }

  purchaseEvent(purchase: Purchase): Observable<string> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text' as 'json'
    };
    return this.httpClient.post<string>('http://localhost:9000/api/purchase', purchase, httpOptions);
  }
}
