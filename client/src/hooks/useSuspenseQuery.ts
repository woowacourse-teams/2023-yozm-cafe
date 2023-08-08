import { useQuery, type QueryKey, type UseQueryOptions, type UseQueryResult } from '@tanstack/react-query';

type BaseUseSuspenseQueryResult<TData> = Omit<
  UseQueryResult<TData, never>,
  | 'data'
  | 'enabled'
  | 'status'
  | 'error'
  | 'isError'
  | 'isLoading'
  | 'isLoadingError'
  | 'isInitialLoading'
  | 'isSuccess'
>;

/**
 * useSuspenseQuery가 성공한 경우의 결과 타입
 */
type UseSuspenseQueryResultOnSuccess<TData> = BaseUseSuspenseQueryResult<TData> & {
  data: TData;
  status: 'success';
  isSuccess: true;
  isIdle: false;
};

/**
 * useSuspenseQuery가 대기(idle)중일 때의 결과 타입
 */
type UseSuspenseQueryResultOnIdle<TData> = BaseUseSuspenseQueryResult<TData> & {
  data: undefined;
  status: 'idle';
  isSuccess: false;
  isIdle: true;
};

type UseSuspenseQueryResult<TData> = UseSuspenseQueryResultOnSuccess<TData> | UseSuspenseQueryResultOnIdle<TData>;

type UseSuspenseQueryOption<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
> = Omit<UseQueryOptions<TQueryFnData, TError, TData, TQueryKey>, 'suspense'>;

function useSuspenseQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(options: UseSuspenseQueryOption<TData, TError, TData, TQueryKey>): UseSuspenseQueryResultOnSuccess<TData>;

function useSuspenseQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  options: UseSuspenseQueryOption<TData, TError, TData, TQueryKey> & { enabled: true },
): UseSuspenseQueryResultOnSuccess<TData>;

function useSuspenseQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(options: UseSuspenseQueryOption<TData, TError, TData, TQueryKey> & { enabled: false }): UseSuspenseQueryResult<TData>;

/**
 * `suspense: true`일 때 data의 타입이 `TData | undefiend`로 나타나는 문제를 해결한
 * 커스텀 훅입니다.
 *
 * suspense를 사용하면 성공한 결과밖에 반환합니다. 따라서 `TData | undefined`는 올바른
 * 타입이 아니며, `TData` 가 올바른 타입입니다.
 *
 * @see https://github.com/woowacourse-teams/2023-yozm-cafe/issues/35
 *
 * @example
 * const Example = () => {
 *   const { data: cafes } = useSuspenseQuery({
 *     queryKey: ['cafes'],
 *     queryFn: () => client.getCafes(),
 *   });
 *
 *   return <div>등록된 카페 수: {cafes.length}개</div>;
 * }
 */
function useSuspenseQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  options: UseSuspenseQueryOption<TQueryFnData, TError, TData, TQueryKey> & { enabled?: boolean },
): UseSuspenseQueryResult<TData> {
  const queryResult = useQuery({
    ...options,
    suspense: true,
  });
  return {
    ...queryResult,
    isIdle: 'isIdle' in queryResult ? queryResult.isIdle : false,
  } as UseSuspenseQueryResult<TData>;
}

export default useSuspenseQuery;
