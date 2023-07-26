import { rest } from 'msw';
import { cafes } from '../data/mockData';

export const handlers = [
  // 카페 조회
  rest.get('/api/cafes', (req, res, ctx) => {
    const page = Number(req.url.searchParams.get('page') || 1);

    return res(
      ctx.status(200),
      ctx.json(
        cafes.map((cafe) => ({
          ...cafe,
          id: cafes.length * Number(page - 1) + cafe.id,
          name: `${cafe.name} (${page}번째 지점)`,
        })),
      ),
    );
  }),

  // 좋아요 추가
  rest.post('/api/cafes/:cafe_id/likes', (req, res, ctx) => {
    const { cafeId } = req.params;
    const updatedCafes = cafes.map((cafe) => {
      if (cafe.id === Number(cafeId)) {
        return { ...cafe, likeCount: cafe.likeCount + 1, isLiked: true };
      }
      return cafe;
    });
    return res(ctx.status(200), ctx.json(updatedCafes));
  }),

  // 좋아요 취소
  rest.delete('/api/cafes/:cafe_id/likes', (req, res, ctx) => {
    const { cafeId } = req.params;
    const updatedCafes = cafes.map((cafe) => {
      if (cafe.id === Number(cafeId) && cafe.likeCount > 0) {
        return { ...cafe, likeCount: cafe.likeCount - 1, isLiked: false };
      }
      return cafe;
    });
    return res(ctx.status(200), ctx.json(updatedCafes));
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

  // refreshToken (httpOnly) 쿠키 삭제
  rest.delete('/auth', async (req, res, ctx) => {
    return res(ctx.status(204));
  }),
];
