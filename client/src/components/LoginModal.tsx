import type React from 'react';
import { CgClose } from 'react-icons/cg';
import { styled } from 'styled-components';
import useAuthUrls from '../hooks/useAuthUrls';
import type { Theme } from '../styles/theme';
import LoginButton from './LoginButton';

const brandColors: Record<string, keyof Theme['color']> = {
  kakao: 'yellow',
  google: 'white',
};

type ModalProps = {
  onClose: () => void;
};

const LoginModal = (props: ModalProps) => {
  const { onClose } = props;
  const { data: urls } = useAuthUrls();

  const handleContentClick: React.MouseEventHandler = (event) => {
    // 클릭 이벤트가 ModalContainer까지 전파되지 않도록 막습니다.
    event.stopPropagation();
  };

  return (
    <ModalContainer onClick={onClose}>
      <ModalContent onClick={handleContentClick}>
        <CloseButtonContainer aria-label="닫기 버튼" role="dialog" aria-modal="true" aria-hidden="true">
          <CloseIcon onClick={onClose} />
        </CloseButtonContainer>
        <Logo />
        <LoginTitle>간편 로그인</LoginTitle>
        <ButtonContainer>
          {urls.map(({ provider, authorizationUrl }) => (
            <a href={authorizationUrl} key={provider}>
              <LoginButton $color={brandColors[provider] ?? 'white'} aria-label={`${provider} 로그인`}>
                <img src={`/assets/${provider}.svg`} alt={`${provider} 로고`} />
              </LoginButton>
            </a>
          ))}
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

const CloseButtonContainer = styled.button`
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

const Logo = styled.img.attrs({ src: '/assets/logo.svg' })`
  height: ${({ theme }) => theme.fontSize['5xl']};
`;
