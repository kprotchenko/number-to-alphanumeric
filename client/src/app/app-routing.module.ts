import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NumberListComponent } from './number-list/number-list.component';
import { NumberEditComponent } from './number-edit/number-edit.component';

// import { OktaCallbackComponent } from '@okta/okta-angular';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'number-list',
    component: NumberListComponent
  },
  {
    path: 'number-add',
    component: NumberEditComponent
  },
  {
    path: 'number-edit/:id',
    component: NumberEditComponent
  }
  // ,
  // {
  //   path: 'implicit/callback',
  //   component: OktaCallbackComponent
  // }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
