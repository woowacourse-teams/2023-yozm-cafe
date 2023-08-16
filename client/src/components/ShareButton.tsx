import { FaShare } from 'react-icons/fa';
import { styled } from 'styled-components';

type ShareButtonProps = {
  url: string;
};

const ShareButton = (props: ShareButtonProps) => {
  const { url } = props;
  const copyToClipboard = () => {
    navigator.clipboard.writeText(url).then(
      () => {
        alert('URL이 복사되었습니다!');
      },
      (error) => {
        console.error('URL 복사 실패:', error);
      },
    );
  };

  return (
    <Container>
      <Button onClick={copyToClipboard} />
      <Text>공유</Text>
    </Container>
  );
};

export default ShareButton;

const Container = styled.aside`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Button = styled(FaShare)`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['4xl']};
`;

const Text = styled.span`
  font-size: ${({ theme }) => theme.fontSize.sm};
`;
