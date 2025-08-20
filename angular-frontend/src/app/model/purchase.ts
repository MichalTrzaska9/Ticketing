export interface Purchase {
  id?: number;
  purchaseNumber?: string;
  stockCode: string;
  price: number;
  quantity: number;
  userDetails: UserDetails
}

export interface UserDetails {
  email: string;
  firstName: string;
  lastName: string;
}
