import { PiHeartFill } from 'react-icons/pi';
import { styled } from 'styled-components';

type LikeButtonProps = {
  likeCount: number;
  active: boolean;
  onChange: () => void;
};

const LikeButton = ({ likeCount, active, onChange }: LikeButtonProps) => {
  const handleLikeClick = () => onChange?.();

  return (
    <Container>
      <HeartIcon $active={active} onClick={handleLikeClick} />
      <LikeCount>{likeCount}</LikeCount>
    </Container>
  );
};

export default LikeButton;

const HeartIcon = styled(PiHeartFill)<{ $active: boolean }>`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['4xl']};
  color: ${({ theme, $active }) => ($active ? theme.color.primary : theme.color.white)};
`;

const LikeCount = styled.span`
  font-size: small;
  color: ${({ theme }) => theme.color.white};
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
