import type { ButtonHTMLAttributes } from 'react';
import styled, { css } from 'styled-components';

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  $variant?: 'default' | 'outlined' | 'secondary';
  $fullWidth?: boolean;
  $fullHeight?: boolean;
};

const Button = (props: ButtonProps) => {
  const { children, $variant = 'default', $fullWidth = false, $fullHeight = false, ...rest } = props;

  return (
    <Container $variant={$variant} $fullWidth={$fullWidth} $fullHeight={$fullHeight} {...rest}>
      {children}
    </Container>
  );
};

export default Button;

const ButtonVariants = {
  secondary: css`
    color: ${(props) => props.theme.color.white};
    background-color: ${(props) => props.theme.color.tertiary};
    border: none;
  `,
  outlined: css`
    color: ${(props) => props.theme.color.gray};
    background-color: ${(props) => props.theme.color.white};
    border: 2px solid ${(props) => props.theme.color.primary};
  `,
  default: css`
    color: ${(props) => props.theme.color.white};
    background-color: ${(props) => props.theme.color.primary};
    border: none;
  `,
};

const Container = styled.button<ButtonProps>`
  cursor: pointer;

  padding: ${({ theme }) => theme.space['1.5']} 0;

  font-size: 16px;
  font-weight: 500;

  border-radius: 40px;
  ${(props) => ButtonVariants[props.$variant || 'default']}
  ${(props) => props.$fullWidth && 'width: 100%;'}
  ${(props) => props.$fullHeight && 'height: 100%;'}
`;
