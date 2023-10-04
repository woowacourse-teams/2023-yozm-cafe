import type { PropsWithChildren } from 'react';
import { createContext, useContext, useEffect, useState } from 'react';
import styled, { css, keyframes } from 'styled-components';

type ToastVariant = 'default' | 'success' | 'warning' | 'error';

type ToastContextType = {
  showToast: (variant: ToastVariant, message: string) => void;
};

const ToastContext = createContext<ToastContextType | undefined>(undefined);

type ToastProviderPros = PropsWithChildren;

export const ToastProvider = (props: ToastProviderPros) => {
  const { children } = props;
  const [toastState, setToastState] = useState<{ variant: ToastVariant; message: string | null }>({
    variant: 'default',
    message: null,
  });

  useEffect(() => {
    if (toastState.variant && toastState.message !== null) {
      const timer = setTimeout(() => {
        setToastState({ variant: 'default', message: null });
      }, 5000);

      return () => clearTimeout(timer);
    }
  }, [toastState]);

  const showToast = (variant: ToastVariant, message: string) => {
    setToastState({ variant, message });
  };

  const contextValue = {
    showToast,
  };

  return (
    <ToastContext.Provider value={contextValue}>
      {children}
      {toastState.message !== null && (
        <Container>
          <ToastMessage $variant={toastState.variant}>{toastState.message}</ToastMessage>
        </Container>
      )}
    </ToastContext.Provider>
  );
};

export const useToast = () => {
  const context = useContext(ToastContext);

  if (!context) {
    throw new Error('useToast를 올바르게 사용하지 않았습니다.');
  }

  const showToast = (variant: ToastVariant, message: string) => {
    context.showToast(variant, message);

    setTimeout(() => {
      context.showToast('default', '');
    }, 5000);
  };

  return showToast;
};

const Container = styled.div`
  position: absolute;
  bottom: 50px;
  left: 50%;
  transform: translate(-50%);

  display: flex;
  flex-direction: column;

  white-space: nowrap;
`;

const ToastAnimation = keyframes`
  from {
    transform: translateY(100px);
    opacity: 0;
  }

  to {
    transform: translateY(0px);
    opacity: 1;
  }
`;

const ToastColorVariants = {
  default: css`
    background-color: ${(props) => props.theme.color.primary};
  `,
  success: css`
    background-color: ${(props) => props.theme.color.success};
  `,
  warning: css`
    background-color: ${(props) => props.theme.color.warning};
  `,
  error: css`
    background-color: ${(props) => props.theme.color.error};
  `,
};

const ToastMessage = styled.div<{ $variant: ToastVariant }>`
  padding: ${({ theme }) => theme.space['3.5']};

  color: ${({ theme }) => theme.color.white};

  border-radius: 20px;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;

  animation: 0.3s ${ToastAnimation}, 0.3s 3s reverse forwards ${ToastAnimation};
  ${({ $variant }) => ToastColorVariants[$variant]}
`;
