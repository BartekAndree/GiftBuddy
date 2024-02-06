export interface EventResponseDTO {
  id: string;
  organizerId: string;
  organizerExternalId: string;
  title: string;
  description?: string;
  giftIdea?: string;
  imageUrl?: string;
  contribution?: number;
  currentAmount?: number;
  targetAmount?: number;
  eventDate?: Date;
  endDate: Date;
  isActive: boolean;
}

export interface EventRequestDTO {
  title: string;
  description?: string;
  giftIdea?: string;
  imageUrl?: string;
  contribution?: number;
  targetAmount?: number;
  eventDate?: Date;
  endDate: Date;
}

