import { ParticipantResponseDTO } from './participant';

export interface EmailRequestDTO {
  email: string;
}

export interface EmailBufferResponseDTO {
  id: string;
  eventId: string;
  email: string;
}

export interface SaveEmailResponseDTO {
  emailBufferResponseDTO?: EmailBufferResponseDTO;
  participantResponseDTO?: ParticipantResponseDTO;
}
