import type { Cafe, CafeMenu, LikedCafe } from '../types';

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
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '15:27',
          close: '15:27',
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
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '15:27',
          close: '15:27',
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
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '15:27',
          close: '15:27',
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
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '15:27',
          close: '15:27',
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
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'TUESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'WEDNESDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'THURSDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'FRIDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SATURDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
        {
          day: 'SUNDAY',
          open: '15:27',
          close: '15:27',
          opened: true,
        },
      ],
    },
  },
];

export const cafeMenus: CafeMenu[] = [
  {
    cafeId: 1,
    menuBoards: [
      {
        id: 1,
        priority: 1,
        imageUrl: '/images/cafe-menu-board-image-1.jpg',
      },
      {
        id: 2,
        priority: 2,
        imageUrl: '/images/cafe-menu-board-image-2.jpg',
      },
      {
        id: 3,
        priority: 3,
        imageUrl: '/images/cafe-menu-board-image-3.jpg',
      },
    ],
    menus: [
      {
        id: 1,
        priority: 2,
        name: '아메리카노',
        imageUrl: '/images/cafe-menu-image-1.jpg',
        description: '시원한 아메리카노 입니다.',
        price: '5000',
        recommended: true,
      },
      {
        id: 2,
        priority: 1,
        name: '라떼',
        imageUrl: null,
        description: '따뜻한 라떼 한잔 어떠세요?',
        price: '6000',
        recommended: false,
      },
    ],
  },
  {
    cafeId: 2,
    menuBoards: [
      {
        id: 4,
        priority: 1,
        imageUrl: '/images/cafe-menu-board-image-2.jpg',
      },
      {
        id: 5,
        priority: 2,
        imageUrl: '/images/cafe-menu-board-image-3.jpg',
      },
    ],
    menus: [
      {
        id: 3,
        priority: 1,
        name: '초코라떼',
        imageUrl: '/images/cafe-menu-image-1.jpg',
        description: '맛있는 초코라떼 초콜릿 맛',
        price: '변동',
        recommended: true,
      },
      {
        id: 4,
        priority: 2,
        name: '오렌지주스',
        imageUrl: '/images/cafe-menu-image-2.avif',
        description: '상큼한 과일음료 오렌지 주스',
        price: '6500~9000',
        recommended: false,
      },
    ],
  },
];
