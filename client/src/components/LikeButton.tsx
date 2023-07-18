import { useState } from 'react';
import { PiHeartFill } from 'react-icons/pi';
import { styled } from 'styled-components';

type LikeButtonProps = {
  likeCount: number;
  onChange: (likeCount: number) => void;
};

const LikeButton = ({ likeCount }: LikeButtonProps) => {
  const [isActive, setIsActive] = useState(false);

  const handleLikeClick = async () => {
    setIsActive(!isActive);
  };

  return (
    <Container>
      <HeartIcon $isActive={isActive} onClick={handleLikeClick} />
      <LikeCount>{likeCount}</LikeCount>
    </Container>
  );
};

export default LikeButton;

const HeartIcon = styled(PiHeartFill)<{ $isActive: boolean }>`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['5xl']};
  color: ${({ theme, $isActive }) => ($isActive ? theme.color.primary : theme.color.secondary)};
`;

const LikeCount = styled.span`
  font-size: small;
  color: ${({ theme }) => theme.color.gray};
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
