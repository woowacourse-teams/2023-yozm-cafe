import { PiShareFatFill } from 'react-icons/pi';

import { styled } from 'styled-components';

const ShareButton = () => {
  const handleClick = () => {
    alert('아직 준비중입니다!');
  };

  return (
    <Container>
      <Button onClick={handleClick} />
    </Container>
  );
};

export default ShareButton;

const Container = styled.aside`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Button = styled(PiShareFatFill)`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['5xl']};
`;
