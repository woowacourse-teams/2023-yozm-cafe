import { FaShare } from 'react-icons/Fa';
import { styled } from 'styled-components';

const ShareButton = () => {
  return (
    <Wrapper>
      <Button />
    </Wrapper>
  );
};

export default ShareButton;

const Wrapper = styled.aside`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Button = styled(FaShare)`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['3xl']};
`;
