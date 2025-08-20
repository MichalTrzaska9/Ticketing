import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Event} from "../../model/event";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private httpClient: HttpClient) {
  }

  getEvents(): Observable<Array<Event>> {
    return this.httpClient.get<Array<Event>>('http://localhost:9000/api/event');
  }

  createEvent(event: Event): Observable<Event> {
    return this.httpClient.post<Event>('http://localhost:9000/api/event', event);
  }
}
