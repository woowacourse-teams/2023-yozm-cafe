import { PiChatFill } from 'react-icons/pi';
import { styled } from 'styled-components';

const CommentButton = () => {
  const handleClick = () => {
    alert('아직 준비중입니다!');
  };

  return (
    <Container>
      <Button onClick={handleClick} />
    </Container>
  );
};

export default CommentButton;

const Container = styled.aside`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Button = styled(PiChatFill)`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['5xl']};
`;
