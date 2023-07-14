import { FaRegComment } from 'react-icons/Fa';
import { styled } from 'styled-components';

const CommentButton = () => {
  return (
    <Button>
      <FaRegComment size={40} />
    </Button>
  );
};

export default CommentButton;

const Button = styled.button`
  cursor: pointer;
  background: transparent;
  border: none;
`;
