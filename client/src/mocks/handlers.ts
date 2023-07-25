import { rest } from 'msw';
import { cafes } from '../data/mockData';

export const handlers = [
  // 카페 조회
  rest.get('/api/cafes', (req, res, ctx) => {
    const PAGINATE_UNIT = 5;

    const page = Number(req.url.searchParams.get('page') || 1);
    const isPageExists = !!cafes.at(PAGINATE_UNIT * page - 1);

    if (!isPageExists) {
      cafes
        .map((cafe) => structuredClone(cafe))
        .map((cafe, index) => ({
          ...cafe,
          name: `${cafe.name} (${page}호점)`,
          id: PAGINATE_UNIT * (page - 1) + 1 + index,
        }))
        .forEach((cafe) => {
          cafes.push(cafe);
        });
    }

    const [start, end] = [PAGINATE_UNIT * (page - 1), PAGINATE_UNIT * page];
    const paginatedCafes = cafes.slice(start, end);

    return res(ctx.status(200), ctx.json(paginatedCafes));
  }),

  // 좋아요 추가
  rest.post('/api/cafes/:cafeId/likes', (req, res, ctx) => {
    const { cafeId } = req.params;
    const liked = req.url.searchParams.get('isLiked');
    if (!liked) {
      return res(
        ctx.status(400),
        ctx.json({
          message: 'isLiked Query Parameter가 필요합니다.',
        }),
      );
    }
    const isLiked = liked === 'true';

    const cafe = cafes.find((cafe) => cafe.id === Number(cafeId));
    if (!cafe) {
      return res(
        ctx.status(404),
        ctx.json({
          message: '카페를 찾을 수 없습니다.',
        }),
      );
    }

    cafe.isLiked = isLiked;
    return res(ctx.status(200), ctx.json({}));
  }),

  // 좋아요 한 목록 조회
  rest.get('/api/members/:memberId/liked-cafes', (req, res, ctx) => {
    const PAGINATE_UNIT = 20;

    const memberId = Number(req.params.memberId);
    const page = Number(req.url.searchParams.get('page') || 1);
    const [start, end] = [PAGINATE_UNIT * (page - 1), PAGINATE_UNIT * page];

    return res(
      ctx.status(200),
      ctx.json(
        cafes
          .filter((cafe) => cafe.isLiked)
          .slice(start, end)
          .map((cafe) => ({ cafeId: cafe.id, imageUrl: cafe.images.urls[0] })),
      ),
    );
  }),

  // 인증 코드를 accessToken으로 교환
  rest.post('/api/auth/kakao', async (req, res, ctx) => {
    const code = req.url.searchParams.get('code');
    if ((code?.length ?? 0) <= 0) {
      return res(
        ctx.status(400),
        ctx.json({
          message: 'Access Token을 발급하려면 Authorization Code가 필요합니다.',
        }),
      );
    }
    // 여기에서 모킹된 액세스 토큰을 생성하고 받은 리프레시 토큰을 사용
    const accessToken = 'VeryGoodSalmonKingFuckingKoreanILoveCoffee';

    return res(
      ctx.status(200),
      ctx.json({
        accessToken,
      }),
    );
  }),
];
