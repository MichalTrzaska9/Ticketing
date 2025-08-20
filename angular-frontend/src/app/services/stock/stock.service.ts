import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class StockService {
private gatewayUrl = 'http://localhost:9000/api';

constructor(private http: HttpClient) {}

  getQuantity(stockCode: string): Observable<number> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'json' as const
    };
    return this.http.get<number>(
      `${this.gatewayUrl}/stock/quantity?stockCode=${stockCode}`,
      httpOptions
    );
  }
}
