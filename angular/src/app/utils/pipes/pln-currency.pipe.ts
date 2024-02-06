import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'plnCurrency'
})
export class PlnCurrencyPipe implements PipeTransform {
  transform(value: number | null | undefined): string {
    if (value === null || value === undefined) {
      return '';
    }

    return `${value.toFixed(2)} zł`;
  }
}
