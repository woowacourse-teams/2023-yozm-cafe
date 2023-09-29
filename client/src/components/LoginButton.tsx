import type { ButtonHTMLAttributes } from 'react';
import { styled } from 'styled-components';
import type { Theme } from '../styles/theme';
import theme from '../styles/theme';

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
  $color: keyof Theme['color'];
};

const LoginButton = (props: ButtonProps) => {
  const { children, ...rest } = props;

  return (
    <Container {...rest}>
      <ButtonContent>{children}</ButtonContent>
    </Container>
  );
};

export default LoginButton;

const Container = styled.button<ButtonProps>`
  width: 44px;
  height: 44px;

  font-size: ${({ theme }) => theme.fontSize.base};
  font-weight: 500;

  background-color: ${(props) => theme.color[props.$color]};
  border: none;
  border-radius: 4px;
  box-shadow: ${({ theme }) => theme.shadow[1]};
`;

const ButtonContent = styled.div`
  display: flex;
  justify-content: center;
`;
