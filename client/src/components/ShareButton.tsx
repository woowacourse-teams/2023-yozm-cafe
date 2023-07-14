import { FaShare } from 'react-icons/Fa';
import { styled } from 'styled-components';

const ShareButton = () => {
  const handleClick = () => {
    alert('아직 준비중입니다!');
  };

  return (
    <Wrapper>
      <Button onClick={handleClick} />
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
