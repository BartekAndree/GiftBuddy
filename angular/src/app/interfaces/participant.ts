export interface ParticipantDetailsResponseDTO {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  isSettled: boolean;
}

export interface ParticipantResponseDTO {
  id: string;
  eventId: string;
  userId: string;
  isSettled: boolean;
}
