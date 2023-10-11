import { MdOutlineErrorOutline } from 'react-icons/md';
import { Link, useRouteError } from 'react-router-dom';
import { keyframes, styled } from 'styled-components';
import Button from '../components/Button';
import AppError from '../errors/AppError';

const ErrorPage = () => {
  const error = useRouteError();
  const message = error instanceof Error ? error.message : '알 수 없는 에러';
  const isExpectedError = error instanceof AppError;

  return (
    <Container>
      <SentenceContainer>
        <MainSentence>{isExpectedError ? '다시 확인해주세요' : '예기치 못한 에러가 발생하였습니다'}</MainSentence>
        {isExpectedError && <Sentence>{message}</Sentence>}
        <ButtonContainer to="/">
          <Button $fullWidth>홈으로 돌아가기</Button>
        </ButtonContainer>
      </SentenceContainer>
      <Icon />
    </Container>
  );
};

export default ErrorPage;

const Container = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;

  height: 100%;
  padding-top: 150px;
`;

const SentenceContainer = styled.article`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[5]};
  align-items: center;
`;

const ButtonContainer = styled(Link)`
  width: 133px;
`;

const MainSentence = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['4xl']};
  font-weight: bold;
  color: ${({ theme }) => theme.color.text.secondary};
`;

const Sentence = styled.span`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.color.text.secondary};
  text-align: center;
`;

const bounce = keyframes`
  0%, 20%, 50%, 80%, 100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-5px);
  }
  60% {
    transform: translateY(-3px);
  }
`;

const Icon = styled(MdOutlineErrorOutline)`
  width: 100px;
  height: 100px;
  margin-top: 80px;

  fill: ${({ theme }) => theme.color.primary};

  animation: ${bounce} 1s ease infinite;
`;
