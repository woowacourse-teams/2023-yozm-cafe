import { rest } from 'msw';
import { cafes } from '../data/mockData';

export const handlers = [
  // 카페 조회
  rest.get('/cards', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(cafes));
  }),

  // 좋아요 추가
  rest.post('/cafes/:cafe_id/likes', (req, res, ctx) => {
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
  rest.delete('/cafes/:cafe_id/likes', (req, res, ctx) => {
    const { cafeId } = req.params;
    const updatedCafes = cafes.map((cafe) => {
      if (cafe.id === Number(cafeId) && cafe.likeCount > 0) {
        return { ...cafe, likeCount: cafe.likeCount - 1, isLiked: false };
      }
      return cafe;
    });
    return res(ctx.status(200), ctx.json(updatedCafes));
  }),
];
