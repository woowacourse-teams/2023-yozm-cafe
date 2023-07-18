import { ButtonHTMLAttributes } from 'react';
import styled from 'styled-components';

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  variant?: 'contained' | 'outlined';
  fullWidth?: boolean;
};

const Button = ({ children, variant = 'contained', fullWidth = false, ...rest }: ButtonProps) => {
  return (
    <Container variant={variant} fullWidth={fullWidth} {...rest}>
      {children}
    </Container>
  );
};

export default Button;

const Container = styled.button<ButtonProps>`
  cursor: pointer;

  padding: ${({ theme }) => theme.space['1.5']} 0;

  font-size: 16px;
  font-weight: 500;

  border-radius: 40px;

  ${(props) => {
    if (props.variant === 'contained') {
      return `
        background-color: ${props.theme.color.primary};
        color: ${props.theme.color.white};
        border: none;
      `;
    } else if (props.variant === 'outlined') {
      return `
        background-color: ${props.theme.color.white};
        color: ${props.theme.color.text.secondary};
        border: 2px solid ${props.theme.color.primary};
      `;
    }
  }}

  ${(props) => props.fullWidth && 'width: 100%;'}
`;
