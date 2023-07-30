import { Cafe, LikedCafe } from '../types';

export const likedCafes: LikedCafe[] = [
  {
    cafeId: 1,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 2,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 3,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 4,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 5,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 6,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 7,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 8,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 9,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 10,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 11,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 12,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 13,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 14,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 15,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 16,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 17,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 18,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 19,
    imageUrl: '/images/cafe-image-1.png',
  },
  {
    cafeId: 20,
    imageUrl: '/images/cafe-image-1.png',
  },
];

export const cafes: Cafe[] = [
  {
    id: 1,
    name: '성수 카페',
    address: '성수로 1길',
    images: [
      '/images/cafe-image-1.png',
      '/images/cafe-image-2.png',
      '/images/cafe-image-3.png',
      '/images/cafe-image-4.png',
      '/images/cafe-image-5.png',
    ],
    isLiked: false,
    likeCount: 1,
    detail: {
      mapUrl: 'https://map.kakao/~~',
      description: '우리 카페는 이뻐용',
      openingHours: [
        {
          day: 'MONDAY',
          open: '2023-07-14T15:27:48.222433',
          close: '2023-07-14T15:27:48.222448',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '2023-07-14T15:27:48.222455',
          close: '2023-07-14T15:27:48.222457',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '2023-07-14T15:27:48.222459',
          close: '2023-07-14T15:27:48.222461',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '2023-07-14T15:27:48.222463',
          close: '2023-07-14T15:27:48.222465',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '2023-07-14T15:27:48.222468',
          close: '2023-07-14T15:27:48.22247',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '2023-07-14T15:27:48.222472',
          close: '2023-07-14T15:27:48.222475',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '2023-07-14T15:27:48.222477',
          close: '2023-07-14T15:27:48.222479',
          opened: true,
        },
      ],
    },
  },
  {
    id: 2,
    name: '성수 카페2',
    address: '성수로 2길',
    images: ['/images/cafe-image-2.png', '/images/cafe-image-2.png'],
    isLiked: true,
    likeCount: 0,
    detail: {
      mapUrl: 'https://map.kakao/~~',
      description: '우리 카페는 귀여워용',
      openingHours: [
        {
          day: 'MONDAY',
          open: '2023-07-14T15:27:48.222433',
          close: '2023-07-14T15:27:48.222448',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '2023-07-14T15:27:48.222455',
          close: '2023-07-14T15:27:48.222457',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '2023-07-14T15:27:48.222459',
          close: '2023-07-14T15:27:48.222461',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '2023-07-14T15:27:48.222463',
          close: '2023-07-14T15:27:48.222465',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '2023-07-14T15:27:48.222468',
          close: '2023-07-14T15:27:48.22247',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '2023-07-14T15:27:48.222472',
          close: '2023-07-14T15:27:48.222475',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '2023-07-14T15:27:48.222477',
          close: '2023-07-14T15:27:48.222479',
          opened: true,
        },
      ],
    },
  },
  {
    id: 3,
    name: '성수 카페3',
    address: '성수로 3길',
    images: ['/images/cafe-image-3.png', '/images/cafe-image-3.png'],
    isLiked: true,
    likeCount: 0,
    detail: {
      mapUrl: 'https://map.kakao/~~',
      description: '우리 카페는 귀여워용',
      openingHours: [
        {
          day: 'MONDAY',
          open: '2023-07-14T15:27:48.222433',
          close: '2023-07-14T15:27:48.222448',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '2023-07-14T15:27:48.222455',
          close: '2023-07-14T15:27:48.222457',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '2023-07-14T15:27:48.222459',
          close: '2023-07-14T15:27:48.222461',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '2023-07-14T15:27:48.222463',
          close: '2023-07-14T15:27:48.222465',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '2023-07-14T15:27:48.222468',
          close: '2023-07-14T15:27:48.22247',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '2023-07-14T15:27:48.222472',
          close: '2023-07-14T15:27:48.222475',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '2023-07-14T15:27:48.222477',
          close: '2023-07-14T15:27:48.222479',
          opened: true,
        },
      ],
    },
  },
  {
    id: 4,
    name: '성수 카페4',
    address: '성수로 4길',
    images: ['/images/cafe-image-4.png', '/images/cafe-image-4.png'],
    isLiked: false,
    likeCount: 0,
    detail: {
      mapUrl: 'https://map.kakao/~~',
      description: '우리 카페는 귀여워용',
      openingHours: [
        {
          day: 'MONDAY',
          open: '2023-07-14T15:27:48.222433',
          close: '2023-07-14T15:27:48.222448',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '2023-07-14T15:27:48.222455',
          close: '2023-07-14T15:27:48.222457',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '2023-07-14T15:27:48.222459',
          close: '2023-07-14T15:27:48.222461',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '2023-07-14T15:27:48.222463',
          close: '2023-07-14T15:27:48.222465',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '2023-07-14T15:27:48.222468',
          close: '2023-07-14T15:27:48.22247',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '2023-07-14T15:27:48.222472',
          close: '2023-07-14T15:27:48.222475',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '2023-07-14T15:27:48.222477',
          close: '2023-07-14T15:27:48.222479',
          opened: true,
        },
      ],
    },
  },
  {
    id: 5,
    name: '성수 카페5',
    address: '성수로 5길',
    images: ['/images/cafe-image-5.png', '/images/cafe-image-5.png'],
    isLiked: false,
    likeCount: 0,
    detail: {
      mapUrl: 'https://map.kakao/~~',
      description: '우리 카페는 귀여워용',
      openingHours: [
        {
          day: 'MONDAY',
          open: '2023-07-14T15:27:48.222433',
          close: '2023-07-14T15:27:48.222448',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '2023-07-14T15:27:48.222455',
          close: '2023-07-14T15:27:48.222457',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '2023-07-14T15:27:48.222459',
          close: '2023-07-14T15:27:48.222461',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '2023-07-14T15:27:48.222463',
          close: '2023-07-14T15:27:48.222465',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '2023-07-14T15:27:48.222468',
          close: '2023-07-14T15:27:48.22247',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '2023-07-14T15:27:48.222472',
          close: '2023-07-14T15:27:48.222475',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '2023-07-14T15:27:48.222477',
          close: '2023-07-14T15:27:48.222479',
          opened: true,
        },
      ],
    },
  },
];
