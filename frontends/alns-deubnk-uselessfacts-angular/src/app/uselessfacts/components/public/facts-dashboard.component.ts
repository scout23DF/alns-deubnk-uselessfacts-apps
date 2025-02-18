import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {UselessFactResponse} from '../../models/fact.models';
import {UselessFactsService} from '../../services/fact.services';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {MatButton} from '@angular/material/button';
import {NgIf} from '@angular/common';
import {MatGridList, MatGridListModule, MatGridTile} from '@angular/material/grid-list';
import {MatPaginator} from '@angular/material/paginator';
import {MatCard, MatCardContent, MatCardHeader} from '@angular/material/card';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';

@Component({
  selector: 'app-facts-dashboard',
  templateUrl: './facts-dashboard.component.html',
  styleUrls: ['./facts-dashboard.component.scss'],
  imports: [
    MatTable,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCell,
    MatCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatColumnDef,
    MatButton,
    NgIf,
    MatGridList,
    MatGridTile,
    MatGridListModule,
    MatPaginator,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatFormField,
    MatLabel,
    MatInput
  ]
})
export class FactsDashboardComponent implements OnInit, AfterViewInit {
  constructor(private uselessFactService: UselessFactsService) {

  }

  foundUselessFactsList: UselessFactResponse[] = [];
  selectedUselessFact: UselessFactResponse | null = null;
  paginatedDataSource = new MatTableDataSource<UselessFactResponse>(this.foundUselessFactsList);

  @ViewChild(MatPaginator)
  paginator: MatPaginator = new MatPaginator;

  ngAfterViewInit() {
    this.paginatedDataSource.paginator = this.paginator;
    // this.loadFacts();
  }

  ngOnInit(): void {
    this.loadFacts();
  }

  loadFacts() {
    this.uselessFactService.getAllFacts().subscribe(data => {
      this.foundUselessFactsList = data;
      this.paginatedDataSource.data = data;
    });
  }

  selectUselessFact(shortenedUrl: string) {
    this.uselessFactService.getFactByShortenedUrl(shortenedUrl).subscribe(data => {
      this.selectedUselessFact = data;
      this.paginatedDataSource._updatePaginator(this.foundUselessFactsList.length)
    });
  }

  fetchOneUselessFact() {
    this.uselessFactService.fetchFromRemoteAndCreate().subscribe(data => {
      this.selectedUselessFact = data;
      this.loadFacts()
    });
  }

  createBulkFacts(qty: number) {
    for (let i = 0; i < qty; i++) {
      this.fetchOneUselessFact();
    }
  }

  deleteAllFacts() {
    /*
    this.factService.deleteAllFacts().subscribe(() => {
      this.loadFacts();
    });
    */
    console.log('deleteAllFacts');
  }

}
