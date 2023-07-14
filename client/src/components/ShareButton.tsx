import { FaShare } from 'react-icons/Fa';
import { styled } from 'styled-components';

const ShareButton = () => {
  return (
    <Wrapper>
      <Button>
        <FaShare size={45} />
      </Button>
    </Wrapper>
  );
};

export default ShareButton;

const Wrapper = styled.aside`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Button = styled.button`
  cursor: pointer;
  background: transparent;
  border: none;
  stroke: black;
`;
