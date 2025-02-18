import {StatisticsMetadataResponse, UselessFactResponse} from './fact.models';

export interface UselessFactWithStatsResponse extends UselessFactResponse {
  hitsCount: number;
}

export interface AnalyticsFactsSummaryResponse {
  totalFactsCount: number;
  totalHitsCount: number;
  mostReadUselessFact: UselessFactWithStatsResponse | null;
  leastReadUselessFact: UselessFactWithStatsResponse | null;
  othersUselessFactAttributesMap: Map<String, number> | null;
  statisticsMetadataList: StatisticsMetadataResponse[] | null;
}
