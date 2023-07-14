import { FaShare } from 'react-icons/Fa';
import { styled } from 'styled-components';

const ShareButton = () => {
  return (
    <Button>
      <FaShare size={45} />
    </Button>
  );
};

export default ShareButton;

const Button = styled.button`
  cursor: pointer;
  background: transparent;
  border: none;
`;
