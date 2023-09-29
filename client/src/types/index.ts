export type AuthUrl = {
  provider: string;
  authorizationUrl: string;
};

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

export type OpeningHourDay = 'MONDAY' | 'TUESDAY' | 'WEDNESDAY' | 'THURSDAY' | 'FRIDAY' | 'SATURDAY' | 'SUNDAY';

export type OpeningHour =
  | {
      day: OpeningHourDay;
      open: string;
      close: string;
      opened: true;
    }
  | {
      day: OpeningHourDay;
      open: null;
      close: null;
      opened: false;
    };

type CafeDetail = {
  openingHours: OpeningHour[];
  mapUrl: string;
  description: string;
};

export type Cafe = {
  id: number;
  name: string;
  address: string;
  images: string[];
  isLiked: boolean;
  likeCount: number;
  detail: CafeDetail;
};

export type LikedCafe = {
  cafeId: number;
  imageUrl: string;
};

export type AuthProvider = 'kakao' | 'google';

export type CafeMenuBoard = {
  id: number;
  priority: number;
  imageUrl: string;
};

export type CafeMenuItem = {
  id: number;
  priority: number;
  name: string;
  imageUrl: string | null;
  description: string;
  price: string;
  isRecommended: boolean;
};

export type CafeMenu = {
  cafeId: Cafe['id'];
  menuBoards: CafeMenuBoard[];
  menus: CafeMenuItem[];
};

export type Rank = {
  id: number;
  rank: number;
  name: string;
  address: string;
  image: string;
  likeCount: number;
};

export type LocationData = {
  id: number;
  name: string;
  address: string;
  latitude: number;
  longitude: number;
};

export type DisplayPosition = {
  longitude: number;
  latitude: number;
  longitudeDelta: number;
  latitudeDelta: number;
};
