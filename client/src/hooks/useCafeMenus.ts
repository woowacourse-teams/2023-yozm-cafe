import client from '../client';
import type { Cafe, CafeMenu } from '../types';
import useSuspenseQuery from './useSuspenseQuery';

/**
 * `/cafes/:cafeId/menus` 엔드포인트에서 받아오는 데이터(상태)를 사용할 수 있는 커스텀 훅입니다.
 * 카페에서 판매하는 메뉴 정보를 얻을 수 있으며, 응답 값에 대한 타입 정의는 @see {@link CafeMenu} 를 참고해주세요.
 *
 * @example
 * const Example = (props: ExampleProps) => {
 *   const { cafeId } = props;
 *   const { data: { menus, menuBoards }, fetchNextPage } = useCafeMenus(cafeId);
 *
 *   return <>...</>
 * }
 */
const useCafeMenus = (cafeId: Cafe['id']) => {
  return useSuspenseQuery<CafeMenu>({
    queryKey: ['cafes', cafeId, 'menus'],
    queryFn: () => client.getCafeMenu(cafeId),
  });
};

export default useCafeMenus;
