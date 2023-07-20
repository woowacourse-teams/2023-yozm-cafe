import { styled } from 'styled-components';
import LoginButton from '../components/LoginButton';
import Logo from '../components/Logo';

const Login = () => {
  return (
    <Container>
      <LogoContainer>
        <Logo fontSize="7xl" />
      </LogoContainer>
      <ButtonContainer>
        <a href="/auth/kakao?code=1234">
          <KakaoLoginButton>
            <LoginButton $color="yellow" $border="none">
              <ButtonContent>
                <img src="/assets/kakao.svg" alt="카카오 로고" />
                <ButtonText>카카오 계정으로 로그인</ButtonText>
              </ButtonContent>
            </LoginButton>
          </KakaoLoginButton>
        </a>
        <a href="/api/auth/google">
          <GoogleLoginButton>
            <LoginButton $color="white">
              <ButtonContent>
                <img src="/assets/google.svg" alt="카카오 로고" />
                <ButtonText>구글 계정으로 로그인</ButtonText>
              </ButtonContent>
            </LoginButton>
          </GoogleLoginButton>
        </a>
      </ButtonContainer>
    </Container>
  );
};

export default Login;

const Container = styled.main`
  position: relative;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;

  width: 100%;
  height: 100%;
`;

const LogoContainer = styled.div``;

const ButtonContainer = styled.section`
  display: flex;
  flex-direction: column;
`;

const ButtonContent = styled.div`
  display: flex;
  align-items: center;
`;

const ButtonText = styled.span`
  width: 100%;
`;

const KakaoLoginButton = styled.div`
  margin-bottom: ${({ theme }) => theme.space['3']};
`;

const GoogleLoginButton = styled.div``;
