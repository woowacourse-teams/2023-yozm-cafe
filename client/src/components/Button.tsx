import { ButtonHTMLAttributes } from 'react';
import { styled } from 'styled-components';
import theme from '../styles/theme';

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  width: string;
  height: string;
  border: string;
  fontWeight: string;
  color: keyof typeof theme.color;
};

const Button = ({ children, ...rest }: ButtonProps) => {
  return <StyledButton {...rest}>{children}</StyledButton>;
};
export default Button;

const StyledButton = styled.button<ButtonProps>`
  width: ${(props) => props.width};
  height: ${(props) => props.height};

  font-weight: ${(props) => props.fontWeight};
  color: ${({ theme }) => theme.color.text.primary};

  background-color: ${(props) => theme.color[props.color]};
  border: ${(props) => props.border};
  border-radius: 40px;
`;
