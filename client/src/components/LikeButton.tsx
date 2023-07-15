import { useState } from 'react';
import { PiHeartFill } from 'react-icons/pi';
import { styled } from 'styled-components';
import client, { ClientNetworkError } from '../client';
import { Cafe } from '../types';

type LikeProps = {
  currentLikeCount: number;
};

const LikeButton = ({ currentLikeCount }: LikeProps) => {
  const [isActive, setIsActive] = useState(false);
  const [likeCount, setLikeCount] = useState(currentLikeCount);

  const handleLikeClick = async () => {
    try {
      if (isActive) {
        await client.removeFavoriteCafe(1);
      } else {
        await client.addFavoriteCafe(1);
      }
      const updatedCafes = await client.getCafes();
      const updatedLikeCount = updatedCafes.find((cafe: Cafe) => cafe.id === 1)?.likeCount || 0;
      setLikeCount(updatedLikeCount);
      setIsActive(!isActive);
    } catch (error) {
      if (error instanceof ClientNetworkError) {
        console.error('네트워크 에러입니다.', error);
      } else {
        console.error('좋아요 기능의 업데이트 에러입니다.', error);
      }
    }
  };

  return (
    <Container>
      <StyledHeart $isActive={isActive} onClick={handleLikeClick} />
      <LikeCount>{likeCount}</LikeCount>
    </Container>
  );
};

export default LikeButton;

const StyledHeart = styled(PiHeartFill)<{ $isActive: boolean }>`
  cursor: pointer;
  font-size: ${({ theme }) => theme.fontSize['5xl']};
  color: ${({ theme, $isActive }) => ($isActive ? theme.color.text.secondary : theme.color.primary)};
`;

const LikeCount = styled.span`
  font-size: small;
  color: ${({ theme }) => theme.color.text.secondary};
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
