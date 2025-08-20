export interface Event {
  id?: string;
  stockCode: string;
  name: string;
  description: string;
  price: number;
  availableTickets?: number;
}
