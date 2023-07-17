import { ButtonHTMLAttributes } from 'react';
import { styled } from 'styled-components';
import theme, { Theme } from '../styles/theme';

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  color: keyof Theme['color'];
  border?: string;
};

const LoginButton = ({ children, ...rest }: ButtonProps) => {
  return <Container {...rest}>{children}</Container>;
};

export default LoginButton;

const Container = styled.button<ButtonProps>`
  cursor: pointer;

  width: 360px;
  height: 48px;
  padding: ${({ theme }) => theme.space['2.5']} ${({ theme }) => theme.space['5']};

  font-size: ${({ theme }) => theme.fontSize.base};
  font-weight: 600;

  background-color: ${(props) => theme.color[props.color]};
  border: ${(props) => props.border};
  border-radius: 40px;
`;
