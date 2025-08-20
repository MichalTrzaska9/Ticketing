import {Component, inject, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Event} from "../../model/event";
import {EventService} from "../../services/event/event.service";
import {AsyncPipe, JsonPipe} from "@angular/common";
import {Router} from "@angular/router";
import {Purchase} from "../../model/purchase";
import {FormsModule} from "@angular/forms";
import {PurchaseService} from "../../services/purchase/purchase.service";
import {StockService} from "../../services/stock/stock.service";

@Component({
  selector: 'app-homepage',
  templateUrl: './home-page.component.html',
  standalone: true,
  imports: [
    AsyncPipe,
    JsonPipe,
    FormsModule
  ],
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly eventService = inject(EventService);
  private readonly purchaseService = inject(PurchaseService);
private readonly stockService = inject(StockService);
  private readonly router = inject(Router);
  isAuthenticated = false;
  events: Array<Event> = [];
  quantityIsNull = false;
  purchaseSuccess = false;
  purchaseFailed = false;

ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated}) => {
      this.isAuthenticated = isAuthenticated;
      this.eventService.getEvents().subscribe(events => {
        this.events = events;

        this.events.forEach(event => {
          this.stockService.getQuantity(event.stockCode).subscribe((qty: number) => {
            event.availableTickets = qty;
          });
        });
      });
    });
  }

  goToCreateEventPage() {
    this.router.navigateByUrl('/add-event');
  }

  purchaseEvent(event: Event, quantity: string) {
  console.log('DEBUG: event object from UI', event);
  console.log('DEBUG: stockCode from event', event.stockCode);

    this.oidcSecurityService.userData$.subscribe(result => {
      console.log(result);
      const userDetails = {
        email: result.userData.email,
        firstName: result.userData.given_name,
        lastName: result.userData.family_name
      };

      if(!quantity) {
        this.purchaseFailed = true;
        this.purchaseSuccess = false;
        this.quantityIsNull = true;
      } else {
        const purchase: Purchase = {
          stockCode: event.stockCode,
          price: event.price,
          quantity: Number(quantity),
          userDetails: userDetails
        }

        console.log('DEBUG: purchase object sending to backend', purchase);


        this.purchaseService.purchaseEvent(purchase).subscribe(() => {
          this.purchaseSuccess = true;
          this.purchaseFailed = false;
          this.quantityIsNull = false;

          this.stockService.getQuantity(event.stockCode).subscribe((qty: number) => {
            event.availableTickets = qty;
          });


        }, error => {
        console.error('DEBUG: purchase failed', error);
          this.purchaseFailed = true;
          this.purchaseSuccess = false;
        })
      }
    })
  }
}
