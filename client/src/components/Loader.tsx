import { css, keyframes, styled } from 'styled-components';

const LoaderContainer = styled.div`
  position: absolute;
  top: 50%;
  left: 40%;
  margin-left: 10%;
  transform: translate3d(-50%, -50%, 0);
`;

const Slide = keyframes`
  0% {
    transform: scale(1);
  }
  50% {
    opacity: 0.3;
    transform: scale(2);
  }
  100% {
    transform: scale(1);
  }
`;

const Dot = styled.div<{ delay: number }>`
  width: 24px;
  height: 24px;
  background: ${({ theme }) => theme.color.primary};
  border-radius: 100%;
  display: inline-block;

  animation: ${({ delay }) =>
    css`
      ${Slide} 1s infinite ${delay}s
    `};
`;

const Loader = () => {
  return (
    <LoaderContainer>
      {[1, 2, 3, 4, 5].map((index) => (
        <Dot key={index} delay={index * 0.1} />
      ))}
    </LoaderContainer>
  );
};

export default Loader;