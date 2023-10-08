import { QueryErrorResetBoundary, onlineManager } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import type { ErrorBoundaryProps } from './ErrorBoundary';
import ErrorBoundary from './ErrorBoundary';
import ErrorRetryPrompt from './ErrorRetryPrompt';

type QueryErrorBoundaryProps<TError> = ErrorBoundaryProps<TError>;

/**
 * react-query에서 `useQuery` 혹은 `useMutation` 등의 쿼리 함수를
 * `suspense: true`로 사용 중 발생하는 에러를 위한
 * ErrorBoundary 컴포넌트입니다.
 *
 * ErrorBoundary에서 다음 부가 기능이 추가되어있습니다
 * * network 상태가 온라인으로 전환되었을 때 자동으로 재시도(error reset)
 * * ErrorBoundary reset 시, query cache도 reset
 * * 아무런 fallback이 주어지지 않았을 시 {@link ErrorRetryPrompt} 를 기본으로 사용
 */
const QueryErrorBoundary = <TError = Error,>(props: QueryErrorBoundaryProps<TError>) => {
  const { children, onReset, ...restProps } = props;
  const [id, setId] = useState(0);

  useEffect(() => {
    return onlineManager.subscribe(() => {
      if (onlineManager.isOnline()) setId((id) => id + 1);
    });
  }, []);

  return (
    <QueryErrorResetBoundary>
      {({ reset }) => (
        <ErrorBoundary
          key={id}
          onReset={() => {
            reset();
            onReset?.();
          }}
          {...('FallbackComponent' in restProps || 'fallbackRender' in restProps
            ? restProps
            : { FallbackComponent: ErrorRetryPrompt, ...restProps })}
        >
          {children}
        </ErrorBoundary>
      )}
    </QueryErrorResetBoundary>
  );
};

export default QueryErrorBoundary;
