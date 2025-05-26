export interface IPartieDeJournee {
  id: number;
  timeRangeStart?: string | null;
  timeRangeEnd?: string | null;
  nom?: string | null;
}

export type NewPartieDeJournee = Omit<IPartieDeJournee, 'id'> & { id: null };
