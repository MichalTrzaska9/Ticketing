import {Routes} from '@angular/router';
import {HomePageComponent} from "./pages/home-page/home-page.component";
import {AddEventComponent} from "./pages/add-event/add-event.component";

export const routes: Routes = [
  {path: '', component: HomePageComponent},
  {path: 'add-event', component: AddEventComponent}
];
