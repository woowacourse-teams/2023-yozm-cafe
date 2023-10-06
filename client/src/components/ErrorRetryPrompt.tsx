import styled from 'styled-components';
import Button from './Button';

type ErrorRetryPromptProps = {
  resetErrorBoundary: () => void;
};

const ErrorRetryPrompt = (Props: ErrorRetryPromptProps) => {
  const { resetErrorBoundary } = Props;

  return (
    <Container>
      <SentenceContainer>
        <MainSentence>일시적인 장애가 발생했습니다</MainSentence>
        <Sentence>요청사항을 처리하는데 실패했습니다.</Sentence>
        <ButtonContainer>
          <Button $fullWidth onClick={resetErrorBoundary}>
            다시 시도
          </Button>
        </ButtonContainer>
      </SentenceContainer>
    </Container>
  );
};

export default ErrorRetryPrompt;

const Container = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;
  height: 100%;
  padding-top: 230px;
`;

const SentenceContainer = styled.article`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.space[5]};
  align-items: center;
`;

const ButtonContainer = styled.div`
  width: 133px;
`;

const MainSentence = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['2xl']};
  font-weight: bold;
  color: ${({ theme }) => theme.color.text.secondary};
`;

const Sentence = styled.span`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.color.text.secondary};
  text-align: center;
`;
