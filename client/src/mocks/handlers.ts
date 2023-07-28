import { rest } from 'msw';
import { cafes } from '../data/mockData';
import { Identity, User } from '../types';

let pageState = 1;

export const handlers = [
  // 카페 조회
  rest.get('/api/cafes', (req, res, ctx) => {
    const PAGINATE_UNIT = 5;

    const page = pageState;
    pageState += 1;

    const paginatedCafes = cafes.map((cafe, index) => ({
      ...cafe,
      name: `${cafe.name} (${page}호점)`,
      id: PAGINATE_UNIT * (page - 1) + 1 + index,
      detail: { ...cafe.detail, description: `(로그인 된 사용자)\n\n${cafe.detail.description}` },
    }));

    return res(ctx.status(200), ctx.json(paginatedCafes));
  }),

  // 로그인하지 않은 사용자의 카페 조회
  rest.get('/api/cafes/guest', (req, res, ctx) => {
    const PAGINATE_UNIT = 5;

    const page = Number(req.url.searchParams.get('page') || 1);

    const paginatedCafes = cafes.map((cafe, index) => ({
      ...cafe,
      name: `${cafe.name} (${page}호점)`,
      id: PAGINATE_UNIT * (page - 1) + 1 + index,
      detail: { ...cafe.detail, description: `(게스트 사용자)\n\n${cafe.detail.description}` },
    }));

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
          .map((cafe) => ({ cafeId: cafe.id, imageUrl: cafe.images[0] })),
      ),
    );
  }),

  // 인증 코드를 accessToken으로 교환
  rest.post('/api/auth/:authProvider', async (req, res, ctx) => {
    const { authProvider } = req.params;
    if (!['kakao', 'google'].includes(authProvider as string)) {
      return res(
        ctx.status(400),
        ctx.json({
          message: 'OAuth 제공자는 kakao 또는 google이어야 합니다.',
        }),
      );
    }
    const code = req.url.searchParams.get('code');
    if ((code?.length ?? 0) <= 0) {
      return res(
        ctx.status(400),
        ctx.json({
          message: 'Access Token을 발급하려면 Authorization Code가 필요합니다.',
        }),
      );
    }

    // 여기에서 모킹된 액세스 토큰을 생성
    const token =
      btoa(
        JSON.stringify({
          typ: 'JWT',
          alg: 'HS256',
        }),
      ) +
      '.' +
      btoa(
        JSON.stringify({
          sub: '1000',
          iat: Date.now(),
          exp: Date.now() + 1 * 60 * 60 * 1000,
        } satisfies Identity),
      ) +
      '.' +
      'SUPERSECRET';

    return res(
      ctx.status(200),
      ctx.json({
        token,
      }),
    );
  }),

  rest.get('/api/members/:memberId', (req, res, ctx) => {
    const { memberId } = req.params;
    const authorization = req.headers.get('Authorization');
    if (!authorization?.startsWith('Bearer')) {
      return res(
        ctx.status(401),
        ctx.json({
          message: '로그인이 필요합니다.',
        }),
      );
    }

    return res(
      ctx.status(200),
      ctx.json({
        id: String(memberId),
        imageUrl: 'https://avatars.githubusercontent.com/u/20203944?v=4',
        name: '솔로스타',
      } satisfies User),
    );
  }),

  // refreshToken (httpOnly) 쿠키 삭제
  rest.delete('/api/auth', async (req, res, ctx) => {
    return res(ctx.status(200));
  }),
];
