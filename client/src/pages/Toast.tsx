import type { PropsWithChildren } from 'react';
import { useEffect, useState } from 'react';
import styled, { css, keyframes } from 'styled-components';

type ToastProps = PropsWithChildren & {
  $variant?: 'default' | 'success' | 'warning' | 'error';
  $position?: string;
  $bottom?: string;
  $display?: string;
  $flexDirection?: string;
  $gap?: string;
};

const Toast = (props: ToastProps) => {
  const {
    $variant,
    $position = 'absolute',
    $bottom = '550px',
    $display = 'flex',
    $flexDirection = 'column',
    $gap = '8px',
    children,
  } = props;
  const [toastVisible, setToastVisible] = useState(false);

  useEffect(() => {
    setToastVisible(true);

    setTimeout(() => {
      setToastVisible(false);
    }, 5000);
  }, []);

  return (
    <>
      <Container
        $position={$position}
        $bottom={$bottom}
        $display={$display}
        $flexDirection={$flexDirection}
        $gap={$gap}
      >
        {toastVisible && <ToastMessage $variant={$variant}>{children}</ToastMessage>}
      </Container>
    </>
  );
};

export default Toast;

const Container = styled.div<ToastProps>`
  position: ${(props) => props.$position};
  bottom: ${(props) => props.$bottom};

  display: ${(props) => props.$display};
  flex-direction: ${(props) => props.$flexDirection};
  gap: ${(props) => props.$gap};
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

const ToastMessage = styled.div<ToastProps>`
  padding: ${({ theme }) => theme.space['3.5']};

  color: ${({ theme }) => theme.color.white};

  border-radius: 20px;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;

  animation: 0.3s ${ToastAnimation}, 0.3s 3s reverse forwards ${ToastAnimation};

  ${(props) => ToastColorVariants[props.$variant || 'default']}
`;
