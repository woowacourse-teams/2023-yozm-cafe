import { MdOutlineErrorOutline } from 'react-icons/md';
import { Link } from 'react-router-dom';
import { keyframes, styled } from 'styled-components';
import Button from '../components/Button';

const ErrorPage = () => {
  return (
    <Contaienr>
      <SentenceContainer>
        <MainSentence>다시 확인해주세요</MainSentence>
        <Sentence>요청하신 내용을 찾을 수 없어요</Sentence>
        <ButtonContainer to="/">
          <Button $fullWidth={true}>홈으로 돌아가기</Button>
        </ButtonContainer>
      </SentenceContainer>
      <Icon />
    </Contaienr>
  );
};

export default ErrorPage;

const Contaienr = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;

  height: 100vh;
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
