import { Pipe, PipeTransform } from '@angular/core';
import { Need } from './need';

@Pipe({
  name: 'needFilter'
})
export class NeedFilterPipe implements PipeTransform {

  transform(needs: Need[], filter: string): any {
    if(filter == '') return needs; 
    return needs.filter(need => need.name.toLowerCase().startsWith(filter.toLowerCase())); 
  }

}
