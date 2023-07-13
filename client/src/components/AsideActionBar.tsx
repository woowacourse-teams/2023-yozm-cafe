import { BiComment, BiHeart, BiShare } from 'react-icons/bi';
import { styled } from 'styled-components';

const AsideActionBar = () => {
  return (
    <Container>
      <Action>
        <BiHeart size={50} />
      </Action>
      <Action>
        <BiComment size={50} />
      </Action>
      <Action>
        <BiShare size={50} />
      </Action>
    </Container>
  );
};

export default AsideActionBar;

const Container = styled.aside`
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const Action = styled.button`
  color: white;
  background: transparent;
  border: none;
`;
