import type { ComponentType, ErrorInfo, PropsWithChildren, ReactNode } from 'react';
import React from 'react';

type FallbackProps<TError> = {
  error: TError;
  resetErrorBoundary: () => void;
};

export type ErrorBoundaryBaseProps<TError> = PropsWithChildren<{
  onReset?: () => void;
  caught?: (error: unknown) => error is TError;
}>;

export type ErrorBoundaryWithRenderProps<TError> = ErrorBoundaryBaseProps<TError> & {
  fallbackRender: ({ error, resetErrorBoundary }: FallbackProps<TError>) => ReactNode;
  FallbackComponent?: never;
};

export type ErrorBoundaryWithComponentProps<TError> = ErrorBoundaryBaseProps<TError> & {
  fallbackRender?: never;
  FallbackComponent: ComponentType<FallbackProps<TError>>;
};

export type ErrorBoundaryWithNothingProps<TError> = ErrorBoundaryBaseProps<TError> & {
  fallbackRender?: never;
  FallbackComponent?: never;
};

export type ErrorBoundaryProps<TError> =
  | ErrorBoundaryWithRenderProps<TError>
  | ErrorBoundaryWithComponentProps<TError>
  | ErrorBoundaryWithNothingProps<TError>;

type ErrorBoundaryState<TError> = {
  error: TError | null;
};

/**
 * 자식 컴포넌트를 렌더링하는 중 에러가 발생하였을 때(throw error)
 * 대체 컴포넌트(fallback)을 렌더합니다.
 */
class ErrorBoundary<TError = Error> extends React.Component<ErrorBoundaryProps<TError>, ErrorBoundaryState<TError>> {
  constructor(props: ErrorBoundaryProps<TError>) {
    super(props);
    this.state = {
      error: null,
    };
  }

  static getDerivedStateFromError(error: any): ErrorBoundaryState<any> {
    return { error };
  }

  componentDidCatch(error: unknown, errorInfo: ErrorInfo) {
    const defaultCaught = (error: unknown): error is Error => error instanceof Error;
    const { caught = defaultCaught } = this.props;
    if (caught(error)) {
      return;
    }
    throw error;
  }

  render() {
    const { error } = this.state;
    if (error) {
      if ('fallbackRender' in this.props && this.props.fallbackRender) {
        const { fallbackRender } = this.props;
        return fallbackRender({ error, resetErrorBoundary: () => this.resetErrorBoundary() });
      }
      if ('FallbackComponent' in this.props && this.props.FallbackComponent) {
        const { FallbackComponent } = this.props;
        return <FallbackComponent error={error} resetErrorBoundary={() => this.resetErrorBoundary()} />;
      }
      return null;
    }
    return this.props.children;
  }

  resetErrorBoundary() {
    this.setState({ error: null });
    const { onReset } = this.props;
    onReset?.();
  }
}

export default ErrorBoundary;
