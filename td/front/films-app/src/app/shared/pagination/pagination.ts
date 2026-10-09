import { Component, input, output } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-pagination',
  styleUrl: './pagination.css',
  templateUrl: './pagination.html',
})
export class Pagination {
  page = input.required<number>();
  totalPages = input.required<number>();
  pageChange = output<number>();

  precedente() {
    this.pageChange.emit(this.page() - 1);
  }

  suivante() {
    this.pageChange.emit(this.page() + 1);
  }
}
