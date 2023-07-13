import { styled } from 'styled-components';

const AppHeader = () => {
  return (
    <Container>
      <BrandIcon src="/images/yozm-cafe-icon.png" />
    </Container>
  );
};

export default AppHeader;

const Container = styled.header`
  position: absolute;
  top: 0;
  height: 60px;
  padding: ${({ theme }) => theme.space[2]};
`;

const BrandIcon = styled.img`
  height: 100%;
  border-radius: 10%;
`;
