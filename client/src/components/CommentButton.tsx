import { FaRegComment } from 'react-icons/Fa';
import { styled } from 'styled-components';

const CommentButton = () => {
  const handleClick = () => {
    alert('아직 준비중입니다!');
  };

  return (
    <Wrapper>
      <Button onClick={handleClick} />
    </Wrapper>
  );
};

export default CommentButton;

const Wrapper = styled.aside`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Button = styled(FaRegComment)`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['3xl']};
`;
