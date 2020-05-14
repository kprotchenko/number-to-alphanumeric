import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { defer } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class NumberService {
  public API = '//localhost:8080';
  public PHONE_NUMBERS_API = this.API + '/numbers';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.API + '/get-phone-numbers');
  }

  getLetterCombinations(number: string): Observable<any> {
    return this.http.get(this.API + '/get-letter-combinations/' + number);
  }

  getRealNumberFromAlphanumeric(number: any): Observable<string> {
    let result: Observable<string>;
    result = this.http.get<string>(this.API + '/get-real-number/' + number.name);
    return result;
  }

  getNumber(id: string): Observable<any> {
    return this.http.get(this.API + '/get-number/' + id);
  }


  get(id: string) {
    return this.http.get(this.PHONE_NUMBERS_API + '/' + id);
  }
  // async getSaveUpdateNumber(number){
  //   const numeric = await this.getRealNumberFromAlphanumeric(number).toPromise();
  //
  //   //get the contract from url
  //   const contract = await this.http.get(customer.contractUrl).toPromise();
  //
  //   return contract; // you can return what you want here
  // }

  saveNumber(number: any): Observable<any> {
    return this.getRealNumberFromAlphanumeric(number).pipe(
      switchMap(numeric => {
        if (numeric) {
          number.number = numeric.toString();
        }

        if ((!number.id) && number.href) {
          number.id = +number.href.substring(number.href.lastIndexOf('/') + 1);
          number.href = null;
        } else if ((!number.id) && (!number.href) ) {
          return this.save(number);
        }
        // return this.save(altered_number);
        const numberClean = {
          id: number.id,
          name: number.name,
          number: number.number
        };
        return this.http.post(this.API + '/save-number', numberClean);
      })
    );
  }

  saveNumber_old(number: any): Observable<any> {
    let result: Observable<Object>;
    // number.number = this.getRealNumberFromAlphanumeric(number.name);
    // if (number['href']) {
    //   result = this.getRealNumberFromAlphanumeric(number.name).subscribe((numeric: any) => {
    //     if (numeric) {
    //       number.number = numeric;
    //       return this.http.put(number.href, number);
    //       // result = this.http.post(this.API + '/save-number', number);
    //       // return result;
    //     }
    //   });
    //
    // } else {
    //   result = this.getRealNumberFromAlphanumeric(number.name).lift((numeric: any) => {
    //     if (numeric) {
    //       number.number = numeric;
    //       return this.http.post(this.API + '/save-number', number);
    //       // result = this.http.post(this.API + '/save-number', number);
    //       // return result;
    //     }
    //   });
    // }
    // const getPromise = val => this.getRealNumberFromAlphanumeric(number).toPromise<object>()
    //   .then((numeric: any) => {
    //     if (numeric) {
    //       number.number = numeric;
    //     }
    //     return number;
    //   }).then((altered_number: object) => {
    //     return this.save(altered_number);
    //   });
    result = defer(() => this.getRealNumberFromAlphanumeric(number)
      .toPromise<any>()
      .then((numeric: string) => {
        if (numeric) {
          number.number = numeric.toString();
        }
        return number;
      })
      .then((altered_number: any) => {
        if ((!altered_number.id) && altered_number.href) {
          altered_number.id = +altered_number.href.substring(altered_number.href.lastIndexOf('/') + 1);
          altered_number.href = null;
        } else if ((!altered_number.id) && (!altered_number.href) ) {
          return this.save(altered_number);
        }
        // return this.save(altered_number);
        const numberClean = {
          id: altered_number.id,
          name: altered_number.name,
          number: altered_number.number
        };

        return this.http.post(this.API + '/save-number', numberClean);
      })
      .then((resultRun: Observable<Object>) => {
        return resultRun;
      })
    );
    // result = observable$;
    return result;
    // result = this.getRealNumberFromAlphanumeric(number).pipe<any>((numeric) => {
    //   if (numeric) {
    //     number.number = numeric;
    //     return number;
    //   }
    // }).lift((number_with_numeric) => {
    //   return this.save(number_with_numeric);
    // });
    // return result;

    // this.getRealNumberFromAlphanumeric(number).pipe((numeric: string) => {
    //   if (numeric) {
    //     number.number = numeric;
    //     result = this.save(number);
    //     return result;
    //   }
    // },result);
      // .lift((numeric: string) => {
      // if (numeric) {
      //   number.number = numeric;
      // }
    //   return this.save(number);
    // });
  }


  save(number: any): Observable<any> {
    let result: Observable<Object>;
    if (number['href']) {
      result = this.http.put(number.href, number);
    } else {
      result = this.http.post(this.PHONE_NUMBERS_API, number);
    }
    return result;
  }

  remove(href: string) {
    return this.http.delete(href);
  }
}
