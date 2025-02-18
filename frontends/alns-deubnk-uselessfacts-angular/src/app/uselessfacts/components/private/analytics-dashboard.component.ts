import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {AnalyticsFactsSummaryResponse} from '../../models/analytics.models';
import {AnalyticsService} from '../../services/analytics.services';
import {MatCard, MatCardContent, MatCardHeader} from '@angular/material/card';
import {MatGridList, MatGridTile} from '@angular/material/grid-list';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
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
  MatTable, MatTableDataSource
} from '@angular/material/table';
import {LogAccessEntryResponse, StatisticsMetadataResponse, UselessFactResponse} from '../../models/fact.models';
import {MatPaginator} from '@angular/material/paginator';

@Component({
  selector: 'app-analytics-dashboard',
  templateUrl: './analytics-dashboard.component.html',
  styleUrls: ['./analytics-dashboard.component.scss'],
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatGridList,
    MatGridTile,
    MatFormField,
    MatInput,
    MatLabel,
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable,
    MatHeaderCellDef,
    MatPaginator,
  ]
})
export class AnalyticsDashboardComponent implements OnInit, AfterViewInit {
  constructor(private analyticsService: AnalyticsService) {

  }

  analyticsFactsSummary: AnalyticsFactsSummaryResponse | null = null;
  statisticsMetadataList: StatisticsMetadataResponse[] = [];
  selectedStatisticsMetadata: StatisticsMetadataResponse | undefined;
  logAccessEntriesOfSelectedFactList: LogAccessEntryResponse[] = [];

  statsMetadataPaginatedDataSource = new MatTableDataSource<StatisticsMetadataResponse>(this.statisticsMetadataList);
  logEntriesPaginatedDataSource = new MatTableDataSource<LogAccessEntryResponse>(this.logAccessEntriesOfSelectedFactList);

  @ViewChild(MatPaginator)
  statsMetadataPaginator: MatPaginator = new MatPaginator;

  @ViewChild(MatPaginator)
  logEntriesPaginator: MatPaginator = new MatPaginator;

  ngOnInit(): void {
    this.loadAnalytics();
  }

  ngAfterViewInit() {
    this.statsMetadataPaginatedDataSource.paginator = this.statsMetadataPaginator;
    this.logEntriesPaginatedDataSource.paginator = this.logEntriesPaginator;
  }

  loadAnalytics() {
    this.analyticsService.getAnalyticsFactsStatistics().subscribe(data => {
      this.analyticsFactsSummary = data;
      this.statisticsMetadataList = (this.analyticsFactsSummary.statisticsMetadataList ? this.analyticsFactsSummary.statisticsMetadataList : []);
      this.statsMetadataPaginatedDataSource.data = this.statisticsMetadataList;
    });
  }

  selectUselessFact(selectedStatisticsMetadata: StatisticsMetadataResponse) {
    this.selectedStatisticsMetadata = selectedStatisticsMetadata
    this.logAccessEntriesOfSelectedFactList = selectedStatisticsMetadata?.logAccessEntriesList
    this.logEntriesPaginatedDataSource.data = this.logAccessEntriesOfSelectedFactList;
    this.logEntriesPaginatedDataSource._updatePaginator(this.logAccessEntriesOfSelectedFactList.length)
  }


}
