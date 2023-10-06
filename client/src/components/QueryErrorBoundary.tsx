import { QueryErrorResetBoundary, onlineManager } from '@tanstack/react-query';
import type { PropsWithChildren } from 'react';
import { useEffect, useState } from 'react';
import type { ErrorBoundaryProps } from './ErrorBoundary';
import ErrorBoundary from './ErrorBoundary';

type QueryErrorBoundaryProps = PropsWithChildren<ErrorBoundaryProps>;

const QueryErrorBoundary = (props: QueryErrorBoundaryProps) => {
  const { children, onReset, ...restProps } = props;
  const [id, setId] = useState(0);

  useEffect(() => {
    return onlineManager.subscribe(() => {
      if (onlineManager.isOnline()) {
        setId((id) => id + 1);
      }
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
          {...restProps}
        >
          {children}
        </ErrorBoundary>
      )}
    </QueryErrorResetBoundary>
  );
};

export default QueryErrorBoundary;
