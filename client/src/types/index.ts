export type Identity = {
  sub: string;
  iat: number;
  exp: number;
};

export type User = {
  id: string;
  name: string;
  imageUrl: string;
};

type CafeImages = {
  urls: string[];
};

type OpeningHours = {
  day: string;
  open: string;
  close: string;
  opened: boolean;
};

type CafeDetail = {
  openingHours: OpeningHours[];
  mapUrl: string;
  description: string;
};

export type Cafe = {
  id: number;
  name: string;
  address: string;
  images: CafeImages;
  isLiked: boolean;
  likeCount: number;
  detail: CafeDetail;
};

export type AuthProvider = 'kakao' | 'google';
