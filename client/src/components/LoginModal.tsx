import { CgClose } from 'react-icons/cg';
import { styled } from 'styled-components';
import LoginButton from './LoginButton';
import Logo from './Logo';

type ModalProps = {
  onClose: () => void;
};

const LoginModal = ({ onClose }: ModalProps) => {
  const handleContentClick = (e: React.MouseEvent<HTMLDivElement>) => {
    // 클릭 이벤트가 ModalContainer까지 전파되지 않도록 막습니다.
    e.stopPropagation();
  };

  return (
    <ModalContainer onClick={onClose}>
      <ModalContent onClick={handleContentClick}>
        <CloseButtonContainer>
          <CloseIcon onClick={onClose} />
        </CloseButtonContainer>
        <Logo fontSize="5xl" />
        <LoginTitle>간편 로그인</LoginTitle>
        <ButtonContainer>
          <a href="/auth/kakao?code=1234">
            <LoginButton $color="yellow">
              <img src="/assets/kakao.svg" alt="카카오 로고" />
            </LoginButton>
          </a>
          <a href="/api/auth/google">
            <LoginButton $color="white">
              <img src="/assets/google.svg" alt="구글 로고" />
            </LoginButton>
          </a>
        </ButtonContainer>
      </ModalContent>
    </ModalContainer>
  );
};

export default LoginModal;

const ModalContainer = styled.div`
  position: fixed;
  z-index: 999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  background: ${({ theme }) => theme.color.background.secondary};
`;

const ModalContent = styled.div`
  cursor: default;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 350px;
  padding: ${({ theme }) => theme.space['5']};

  background-color: white;
  border-radius: 8px;
`;

const CloseButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;

  width: 100%;
  margin-bottom: ${({ theme }) => theme.space['2.5']};

  color: ${({ theme }) => theme.color.gray};
`;

const CloseIcon = styled(CgClose)`
  cursor: pointer;
`;

const LoginTitle = styled.div`
  display: flex;
  gap: ${({ theme }) => theme.space['1.5']};
  align-items: center;

  width: 100%;
  margin-top: ${({ theme }) => theme.space['2.5']};
  margin-bottom: ${({ theme }) => theme.space['4']};

  font-size: ${({ theme }) => theme.fontSize.xs};
  color: ${({ theme }) => theme.color.gray};

  /* ::before 스타일링 */
  &::before {
    content: '';
    flex: 1;
    height: 1px;
    background: ${({ theme }) => theme.color.gray};
  }

  /* ::after 스타일링 */
  &::after {
    content: '';
    flex: 1;
    height: 1px;
    background: ${({ theme }) => theme.color.gray};
  }
`;

const ButtonContainer = styled.section`
  display: flex;
  justify-content: space-evenly;
  width: 100%;
`;
