type CafeImages = {
  urls: string[];
};

type AvailableTime = {
  day: string;
  open: string;
  close: string;
  opened: boolean;
};

type CafeDetail = {
  availableTimes: AvailableTime[];
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
