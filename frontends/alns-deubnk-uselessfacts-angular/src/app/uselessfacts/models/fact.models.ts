export interface UselessFactResponse {
  shortenedUrl: string;
  text: string;
  language: string;
  permalink: string;
}

export interface LogAccessEntryResponse {
  typeOfEntry: string;
  accessedAt: Date;
  ipAddress: string;
  hostName: string;
  username: string;
}

export interface StatisticsMetadataResponse {
  uselessFactResponse: UselessFactResponse;
  hitsCount: number;
  logAccessEntriesList: LogAccessEntryResponse[];
}
