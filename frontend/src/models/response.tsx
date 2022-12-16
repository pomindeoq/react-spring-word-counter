export interface Word {
  word: string;
  count: number;
  group: string;
}

export interface Data {
  name: string;
  words: Word[];
}
