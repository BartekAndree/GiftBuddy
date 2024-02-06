export interface User {

}

export interface UserProfileResponseDTO {
  id?: string;
  externalId: string;
  username: string;
  firstName?: string;
  lastName: string;
  email: string;
  phoneNumber: string;
}

export interface UserProfileRequestDTO {
  externalId: string;
  username: string;
  firstName?: string;
  lastName?: string;
  email: string;
  phoneNumber?: string;
}
