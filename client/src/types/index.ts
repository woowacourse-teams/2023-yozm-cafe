export type Cafe = {
  id: number;
  title: string;
  address: string;
  images: string[];
  like: number | null;
  likeCount: number;
  detail: {
    available: string;
    addressUrl: string;
    etc: string;
  };
};
