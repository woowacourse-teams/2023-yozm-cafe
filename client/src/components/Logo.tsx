import { styled } from 'styled-components';
import theme from '../styles/theme';

type LogoProps = {
  fontSize: keyof typeof theme.fontSize;
};

const Logo = ({ fontSize }: LogoProps) => {
  return <StyledLogo fontSize={fontSize}>요즘카페</StyledLogo>;
};

export default Logo;

const StyledLogo = styled.h1<LogoProps>`
  font-family: 'BMJUA', sans-serif;
  font-size: ${(props) => theme.fontSize[props.fontSize]};
  color: ${({ theme }) => theme.color.primary};
`;
