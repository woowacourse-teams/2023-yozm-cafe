import { rest } from 'msw';
import { cafes } from '../data/mockData';

export const handlers = [
  // 카페 조회
  rest.get('/cards', (req, res, ctx) => {
    return res(ctx.status(200), ctx.json(cafes));
  }),
];
