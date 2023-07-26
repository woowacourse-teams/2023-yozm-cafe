import { styled } from 'styled-components';
import theme from '../styles/theme';

type LogoProps = {
  fontSize: keyof typeof theme.fontSize;
};

const Logo = ({ fontSize }: LogoProps) => {
  return <Container fontSize={fontSize}>요즘카페</Container>;
};

export default Logo;

const Container = styled.h1<LogoProps>`
  cursor: pointer;
  font-family: 'BMJUA', sans-serif;
  font-size: ${(props) => theme.fontSize[props.fontSize]};
  color: ${({ theme }) => theme.color.primary};
`;
