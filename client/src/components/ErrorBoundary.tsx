import type { ErrorInfo, PropsWithChildren, ReactNode } from 'react';
import React from 'react';

export type ErrorBoundaryProps = PropsWithChildren<{
  fallbackRender?: ({ resetErrorBoundary }: { resetErrorBoundary: () => void }) => ReactNode;
  onReset?: () => void;
}>;

type ErrorBoundaryState = {
  error: Error | null;
};

class ErrorBoundary extends React.Component<ErrorBoundaryProps, ErrorBoundaryState> {
  constructor(props: ErrorBoundaryProps) {
    super(props);
    this.state = {
      error: null,
    };
  }

  static getDerivedStateFromError(error: Error): ErrorBoundaryState {
    return { error };
  }

  componentDidCatch(error: unknown, errorInfo: ErrorInfo) {
    if (error instanceof Error) {
      return;
    }
    throw error;
  }

  render() {
    const { fallbackRender } = this.props;
    const { error } = this.state;
    if (error) {
      return fallbackRender?.({ resetErrorBoundary: () => this.resetErrorBoundary() });
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
