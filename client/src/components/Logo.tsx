import { styled } from 'styled-components';
import theme from '../styles/theme';

type LogoProps = {
  fontSize: keyof typeof theme.fontSize;
  onClick?: () => void;
};

const Logo = (props: LogoProps) => {
  const { fontSize, onClick } = props;

  return (
    <Container fontSize={fontSize} onClick={onClick} aria-label="요즘카페 로고">
      요즘카페
    </Container>
  );
};

export default Logo;

const Container = styled.h1<LogoProps>`
  cursor: pointer;
  font-family: 'BMJUA', sans-serif;
  font-size: ${(props) => theme.fontSize[props.fontSize]};
  color: ${({ theme }) => theme.color.primary};
`;
