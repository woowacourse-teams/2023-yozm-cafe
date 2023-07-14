import { FaShare } from 'react-icons/Fa';
import { styled } from 'styled-components';

const ShareButton = () => {
  return (
    <Button>
      <FaShare />
    </Button>
  );
};

export default ShareButton;

const Button = styled(FaShare)`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['3xl']};
`;
